package io.github.sefiraat.equivalencytech.listeners;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.configuration.ConfigMain;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
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

public class DissolutionChestListener implements Listener {

    private final EquivalencyTech plugin;

    public DissolutionChestListener(EquivalencyTech plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!e.isCancelled() && isDis(e)) {
            if (!isAdjacentChest(e.getBlockPlaced())) {
                Location location = e.getBlockPlaced().getLocation();
                ConfigMain.addDissolutionChestStore(plugin, location);
                ConfigMain.setupDChest(plugin, ConfigMain.getDissolutionChestID(plugin, location), e.getPlayer());
            } else {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Messages.messageEventDChestMisPlace(plugin));
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Location location = e.getBlock().getLocation();
        Integer chestID = ConfigMain.getDissolutionChestID(plugin, location);
        if (chestID != null) {
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
            e.getBlock().getWorld().dropItemNaturally(location, plugin.getEqItems().getDissolutionChest().getItemClone());
            ConfigMain.removeDissolutionChest(plugin, chestID);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onOpenChest(PlayerInteractEvent e) {
        if (isChestBeingOpened(e)) {
            Integer disId = getDisId(e);
            if (disId != null && !canOpenDChest(disId, e.getPlayer())) {
                e.getPlayer().sendMessage(Messages.messageEventCantOpenNotOwner(plugin));
                e.setCancelled(true);
            }
        }
    }

    private boolean isAdjacentChest(Block block) {
        List<Block> blockList = new ArrayList<>();
        blockList.add(block.getRelative(BlockFace.NORTH));
        blockList.add(block.getRelative(BlockFace.SOUTH));
        blockList.add(block.getRelative(BlockFace.EAST));
        blockList.add(block.getRelative(BlockFace.WEST));
        for (Block b : blockList) {
            if (ConfigMain.getDissolutionChestID(plugin, b.getLocation()) != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isDis(BlockPlaceEvent e) {
        return ContainerStorage.isDisChest(e.getItemInHand(), plugin);
    }

    private Integer getDisId(PlayerInteractEvent e) {
        return ConfigMain.getDissolutionChestID(plugin, e.getClickedBlock().getLocation());
    }
    private boolean canOpenDChest(Integer disId, Player player) {
        return ConfigMain.isOwnerDChest(plugin, player, disId) || player.isOp() || player.hasPermission("equitech.bypass");
    }

    private boolean isChestBeingOpened(PlayerInteractEvent e) {
        return  e.getClickedBlock() != null &&
                e.getClickedBlock().getType() == Material.CHEST &&
                e.getAction() == Action.RIGHT_CLICK_BLOCK &&
                e.useInteractedBlock() != Event.Result.DENY;
    }

}
