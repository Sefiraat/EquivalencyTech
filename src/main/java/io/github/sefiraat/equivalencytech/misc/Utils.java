package io.github.sefiraat.equivalencytech.misc;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
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

    public static Double getEMC(EquivalencyTech plugin, Material m) {
        return plugin.getEmcDefinitions().getEmcExtended().get(m);
    }

    public static Double getEmcEq(EquivalencyTech plugin, ItemStack i) {
        return plugin.getEmcDefinitions().getEmcEQ().get(i.getItemMeta().getDisplayName());
    }

    public static String toTitleCase(String string) {
        final char[] delimiters = { ' ', '_' };
        return WordUtils.capitalizeFully(string, delimiters);
    }

    public static String materialFriendlyName(Material m) {
        return toTitleCase(m.name().replace("_", " "));
    }

    public static void givePlayerOrb(EquivalencyTech plugin, Player player) {
        player.getPlayer().getInventory().addItem(plugin.getEqItems().getTransmutationOrb().getClone());
        player.getPlayer().sendMessage(Messages.messageCommandOrbGiven(plugin));
    }

    public static double roundDown(double number, int places) {
        BigDecimal value = new BigDecimal(number);
        value = value.setScale(places, RoundingMode.DOWN);
        return value.doubleValue();
    }

    public static int totalRecipes(EquivalencyTech plugin) {
        int recExtended = plugin.getEmcDefinitions().getEmcExtended().size();
        int recEQ = plugin.getEmcDefinitions().getEmcEQ().size();
        return recExtended + recEQ;
    }

}
