package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigBooleans {

    private final boolean debuggingLogs;
    public boolean getDebuggingLogs() {
        return debuggingLogs;
    }

    public ConfigBooleans(EquivalencyTech plugin) {

        FileConfiguration c = plugin.getConfig();

        debuggingLogs = c.getBoolean("DEBUG.SHOW_DEBUGGING_LOGS");
    }

}
