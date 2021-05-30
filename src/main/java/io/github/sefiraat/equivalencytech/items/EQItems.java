package io.github.sefiraat.equivalencytech.items;

import io.github.sefiraat.equivalencytech.EquivalencyTech;

public class EQItems {

    private final EquivalencyTech plugin;
    public EquivalencyTech getPlugin() {
        return plugin;
    }

    private final TransmutationOrb transmutationOrb;
    public TransmutationOrb getTransmutationTable() {
        return transmutationOrb;
    }

    public EQItems(EquivalencyTech plugin) {
        this.plugin = plugin;
        transmutationOrb = new TransmutationOrb(plugin);
    }
}
