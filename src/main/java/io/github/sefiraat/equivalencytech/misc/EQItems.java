package io.github.sefiraat.equivalencytech.misc;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.items.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EQItems {

    private final EquivalencyTech plugin;
    public EquivalencyTech getPlugin() {
        return plugin;
    }

    private final TransmutationOrb transmutationOrb;
    public TransmutationOrb getTransmutationOrb() {
        return transmutationOrb;
    }

    private final AlchemicalCoal alchemicalCoal;
    public AlchemicalCoal getAlchemicalCoal() {
        return alchemicalCoal;
    }

    private final MobiusFuel mobiusFuel;
    public MobiusFuel getMobiusFuel() {
        return mobiusFuel;
    }

    private final AeternalisFuel aeternalisFuel;
    public AeternalisFuel getAeternalisFuel() {
        return aeternalisFuel;
    }

    private final DarkMatter darkMatter;
    public DarkMatter getDarkMatter() {
        return darkMatter;
    }

    private final RedMatter redMatter;
    public RedMatter getRedMatter() {
        return redMatter;
    }

    private final Map<String, ItemStack> eqItemMap;
    public Map<String, ItemStack> getEqItemMap() {
        return eqItemMap;
    }

    public EQItems(EquivalencyTech plugin) {
        this.plugin = plugin;
        transmutationOrb = new TransmutationOrb(plugin);
        alchemicalCoal = new AlchemicalCoal(plugin);
        mobiusFuel = new MobiusFuel(plugin);
        aeternalisFuel = new AeternalisFuel(plugin);
        darkMatter = new DarkMatter(plugin);
        redMatter = new RedMatter(plugin);

        eqItemMap = new HashMap<>();
        eqItemMap.put(transmutationOrb.getItem().getItemMeta().getDisplayName(), transmutationOrb.getClone());
        eqItemMap.put(alchemicalCoal.getItem().getItemMeta().getDisplayName(), alchemicalCoal.getClone());
        eqItemMap.put(mobiusFuel.getItem().getItemMeta().getDisplayName(), mobiusFuel.getClone());
        eqItemMap.put(aeternalisFuel.getItem().getItemMeta().getDisplayName(), aeternalisFuel.getClone());
        eqItemMap.put(darkMatter.getItem().getItemMeta().getDisplayName(), darkMatter.getClone());
        eqItemMap.put(redMatter.getItem().getItemMeta().getDisplayName(), redMatter.getClone());
    }


}
