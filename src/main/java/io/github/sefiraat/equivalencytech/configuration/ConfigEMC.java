package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class ConfigEMC {

    private Map<String, Double> emcBaseValues;
    public Map<String, Double> getEmcBaseValues() {
        return emcBaseValues;
    }

    public ConfigEMC(EquivalencyTech plugin) {
        loadBaseEmcValues(plugin);
    }

    private void loadBaseEmcValues(EquivalencyTech plugin) {
        Map<String, Double> h = new HashMap<>();
        ConfigurationSection c = plugin.getConfig().getConfigurationSection("EMC_VALUES.BASE");
        assert c != null;
        for (String s : c.getKeys(false)) {
            Double emcValue = c.getDouble(s);
            h.put(s, emcValue);
        }
        emcBaseValues = h;
    }

}
