package io.github.sefiraat.equivalencytech.listeners;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.configuration.ConfigMain;
import io.github.sefiraat.equivalencytech.misc.Utils;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChestPlaceListener implements Listener {

    private final EquivalencyTech plugin;

    public ChestPlaceListener(EquivalencyTech plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChestPlace(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() == Material.CHEST) {
            boolean isDis = isDis(e);
            boolean isCon = isCon(e);
            if (e.isCancelled()) {
                return;
            }
            if (isDis) {
                placeDisChest(e);
                return;
            }
            if (isCon) {
                placeConChest(e);
                return;
            }
            if (nearbyEMCChest(e)) {
                e.getPlayer().sendMessage(Messages.messageEventEMCChestPlace(plugin));
                e.setCancelled(true);
            }
        }
    }

    private void placeConChest(BlockPlaceEvent e) {
        if (noNearbyChest(e.getBlockPlaced())) {
            Location location = e.getBlockPlaced().getLocation();
            ConfigMain.addCChestStore(plugin, location);
            ConfigMain.setupCChest(plugin, ConfigMain.getCChestIdStore(plugin, location), e.getPlayer());
        } else {
            e.setCancelled(true);
        }
    }

    private void placeDisChest(BlockPlaceEvent e) {
        if (noNearbyChest(e.getBlockPlaced())) {
            Location location = e.getBlockPlaced().getLocation();
            ConfigMain.addDChestStore(plugin, location);
            ConfigMain.setupDChest(plugin, ConfigMain.getDChestIdStore(plugin, location), e.getPlayer());
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChestBreak(BlockBreakEvent e) {
        Location location = e.getBlock().getLocation();
        Integer disID = ConfigMain.getDChestIdStore(plugin, location);
        Integer conID = ConfigMain.getCChestIdStore(plugin, location);
        if (disID != null || conID != null) {
            e.setCancelled(true);
            Chest chest = (Chest) e.getBlock().getState();
            Inventory inventory = chest.getBlockInventory();
            for (ItemStack itemStack : inventory.getContents()) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    e.getBlock().getWorld().dropItemNaturally(location, itemStack);
                    itemStack.setAmount(0);
                }
            }
            e.getBlock().setType(Material.AIR);
            if (disID != null) {
                e.getBlock().getWorld().dropItemNaturally(location, plugin.getEqItems().getDissolutionChest().getItemClone());
                ConfigMain.removeDChestStore(plugin, disID);
                ConfigMain.removeDChest(plugin, disID);
            }
            if (conID != null) {
                e.getBlock().getWorld().dropItemNaturally(location, plugin.getEqItems().getCondensatorChest().getItemClone());
                ConfigMain.removeCChestStore(plugin, conID);
                ConfigMain.removeCChest(plugin, conID);
            }
        }

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onChestInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null) {
            Location location = e.getClickedBlock().getLocation();
            Integer disID = ConfigMain.getDChestIdStore(plugin, location);
            Integer conID = ConfigMain.getCChestIdStore(plugin, location);
            if (disID != null) {
                if (isChestBeingOpened(e) && !hasPermissionDChest(disID, e.getPlayer())) {
                    e.getPlayer().sendMessage(Messages.messageEventCantOpenNotOwner(plugin));
                    e.setCancelled(true);
                }
            }
            if (conID != null) {
                if (isChestBeingOpened(e)) {
                    openChest(e);
                } else if (isChestBeingSet(e)) {
                    setChest(e);
                }
            }
        }
    }


    private boolean isDis(BlockPlaceEvent e) {
        return ContainerStorage.isDisChest(e.getItemInHand(), plugin);
    }

    private boolean isCon(BlockPlaceEvent e) {
        return ContainerStorage.isConChest(e.getItemInHand(), plugin);
    }

    private boolean nearbyEMCChest(BlockPlaceEvent e) {
        List<Block> blockList = new ArrayList<>();
        Block block = e.getBlockPlaced();
        blockList.add(block.getRelative(BlockFace.NORTH));
        blockList.add(block.getRelative(BlockFace.SOUTH));
        blockList.add(block.getRelative(BlockFace.EAST));
        blockList.add(block.getRelative(BlockFace.WEST));
        for (Block b : blockList) {
            if (b.getType() == Material.CHEST && (getDisId(b) != null || getConId(b) != null)) {
                return true;
            }
        }
        return false;
    }

    private boolean noNearbyChest(Block block) {
        List<Block> blockList = new ArrayList<>();
        blockList.add(block.getRelative(BlockFace.NORTH));
        blockList.add(block.getRelative(BlockFace.SOUTH));
        blockList.add(block.getRelative(BlockFace.EAST));
        blockList.add(block.getRelative(BlockFace.WEST));
        for (Block b : blockList) {
            if (b.getType() == Material.CHEST) {
                return false;
            }
        }
        return true;
    }

    private boolean hasPermissionDChest(Integer disId, Player player) {
        return ConfigMain.isOwnerDChest(plugin, player, disId) || player.isOp() || player.hasPermission("equitech.bypass");
    }

    private boolean hasPermissionCChest(Integer conId, Player player) {
        return ConfigMain.isOwnerCChest(plugin, player, conId) || player.isOp() || player.hasPermission("equitech.bypass");
    }

    private Integer getDisId(Block block) {
        return ConfigMain.getDChestIdStore(plugin, block.getLocation());
    }

    private Integer getConId(Block block) {
        return ConfigMain.getCChestIdStore(plugin, block.getLocation());
    }

    private boolean isChestBeingOpened(PlayerInteractEvent e) {
        return  e.getClickedBlock().getType() == Material.CHEST &&
                e.getAction() == Action.RIGHT_CLICK_BLOCK &&
                !e.getPlayer().isSneaking() &&
                e.useInteractedBlock() != Event.Result.DENY;
    }

    private boolean isChestBeingSet(PlayerInteractEvent e) {
        return  e.getClickedBlock().getType() == Material.CHEST &&
                e.getAction() == Action.RIGHT_CLICK_BLOCK &&
                e.getPlayer().isSneaking() &&
                e.useInteractedBlock() != Event.Result.DENY;
    }

    private void openChest(PlayerInteractEvent e) {
        Integer conId = getConId(e.getClickedBlock());
        if (conId != null && !hasPermissionCChest(conId, e.getPlayer())) {
            e.getPlayer().sendMessage(Messages.messageEventCantOpenNotOwner(plugin));
            e.setCancelled(true);
        }
    }

    private void setChest(PlayerInteractEvent e) {
        Integer conId = getConId(e.getClickedBlock());
        if (conId == null || hasPermissionCChest(conId, e.getPlayer())) {
            ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand().clone();
            if (itemStack.getType() == Material.AIR) {
                ConfigMain.setCChestItem(plugin, conId, null);
                e.getPlayer().sendMessage(Messages.messageEventItemUnset(plugin));
                return;
            }
            itemStack.setAmount(1);
            Double emcValue = Utils.getEMC(plugin, itemStack);
            if (Utils.canBeSynth(plugin, itemStack) && emcValue != null) {
                ConfigMain.setCChestItem(plugin, conId, itemStack);
                e.getPlayer().sendMessage(Messages.messageEventItemSet(plugin));
            } else {
                e.getPlayer().sendMessage(Messages.msgCmdEmcNone(plugin));
            }
        } else {
            e.getPlayer().sendMessage(Messages.messageEventCantOpenNotOwner(plugin));
        }
        e.setCancelled(true);
    }


}
