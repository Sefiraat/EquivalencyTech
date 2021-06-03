package io.github.sefiraat.equivalencytech.listeners;

import io.github.sefiraat.equivalencytech.EquivalencyTech;

public class ManagerEvents {

    private final OrbOpenListener orbOpenListener;
    private final CraftListener craftListener;
    private final PlayerJoinListener playerJoinListener;
    private final ChestPlaceListener chestPlaceListener;

    public OrbOpenListener getOrbOpenListener() {
        return orbOpenListener;
    }

    public CraftListener getCraftListener() {
        return craftListener;
    }

    public ChestPlaceListener getChestPlaceListener() {
        return chestPlaceListener;
    }

    public PlayerJoinListener getPlayerJoinListener() {
        return playerJoinListener;
    }

    public ManagerEvents(EquivalencyTech plugin) {
        orbOpenListener = new OrbOpenListener(plugin);
        craftListener = new CraftListener(plugin);
        playerJoinListener = new PlayerJoinListener(plugin);
        chestPlaceListener = new ChestPlaceListener(plugin);
    }

}
