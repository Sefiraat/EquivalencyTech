package io.github.sefiraat.equivalencytech.runnables;

import io.github.sefiraat.equivalencytech.EquivalencyTech;

public class ManagerRunnables {

    private final EquivalencyTech plugin;

    private RunnableSave runnableSave;
    private RunnableEQTick runnableEQTick;

    public RunnableSave getRunnableSave() {
        return runnableSave;
    }

    public RunnableEQTick getRunnableEQTick() {
        return runnableEQTick;
    }

    public ManagerRunnables(EquivalencyTech plugin) {
        this.plugin = plugin;
        setupRunnables();
    }

    private void setupRunnables() {

        runnableSave = new RunnableSave(plugin);
        runnableSave.runTaskTimer(plugin, 0, 100L);

        runnableEQTick = new RunnableEQTick(plugin);
        runnableEQTick.runTaskTimer(plugin, 0, 20L);

    }


}
