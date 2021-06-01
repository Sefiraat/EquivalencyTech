package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.misc.Utils;
import io.github.sefiraat.equivalencytech.statics.Messages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
        createLearnedConfig();
        createEmcConfig();
    }

    public void saveAdditionalConfigs() {
        saveEmcConfig();
        saveLearnedConfig();
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

    public static void addLearnedItem(EquivalencyTech plugin, Player player, String itemName) {
        FileConfiguration c = plugin.getConfigClass().getLearnedItemsConfig();
        c.set(player.getUniqueId().toString() + "." + ChatColor.stripColor(itemName), true);
    }

    public static void removeLearnedItem(EquivalencyTech plugin, Player player, String itemName) {
        FileConfiguration c = plugin.getConfigClass().getLearnedItemsConfig();
        c.set(player.getUniqueId().toString() + "." + itemName, null);
    }

    public static List<String> getLearnedItems(EquivalencyTech plugin, Player player) {
        FileConfiguration c = plugin.getConfigClass().getLearnedItemsConfig();
        List<String> list = new ArrayList<>();
        if (c.contains(player.getUniqueId().toString())) {
            list.addAll(c.getConfigurationSection(player.getUniqueId().toString()).getKeys(false));
            java.util.Collections.sort(list);
        }
        return list;
    }

    public static int getLearnedItemAmount(EquivalencyTech plugin, Player player) {
        return getLearnedItems(plugin, player).size();
    }

    public static void addPlayerEmc(EquivalencyTech plugin, Player player, Double emcValue, Double totalEmc, int stackAmount) {
        double playerEmc = getPlayerEmc(plugin, player);
        int burnRate = plugin.getConfigClass().getEmc().getBurnRate();
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

    public static void removePlayerEmc(EquivalencyTech plugin, Player player, Double emcValue) {
        setPlayerEmc(plugin, player, getPlayerEmc(plugin, player) - emcValue);
    }

    public static void setPlayerEmc(EquivalencyTech plugin, Player player, Double emcValue) {
        FileConfiguration c = plugin.getConfigClass().getPlayerEMCConfig();
        c.set(player.getUniqueId().toString(), emcValue);
    }

    public static double getPlayerEmc(EquivalencyTech plugin, Player player) {
        FileConfiguration c = plugin.getConfigClass().getPlayerEMCConfig();
        double amount = 0;
        if (c.contains(player.getUniqueId().toString())) {
            amount = c.getDouble(player.getUniqueId().toString());
        }
        return amount;
    }



}
