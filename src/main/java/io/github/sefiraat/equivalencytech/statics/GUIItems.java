package io.github.sefiraat.equivalencytech.statics;

import dev.dbassett.skullcreator.SkullCreator;
import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.configuration.ConfigStrings;
import io.github.sefiraat.equivalencytech.misc.Utils;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIItems {

    private GUIItems() {
        throw new IllegalStateException("Utility class");
    }

    public static GuiItem guiOrbInfo(EquivalencyTech plugin, Player player) {
        GuiItem g = new GuiItem(SkullCreator.itemFromBase64(SkullTextures.GUI_INFO_ALL));
        ItemStack i = g.getItemStack();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(guiDisplayNameInfo(plugin));
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.setLore(guiDisplayLoreInfo(plugin, player, Config.getLearnedItems(plugin, player).size(), plugin.getEmcDefinitions().getEmcExtended().size()));
        i.setItemMeta(im);
        g.setItemStack(i);
        g.setAction(event -> event.setCancelled(true));
        return g;
    }

    public static GuiItem guiOrbBorder(EquivalencyTech plugin) {
        GuiItem g = new GuiItem(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack i = g.getItemStack();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Colours.THEME_PASSIVE_GRAY + plugin.getConfigClass().getStrings().getGuiBorderName());
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        g.setItemStack(i);
        g.setAction(event -> event.setCancelled(true));
        return g;
    }

    public static GuiItem guiOrbFiller(EquivalencyTech plugin) {
        GuiItem g = new GuiItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemStack i = g.getItemStack();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Colours.THEME_PASSIVE_GRAY + plugin.getConfigClass().getStrings().getGuiFillerName());
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        g.setItemStack(i);
        g.setAction(event -> event.setCancelled(true));
        return g;
    }

    public static GuiItem guiEMCItem(EquivalencyTech plugin, Material material) {

        GuiItem g = new GuiItem(material);
        ItemStack i = g.getItemStack();
        ItemMeta im = i.getItemMeta();
        String itemName = Utils.materialFriendlyName(material);

        im.setDisplayName(ChatColor.WHITE + itemName);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        im.setLore(getEmcItemLore(plugin, material));
        i.setItemMeta(im);
        g.setItemStack(i);
        return g;
    }

    public static GuiItem guiEMCItem(EquivalencyTech plugin, ItemStack itemStack) {

        GuiItem g = new GuiItem(itemStack);
        ItemMeta im = itemStack.getItemMeta();

        im.setDisplayName(ChatColor.WHITE + im.getDisplayName());
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        im.setLore(getEmcItemLore(plugin, itemStack));
        itemStack.setItemMeta(im);
        g.setItemStack(itemStack);
        return g;
    }

    public static List<String> getEmcItemLore(EquivalencyTech plugin, Material material) {
        ConfigStrings c = plugin.getConfigClass().getStrings();
        List<String> lore = new ArrayList<>();
        lore.add(Colours.THEME_EMC_PURPLE + "EMC: " + Utils.getEMC(plugin, material));
        lore.add("");
        lore.add(Colours.THEME_CLICK_INSTRUCTION + "Left Click: " + ChatColor.WHITE + c.getGuiEntryLeftClick());
        lore.add(Colours.THEME_CLICK_INSTRUCTION + "Right Click: " + ChatColor.WHITE + c.getGuiEntryRightClick());
        return lore;
    }


    public static List<String> getEmcItemLore(EquivalencyTech plugin, ItemStack itemStack) {
        ConfigStrings c = plugin.getConfigClass().getStrings();
        List<String> lore = new ArrayList<>();
        lore.add(Colours.THEME_EMC_PURPLE + "EMC: " + Utils.getEmcEq(plugin, itemStack));
        lore.add("");
        lore.add(Colours.THEME_CLICK_INSTRUCTION + "Left Click: " + ChatColor.WHITE + c.getGuiEntryLeftClick());
        lore.add(Colours.THEME_CLICK_INSTRUCTION + "Right Click: " + ChatColor.WHITE + c.getGuiEntryRightClick());
        return lore;
    }

    public static String guiDisplayNameInfo(EquivalencyTech plugin) {
        return ChatColor.RED + plugin.getConfigClass().getStrings().getGuiInfoName();
    }

    public static List<String> guiDisplayLoreInfo(EquivalencyTech plugin, Player player, int recipesKnown, int recipesTotal) {
        List<String> l = new ArrayList<>();
        l.add("" + ChatColor.GOLD + ChatColor.BOLD + plugin.getConfigClass().getStrings().getGuiInfoRecipes() + ": " + ChatColor.WHITE + recipesKnown + "/" + recipesTotal);
        l.add("");
        l.add("" + ChatColor.GOLD + ChatColor.BOLD + "EMC: " + ChatColor.WHITE + Config.getPlayerEmc(plugin, player));
        return l;
    }

}
