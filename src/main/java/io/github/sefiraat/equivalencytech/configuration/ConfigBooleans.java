package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.configuration.file.FileConfiguration;

public final class ConfigBooleans {

    FileConfiguration configuration;

    public boolean getDebuggingLogs() {
        return configuration.getBoolean("DEBUG.SHOW_DEBUGGING_LOGS");
    }

    public ConfigBooleans(EquivalencyTech plugin) {
        configuration = plugin.getConfig();
    }

}
