package io.github.sefiraat.equivalencytech.items;

import dev.dbassett.skullcreator.SkullCreator;
import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.configuration.ConfigStrings;
import io.github.sefiraat.equivalencytech.statics.Colours;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.SkullTextures;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MobiusFuel {

    private final ItemStack item;

    public ItemStack getClone() {
        return item.clone();
    }
    public ItemStack getItem() {
        return item;
    }

    private final EquivalencyTech plugin;
    public EquivalencyTech getPlugin() {
        return plugin;
    }

    public MobiusFuel(EquivalencyTech plugin) {

        this.plugin = plugin;

        ConfigStrings c = plugin.getConfigClass().getStrings();

        item = SkullCreator. itemFromBase64(SkullTextures.ITEM_MOBIUS_FUEL);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Colours.THEME_ITEM_NAME_GENERAL + c.getItemMobiusFuelName());
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + c.getGeneralCraftingItem());
        im.setLore(lore);
        item.setItemMeta(im);

        ContainerStorage.makeMobiusFuel(item, plugin);
        ContainerStorage.makeCrafting(item, plugin);

    }

}
