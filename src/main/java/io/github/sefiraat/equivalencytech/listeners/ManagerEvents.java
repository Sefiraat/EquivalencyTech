package io.github.sefiraat.equivalencytech.listeners;

import io.github.sefiraat.equivalencytech.EquivalencyTech;

public class ManagerEvents {

    private final OrbOpenListener orbOpenListener;
    private final CraftListener craftListener;
    private final DissolutionChestListener dissolutionChestListener;

    public OrbOpenListener getOrbOpenListener() {
        return orbOpenListener;
    }

    public CraftListener getCraftListener() {
        return craftListener;
    }

    public DissolutionChestListener getDissolutionChestListener() {
        return dissolutionChestListener;
    }

    public ManagerEvents(EquivalencyTech plugin) {
        orbOpenListener = new OrbOpenListener(plugin);
        craftListener = new CraftListener(plugin);
        dissolutionChestListener = new DissolutionChestListener(plugin);
    }

}
