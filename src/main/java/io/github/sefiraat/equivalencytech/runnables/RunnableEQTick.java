package io.github.sefiraat.equivalencytech.runnables;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.configuration.ConfigMain;
import io.github.sefiraat.equivalencytech.misc.Utils;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.MessageFormat;
import java.util.HashMap;

public class RunnableEQTick extends BukkitRunnable {

    public final EquivalencyTech plugin;
    public final boolean sf;

    public RunnableEQTick(EquivalencyTech plugin) {
        this.plugin = plugin;
        sf = EquivalencyTech.getInstance().getManagerSupportedPlugins().isInstalledSlimefun();
    }

    @Override
    public void run() {
        processDChests();
        processCChests();
    }

    private void processDChests() {
        for (Location location : ConfigMain.getAllDChestLocations(plugin)) {
            if (location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
                int chestId = ConfigMain.getDChestIdStore(plugin, location);
                String playerUUID = ConfigMain.getOwnerDChest(plugin, chestId);

                BlockState state = location.getBlock().getState();

                if (!(state instanceof Chest)) {
                    //修正溶解箱消失后spam的问题
                    ConfigMain.removeDChestStore(plugin, chestId);
                    ConfigMain.removeDChest(plugin, chestId);
                    EquivalencyTech.getInstance().getLogger().warning(getErrorDissolutionChest(chestId, location));
                    continue;
                }

                Chest chest = (Chest) location.getBlock().getState();
                Inventory inventory = chest.getBlockInventory();
                for (ItemStack itemStack : inventory.getContents()) {
                    if (itemStack != null && itemStack.getType() != Material.AIR) {
                        boolean isEQ = ContainerStorage.isCraftable(itemStack, plugin);
                        SlimefunItem sfItem = null;
                        if (sf) {
                            sfItem = SlimefunItem.getByItem(itemStack);
                        }
                        Material material = itemStack.getType();
                        //fix https://github.com/Sefiraat/EquivalencyTech/issues/44
                        try {
                            if (Utils.getEMC(plugin, itemStack) == null) {
                                EquivalencyTech.getInstance().getLogger().warning(getErrorDissolutionChest(chestId, location) + "\nFix https://github.com/Sefiraat/EquivalencyTech/issues/44");
                                break;//打破当前循环，阻止导致报错的代码执行
                            }//尝试去处理异常
                        }catch(Exception e){
                            EquivalencyTech.getInstance().getLogger().warning(getErrorDissolutionChest(chestId, location) + "\nFix https://github.com/Sefiraat/EquivalencyTech/issues/44");
                        }

                        Double emcValue = Utils.roundDown((Utils.getEMC(plugin, itemStack) / 100) * 150, 2);//改行存在问题

                        //fix end
                        if (emcValue != null && Utils.canBeSynth(plugin, itemStack)) {
                            String entryName;
                            if (isEQ) {
                                entryName = Utils.eqNameConfig(itemStack.getItemMeta().getDisplayName());
                            } else if (sfItem != null) {
                                entryName = sfItem.getId();
                            } else {
                                entryName = material.toString();
                            }
                            if (!ConfigMain.getLearnedItems(plugin, playerUUID).contains(entryName)) {
                                ConfigMain.addLearnedItem(plugin, playerUUID, entryName);
                            }
                            ConfigMain.addPlayerEmc(plugin, playerUUID, emcValue);
                            itemStack.setAmount(itemStack.getAmount() - 1);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void processCChests() {
        for (Location location : ConfigMain.getAllCChestLocations(plugin)) {
            if (location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
                int chestId = ConfigMain.getCChestIdStore(plugin, location);
                String playerUUID = ConfigMain.getOwnerCChest(plugin, chestId);

                BlockState state = location.getBlock().getState();

                if (!(state instanceof Chest)) {
                    //为了解决一些意外导致的凝固箱不工作最后在控制台中喷射，可以采取简单粗暴的直接删除
                    ConfigMain.removeCChestStore(plugin, chestId);
                    ConfigMain.removeCChest(plugin, chestId);
                    EquivalencyTech.getInstance().getLogger().warning(getErrorCondensateChest(chestId, location));
                    continue;
                }

                Chest chest = (Chest) location.getBlock().getState();
                Inventory inventory = chest.getBlockInventory();
                ItemStack itemStack = ConfigMain.getCChestItem(plugin, chestId);
                if (itemStack != null) {
                    Double emcValue = Utils.getEMC(plugin, itemStack);
                    if (emcValue != null) {
                        Double playerEmc = ConfigMain.getPlayerEmc(plugin, playerUUID);
                        if (playerEmc >= emcValue) {
                            HashMap<Integer, ItemStack> failed = inventory.addItem(itemStack);
                            if (failed.isEmpty()) {
                                ConfigMain.removePlayerEmc(plugin, playerUUID, emcValue);
                            }
                        }
                    }
                }
            }
        }
    }

    public static String getErrorDissolutionChest(int chestId, Location location) {
        return MessageFormat.format(
            "A Dissolution chest (ID: {0}has been FUCKED wrongly. " +
                "Either replace with a vanilla chest (location : {1}) " +
                "or remove from dissolution_chests.yml",
            chestId,
            location.toString()
        );
    }

    public static String getErrorCondensateChest(int chestId, Location location) {
        return MessageFormat.format(
            "A Condensate chest (ID: {0}has been removed wrongly. " +
                "Either replace with a vanilla chest (location : {1})  " +
                "or remove from condensate_chests.yml",
            chestId,
            location.toString()
        );
    }
}

