package io.github.sefiraat.equivalencytech.timers;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerSave extends BukkitRunnable {

    public final EquivalencyTech plugin;

    public TimerSave(EquivalencyTech plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getConfigClass().saveAdditionalConfigs();
    }
}