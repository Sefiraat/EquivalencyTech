package io.github.sefiraat.equivalencytech.misc;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.Messages;
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
        if (ContainerStorage.isCrafting(itemStack, plugin)) {
            ItemStack eqStack = plugin.getEqItems().getEqItemMap().get(itemStack.getItemMeta().getDisplayName());
            return plugin.getEmcDefinitions().getEmcEQ().get(eqStack.getItemMeta().getDisplayName());
        } else {
            return plugin.getEmcDefinitions().getEmcExtended().get(itemStack.getType());
        }
    }

    public static String toTitleCase(String string) {
        final char[] delimiters = { ' ', '_' };
        return WordUtils.capitalizeFully(string, delimiters);
    }

    public static String materialFriendlyName(Material m) {
        return toTitleCase(m.name().replace("_", " "));
    }

    public static void givePlayerOrb(EquivalencyTech plugin, Player player) {
        player.getPlayer().getInventory().addItem(plugin.getEqItems().getTransmutationOrb().getItemClone());
        player.getPlayer().sendMessage(Messages.messageCommandOrbGiven(plugin));
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

}
