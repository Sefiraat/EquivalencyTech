package io.github.sefiraat.equivalencytech.statics;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Config {

    public static void addLearnedItem(EquivalencyTech plugin, Player player, Material material) {
        FileConfiguration c = plugin.getLearnedItemsConfig();
        c.set(player.getUniqueId().toString() + "." + material.name(), true);
    }

    public static List<Material> getLearnedItems(EquivalencyTech plugin, Player player) {
        FileConfiguration c = plugin.getLearnedItemsConfig();
        List<Material> list = new ArrayList<>();
        if (c.contains(player.getUniqueId().toString())) {
            for (String s : c.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)) {
                list.add(Material.valueOf(s));
            }
            list.sort(Comparator.comparing(Material::name));
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
