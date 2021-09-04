package io.github.sefiraat.equivalencytech.misc;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.item.builders.CondensatorChest;
import io.github.sefiraat.equivalencytech.item.builders.DissolutionChest;
import io.github.sefiraat.equivalencytech.item.builders.TransmutationOrb;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.Messages;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static Double getEMC(EquivalencyTech plugin, ItemStack itemStack) {
        SlimefunItem sfItem = null;
        if (EquivalencyTech.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
            sfItem = SlimefunItem.getByItem(itemStack);
        }
        if (sfItem != null) {
            return plugin.getEmcDefinitions().getEmcSlimefun().get(sfItem.getId());
        }
        if (ContainerStorage.isCraftable(itemStack, plugin)) {
            ItemStack eqStack = plugin.getEqItems().getEqItemMap().get(eqNameConfig(itemStack.getItemMeta().getDisplayName()));
            return plugin.getEmcDefinitions().getEmcEQ().get(eqStack.getItemMeta().getDisplayName());
        } else {
            return plugin.getEmcDefinitions().getEmcExtended().get(itemStack.getType());
        }
    }

    public static String eqNameConfig(String name) {
        return ChatColor.stripColor(name.replace(" ","_"));
    }

    public static String toTitleCase(String string) {
        final char[] delimiters = { ' ', '_' };
        return WordUtils.capitalizeFully(string, delimiters);
    }

    public static String materialFriendlyName(Material m) {
        return toTitleCase(m.name().replace("_", " "));
    }

    public static void givePlayerOrb(EquivalencyTech plugin, Player player) {
        TransmutationOrb i = plugin.getEqItems().getTransmutationOrb();
        player.getPlayer().getInventory().addItem(i.getItemClone());
        player.getPlayer().sendMessage(Messages.messageCommandItemGiven(plugin, i.getItem().getItemMeta().getDisplayName()));
    }

    public static void givePlayerDChest(EquivalencyTech plugin, Player player) {
        DissolutionChest i = plugin.getEqItems().getDissolutionChest();
        player.getPlayer().getInventory().addItem(i.getItemClone());
        player.getPlayer().sendMessage(Messages.messageCommandItemGiven(plugin, i.getItem().getItemMeta().getDisplayName()));
    }

    public static void givePlayerCChest(EquivalencyTech plugin, Player player) {
        CondensatorChest i = plugin.getEqItems().getCondensatorChest();
        player.getPlayer().getInventory().addItem(i.getItemClone());
        player.getPlayer().sendMessage(Messages.messageCommandItemGiven(plugin, i.getItem().getItemMeta().getDisplayName()));
    }

    public static double roundDown(double number, int places) {
        BigDecimal value = BigDecimal.valueOf(number);
        value = value.setScale(places, RoundingMode.DOWN);
        return value.doubleValue();
    }

    public static int totalRecipes(EquivalencyTech plugin) {
        int recExtended = plugin.getEmcDefinitions().getEmcExtended().size();
        int recEQ = plugin.getEmcDefinitions().getEmcEQ().size();
        return recExtended + recEQ;
    }

    public static boolean canBeSynth(EquivalencyTech plugin, ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            SlimefunItem sfItem = null;
            if (EquivalencyTech.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
                sfItem = SlimefunItem.getByItem(itemStack);
            }
            return ContainerStorage.isCraftable(itemStack, plugin) || sfItem != null;
        } else {
            return true;
        }
    }

}
