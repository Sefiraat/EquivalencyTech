package io.github.sefiraat.equivalencytech.runnables;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.scheduler.BukkitRunnable;

public class RunnableSave extends BukkitRunnable {

    public final EquivalencyTech plugin;

    public RunnableSave(EquivalencyTech plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getConfigMainClass().saveAdditionalConfigs();
    }
}