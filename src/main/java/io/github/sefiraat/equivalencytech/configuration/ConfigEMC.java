package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigEMC {

    private final FileConfiguration configuration;

    public Map<String, Double> getEmcBaseValues() {
        Map<String, Double> h = new HashMap<>();
        ConfigurationSection c = configuration.getConfigurationSection("EMC_VALUES.BASE");
        assert c != null;
        for (String s : c.getKeys(false)) {
            Double emcValue = c.getDouble(s);
            h.put(s, emcValue);
        }
        return h;
    }

    public Map<String, Double> getEmcSlimefunValues() {
        Map<String, Double> h = new HashMap<>();
        ConfigurationSection c = configuration.getConfigurationSection("EMC_VALUES.SLIMEFUN");
        assert c != null;
        for (String s : c.getKeys(false)) {
            Double emcValue = c.getDouble(s);
            h.put(s, emcValue);
        }
        return h;
    }

    public int getBurnRate() {
        return configuration.getInt("EMC_VALUES.BURN_RATE");
    }

    public ConfigEMC(EquivalencyTech plugin) {
        configuration = plugin.getConfig();
    }

}
