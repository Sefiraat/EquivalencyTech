package io.github.sefiraat.equivalencytech.statics;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.misc.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class Messages {

    private Messages() {
        throw new IllegalStateException("Utility class");
    }

    // General
    public static final String PREFIX = "" + ChatColor.GRAY + "[EMCTech] ";
    public static final String SUFFIX = "" + ChatColor.GRAY + "";

    public static final String WARNING = "" + ChatColor.YELLOW;
    public static final String ERROR = "" + ChatColor.RED;
    public static final String NOTICE = "" + ChatColor.WHITE;
    public static final String PASSIVE = "" + ChatColor.GRAY;
    public static final String SUCCESS = "" + ChatColor.GREEN;

    public static final String ID = " - ID: ";

    // Commands
    public static String msgCmdSubcommand(EquivalencyTech plugin) {
        return PREFIX + NOTICE + plugin.getConfigClass().getStrings().getCommandSubcommand();
    }
    public static String msgCmdEmcMustHold(EquivalencyTech plugin) {
        return PREFIX + WARNING + plugin.getConfigClass().getStrings().getCommandEmcMustHold();
    }
    public static String msgCmdEmcNone(EquivalencyTech plugin) {
        return PREFIX + WARNING + plugin.getConfigClass().getStrings().getCommandEmcNone();
    }
    public static String msgCmdEmcDisplay(Material m, Double emc) {
        return PREFIX + WARNING + Utils.materialFriendlyName(m) + " x 1 = EMC " + emc;
    }
    public static String msgCmdEmcDisplay(String s, Double emc) {
        return PREFIX + WARNING + s + " x 1 = EMC " + emc;
    }
    public static String msgCmdEmcDisplayStack(Material m, Integer amount, Double emc) {
        return PREFIX + WARNING + Utils.materialFriendlyName(m) + " x " + amount + " = EMC " + emc;
    }
    public static String msgCmdEmcDisplayStack(String s, Integer amount, Double emc) {
        return PREFIX + WARNING + s + " x " + amount + " = EMC " + emc;
    }
    public static String messageCommandSelectItem(EquivalencyTech plugin) {
        return PREFIX + NOTICE + plugin.getConfigClass().getStrings().getCommandSelectItem();
    }

    public static String messageCommandOrbGiven(EquivalencyTech plugin) {
        return PREFIX + NOTICE + plugin.getConfigClass().getStrings().getCommandOrbGiven();
    }

    public static String messageCommandEmc(EquivalencyTech plugin, Player player) {
        return PREFIX + NOTICE + "You have " + SUCCESS + Config.getPlayerEmc(plugin, player) + NOTICE + " EMC.";
    }

    // GUI

    public static String messageGuiItemLearned(EquivalencyTech plugin) {
        return PREFIX + Colours.THEME_PASSIVE_CONGRATULATE + plugin.getConfigClass().getStrings().getGuiItemLearned();
    }

    public static String messageGuiEmcGiven(EquivalencyTech plugin, Player player, double emcBase, double emcTotal, int itemAmt) {
        return PREFIX + SUCCESS + "+" + emcTotal + " EMC " + PASSIVE + "(" + emcBase + " * " + itemAmt + ") : " + NOTICE + " [EMC : " + Config.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiEmcRemoved(EquivalencyTech plugin, Player player, double emcBase, double emcTotal, int itemAmt) {
        return PREFIX + ERROR + "-" + emcTotal + " EMC " + PASSIVE + "(" + emcBase + " * " + itemAmt + ") : " + NOTICE + " [EMC : " + Config.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiEmcNotEnough(EquivalencyTech plugin, Player player) {
        return PREFIX + ERROR + plugin.getConfigClass().getStrings().getGuiNotEnoughEmc() + NOTICE + " [EMC : " + Config.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiNoSpace(EquivalencyTech plugin) {
        return PREFIX + ERROR + plugin.getConfigClass().getStrings().getGeneralNoInvSpace();
    }

    public static String messageGuiItemMeta(EquivalencyTech plugin) {
        return PREFIX + ERROR + plugin.getConfigClass().getStrings().getGuiItemMeta();
    }

}
