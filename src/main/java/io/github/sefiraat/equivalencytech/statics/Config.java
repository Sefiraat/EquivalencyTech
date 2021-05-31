package io.github.sefiraat.equivalencytech.statics;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static void addLearnedItem(EquivalencyTech plugin, Player player, String itemName) {
        FileConfiguration c = plugin.getLearnedItemsConfig();
        c.set(player.getUniqueId().toString() + "." + itemName, true);
    }

    public static List<String> getLearnedItems(EquivalencyTech plugin, Player player) {
        FileConfiguration c = plugin.getLearnedItemsConfig();
        List<String> list = new ArrayList<>();
        if (c.contains(player.getUniqueId().toString())) {
            list.addAll(c.getConfigurationSection(player.getUniqueId().toString()).getKeys(false));
            java.util.Collections.sort(list);
        }
        return list;
    }

    public static void addPlayerEmc(EquivalencyTech plugin, Player player, Double emcValue) {
        double playerEmc = getPlayerEmc(plugin, player);
        Double sum = playerEmc + emcValue;
        if (sum.equals(Double.POSITIVE_INFINITY)) {
            sum = Double.MAX_VALUE;
        }
        setPlayerEmc(plugin, player, sum);
    }

    public static void removePlayerEmc(EquivalencyTech plugin, Player player, Double emcValue) {
        setPlayerEmc(plugin, player, getPlayerEmc(plugin, player) - emcValue);
    }

    public static void setPlayerEmc(EquivalencyTech plugin, Player player, Double emcValue) {
        FileConfiguration c = plugin.getPlayerEMCConfig();
        c.set(player.getUniqueId().toString(), emcValue);
    }

    public static double getPlayerEmc(EquivalencyTech plugin, Player player) {
        FileConfiguration c = plugin.getPlayerEMCConfig();
        double amount = 0;
        if (c.contains(player.getUniqueId().toString())) {
            amount = c.getDouble(player.getUniqueId().toString());
        }
        return amount;
    }

}
