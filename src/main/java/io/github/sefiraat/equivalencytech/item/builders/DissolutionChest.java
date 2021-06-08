package io.github.sefiraat.equivalencytech.item.builders;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.configuration.ConfigStrings;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.Messages;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DissolutionChest {

    private final ItemStack item;
    private final EquivalencyTech plugin;

    public ItemStack getItemClone() {
        return item.clone();
    }
    public ItemStack getItem() {
        return item;
    }

    public EquivalencyTech getPlugin() {
        return plugin;
    }

    public DissolutionChest(EquivalencyTech plugin) {

        this.plugin = plugin;

        ConfigStrings c = plugin.getConfigMainClass().getStrings();

        item = new ItemStack(Material.CHEST);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Messages.THEME_ITEM_NAME_GENERAL + c.getItemDissolutionChestName());
        List<String> lore = new ArrayList<>();
        for (String s : c.getItemDissolutionChestLore()) {
            lore.add(Messages.THEME_PASSIVE_GRAY + s);
        }
        im.setLore(lore);
        item.setItemMeta(im);

        ContainerStorage.setItemID(item, plugin,"DCHEST");
        ContainerStorage.makeDisChest(item, plugin);
        ContainerStorage.makeCraftable(item, plugin);

    }
}
