package io.github.sefiraat.equivalencytech.item;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.item.builders.*;
import io.github.sefiraat.equivalencytech.misc.Utils;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EQItems {

    private final EquivalencyTech plugin;
    private final TransmutationOrb transmutationOrb;
    private final AlchemicalCoal alchemicalCoal;
    private final MobiusFuel mobiusFuel;
    private final AeternalisFuel aeternalisFuel;
    private final DarkMatter darkMatter;
    private final RedMatter redMatter;
    private final Map<String, ItemStack> eqItemMap;

    public EquivalencyTech getPlugin() {
        return plugin;
    }

    public TransmutationOrb getTransmutationOrb() {
        return transmutationOrb;
    }

    public AlchemicalCoal getAlchemicalCoal() {
        return alchemicalCoal;
    }

    public MobiusFuel getMobiusFuel() {
        return mobiusFuel;
    }

    public AeternalisFuel getAeternalisFuel() {
        return aeternalisFuel;
    }

    public DarkMatter getDarkMatter() {
        return darkMatter;
    }

    public RedMatter getRedMatter() {
        return redMatter;
    }

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
        eqItemMap.put(Utils.eqNameConfig(transmutationOrb.getItem().getItemMeta().getDisplayName()), transmutationOrb.getItemClone());
        eqItemMap.put(Utils.eqNameConfig(alchemicalCoal.getItem().getItemMeta().getDisplayName()), alchemicalCoal.getItemClone());
        eqItemMap.put(Utils.eqNameConfig(mobiusFuel.getItem().getItemMeta().getDisplayName()), mobiusFuel.getItemClone());
        eqItemMap.put(Utils.eqNameConfig(aeternalisFuel.getItem().getItemMeta().getDisplayName()), aeternalisFuel.getItemClone());
        eqItemMap.put(Utils.eqNameConfig(darkMatter.getItem().getItemMeta().getDisplayName()), darkMatter.getItemClone());
        eqItemMap.put(Utils.eqNameConfig(redMatter.getItem().getItemMeta().getDisplayName()), redMatter.getItemClone());
    }


}
