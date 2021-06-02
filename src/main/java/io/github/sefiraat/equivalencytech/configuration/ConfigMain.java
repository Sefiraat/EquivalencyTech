package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.misc.Utils;
import io.github.sefiraat.equivalencytech.statics.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigMain {

    private final EquivalencyTech plugin;

    private final ConfigStrings strings;
    private final ConfigEMC emc;
    private final ConfigBooleans bools;

    private File learnedItemsConfigFile;
    private FileConfiguration learnedItemsConfig;
    private File playerEMCConfigFile;
    private FileConfiguration playerEMCConfig;
    private File blockStoreConfigFile;
    private FileConfiguration blockStoreConfig;
    private File dChestConfigFile;
    private FileConfiguration dChestConfig;

    public ConfigStrings getStrings() {
        return strings;
    }
    public ConfigEMC getEmc() {
        return emc;
    }
    public ConfigBooleans getBools() {
        return bools;
    }

    public File getLearnedItemsConfigFile() {
        return learnedItemsConfigFile;
    }

    public FileConfiguration getLearnedItemsConfig() {
        return learnedItemsConfig;
    }

    public File getPlayerEMCConfigFile() {
        return playerEMCConfigFile;
    }

    public FileConfiguration getPlayerEMCConfig() {
        return playerEMCConfig;
    }

    public File getBlockStoreConfigFile() {
        return blockStoreConfigFile;
    }

    public FileConfiguration getBlockStoreConfig() {
        return blockStoreConfig;
    }

    public File getDChestConfigFile() {
        return dChestConfigFile;
    }

    public FileConfiguration getDChestConfig() {
        return dChestConfig;
    }

    public static final String DIS_CHEST_CFG = "DIS_CHESTS";

    public ConfigMain(EquivalencyTech plugin) {

        this.plugin = plugin;

        strings = new ConfigStrings(plugin);
        emc = new ConfigEMC(plugin);
        bools = new ConfigBooleans(plugin);

        sortConfigs();
    }

    private void sortConfigs() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        createAdditionalConfigs();
    }

    private void createAdditionalConfigs() {
        createLearnedConfig();
        createEmcConfig();
        createBlockStoreConfig();
        createDChestConfig();
    }

    public void saveAdditionalConfigs() {
        saveEmcConfig();
        saveLearnedConfig();
        saveBlockStoreConfig();
        saveDChestConfig();
    }

    private void createLearnedConfig() {
        learnedItemsConfigFile = new File(plugin.getDataFolder(), "learned_items.yml");
        if (!learnedItemsConfigFile.exists()) {
            learnedItemsConfigFile.getParentFile().mkdirs();
            plugin.saveResource("learned_items.yml", false);
        }
        learnedItemsConfig = new YamlConfiguration();
        try {
            learnedItemsConfig.load(learnedItemsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveLearnedConfig() {
        try {
            learnedItemsConfig.save(learnedItemsConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + learnedItemsConfigFile.getName());
        }
    }

    private void createEmcConfig() {
        playerEMCConfigFile = new File(plugin.getDataFolder(), "player_emc.yml");
        if (!playerEMCConfigFile.exists()) {
            playerEMCConfigFile.getParentFile().mkdirs();
            plugin.saveResource("player_emc.yml", false);
        }
        playerEMCConfig = new YamlConfiguration();
        try {
            playerEMCConfig.load(playerEMCConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveEmcConfig() {
        try {
            playerEMCConfig.save(playerEMCConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + playerEMCConfigFile.getName());
        }
    }

    private void createBlockStoreConfig() {
        blockStoreConfigFile = new File(plugin.getDataFolder(), "block_storage.yml");
        if (!blockStoreConfigFile.exists()) {
            blockStoreConfigFile.getParentFile().mkdirs();
            plugin.saveResource("block_storage.yml", false);
        }
        blockStoreConfig = new YamlConfiguration();
        try {
            blockStoreConfig.load(blockStoreConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveBlockStoreConfig() {
        try {
            blockStoreConfig.save(blockStoreConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + blockStoreConfigFile.getName());
        }
    }

    private void createDChestConfig() {
        dChestConfigFile = new File(plugin.getDataFolder(), "dissolution_chests.yml");
        if (!dChestConfigFile.exists()) {
            dChestConfigFile.getParentFile().mkdirs();
            plugin.saveResource("dissolution_chests.yml", false);
        }
        dChestConfig = new YamlConfiguration();
        try {
            dChestConfig.load(dChestConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveDChestConfig() {
        try {
            dChestConfig.save(dChestConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + dChestConfigFile.getName());
        }
    }

    public static void addLearnedItem(EquivalencyTech plugin, String uuid, String itemName) {
        FileConfiguration c = plugin.getConfigMainClass().getLearnedItemsConfig();
        c.set(uuid + "." + ChatColor.stripColor(itemName), true);
    }

    public static void removeLearnedItem(EquivalencyTech plugin, Player player, String itemName) {
        FileConfiguration c = plugin.getConfigMainClass().getLearnedItemsConfig();
        c.set(player.getUniqueId().toString() + "." + itemName, null);
    }

    public static List<String> getLearnedItems(EquivalencyTech plugin, String uuid) {
        FileConfiguration c = plugin.getConfigMainClass().getLearnedItemsConfig();
        List<String> list = new ArrayList<>();
        if (c.contains(uuid)) {
            list.addAll(c.getConfigurationSection(uuid).getKeys(false));
            java.util.Collections.sort(list);
        }
        return list;
    }

    public static int getLearnedItemAmount(EquivalencyTech plugin, Player player) {
        return getLearnedItems(plugin, player.getUniqueId().toString()).size();
    }

    public static void addPlayerEmc(EquivalencyTech plugin, Player player, Double emcValue, Double totalEmc, int stackAmount) {
        double playerEmc = getPlayerEmc(plugin, player);
        int burnRate = plugin.getConfigMainClass().getEmc().getBurnRate();
        if (burnRate > 0) {
            totalEmc -= ((totalEmc / 100) * burnRate);
        }
        totalEmc = Utils.roundDown(totalEmc, 2);
        Double sum = playerEmc + totalEmc;
        if (sum.equals(Double.POSITIVE_INFINITY)) {
            sum = Double.MAX_VALUE;
        }
        setPlayerEmc(plugin, player, sum);
        player.sendMessage(Messages.messageGuiEmcGiven(plugin, player, emcValue, totalEmc, stackAmount, burnRate));
    }

    public static void addPlayerEmc(EquivalencyTech plugin, String uuid, Double totalEmc) {
        double playerEmc = getPlayerEmc(plugin, uuid);
        int burnRate = plugin.getConfigMainClass().getEmc().getBurnRate();
        if (burnRate > 0) {
            totalEmc -= ((totalEmc / 100) * burnRate);
        }
        totalEmc = Utils.roundDown(totalEmc, 2);
        Double sum = playerEmc + totalEmc;
        if (sum.equals(Double.POSITIVE_INFINITY)) {
            sum = Double.MAX_VALUE;
        }
        setPlayerEmc(plugin, uuid, sum);
    }

    public static void removePlayerEmc(EquivalencyTech plugin, Player player, Double emcValue) {
        setPlayerEmc(plugin, player, getPlayerEmc(plugin, player) - emcValue);
    }

    public static void setPlayerEmc(EquivalencyTech plugin, Player player, Double emcValue) {
        FileConfiguration c = plugin.getConfigMainClass().getPlayerEMCConfig();
        c.set(player.getUniqueId().toString(), emcValue);
    }

    public static void setPlayerEmc(EquivalencyTech plugin, String uuid, Double emcValue) {
        FileConfiguration c = plugin.getConfigMainClass().getPlayerEMCConfig();
        c.set(uuid, emcValue);
    }

    public static double getPlayerEmc(EquivalencyTech plugin, Player player) {
        FileConfiguration c = plugin.getConfigMainClass().getPlayerEMCConfig();
        double amount = 0;
        if (c.contains(player.getUniqueId().toString())) {
            amount = c.getDouble(player.getUniqueId().toString());
        }
        return amount;
    }

    public static double getPlayerEmc(EquivalencyTech plugin, String uuid) {
        FileConfiguration c = plugin.getConfigMainClass().getPlayerEMCConfig();
        double amount = 0;
        if (c.contains(uuid)) {
            amount = c.getDouble(uuid);
        }
        return amount;
    }


    public static Integer getNextDissolutionChestID(EquivalencyTech plugin) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        int nextValue = 1;
        if (section != null) {
            for (String key : section.getKeys(false)) {
                int value = Integer.parseInt(key);
                if (value > nextValue) {
                    nextValue = value;
                }
            }
            nextValue++;
        }
        return nextValue;
    }

    public static void addDissolutionChestStore(EquivalencyTech plugin, Location location) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        c.set(DIS_CHEST_CFG + "." + getNextDissolutionChestID(plugin).toString(), location);
    }

    @Nullable
    public static Integer getDissolutionChestID(EquivalencyTech plugin, Location location) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Location l = section.getLocation(key);
                if (l.equals(location)) {
                    return Integer.parseInt(key);
                }
            }
        }
        return null;
    }

    public static void removeDissolutionChest(EquivalencyTech plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        section.set(id.toString(), null);
    }

    public static void setupDChest(EquivalencyTech plugin, Integer id, Player player) {
        FileConfiguration c = plugin.getConfigMainClass().dChestConfig;
        c.set(id + ".OWNING_PLAYER", player.getUniqueId().toString());
        c.set(id + ".LEVEL", 1);
    }

    public static void removeDChest(EquivalencyTech plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().dChestConfig;
        c.set(String.valueOf(id), null);
    }

    public static boolean isOwnerDChest(EquivalencyTech plugin, Player player, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().dChestConfig;
        return c.get(id + ".OWNING_PLAYER").equals(player.getUniqueId());
    }

    public static String getOwnerDChest(EquivalencyTech plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().dChestConfig;
        return c.getString(id + ".OWNING_PLAYER");
    }

    public static Location getDChestLocation(EquivalencyTech plugin, Integer id) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        return section.getLocation(id.toString());
    }

    public static List<Location> getAllDChestLocations(EquivalencyTech plugin) {
        FileConfiguration c = plugin.getConfigMainClass().blockStoreConfig;
        ConfigurationSection section = c.getConfigurationSection(DIS_CHEST_CFG);
        List<Location> ids = new ArrayList<>();
        if (section != null) {
            for (String s : section.getKeys(false)) {
                ids.add(section.getLocation(s));
            }
        }
        return ids;
    }

}
