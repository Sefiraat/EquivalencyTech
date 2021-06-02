package io.github.sefiraat.equivalencytech.item.builders;

import dev.dbassett.skullcreator.SkullCreator;
import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.configuration.ConfigStrings;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.Messages;
import io.github.sefiraat.equivalencytech.statics.SkullTextures;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AlchemicalCoal {

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

    public AlchemicalCoal(EquivalencyTech plugin) {

        this.plugin = plugin;

        ConfigStrings c = plugin.getConfigMainClass().getStrings();

        item = SkullCreator. itemFromBase64(SkullTextures.ITEM_ALCHEMICAL_COAL);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Messages.THEME_ITEM_NAME_GENERAL + c.getItemAlchemicalCoalName());
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + c.getGeneralCraftingItem());
        im.setLore(lore);
        item.setItemMeta(im);

        ContainerStorage.makeAlchemicalCoal(item, plugin);
        ContainerStorage.makeCraftable(item, plugin);

    }

}
