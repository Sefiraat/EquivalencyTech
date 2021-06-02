package io.github.sefiraat.equivalencytech.statics;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.configuration.ConfigMain;
import io.github.sefiraat.equivalencytech.misc.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class Messages {

    public static final String PREFIX = "" + ChatColor.GRAY + "[EMCTech] ";
    public static final String SUFFIX = "" + ChatColor.GRAY + "";

    public static final ChatColor THEME_WARNING = ChatColor.YELLOW;
    public static final ChatColor THEME_ERROR = ChatColor.RED;
    public static final ChatColor THEME_NOTICE = ChatColor.WHITE;
    public static final ChatColor THEME_PASSIVE = ChatColor.GRAY;
    public static final ChatColor THEME_SUCCESS = ChatColor.GREEN;
    public static final ChatColor THEME_EMC_PURPLE = ChatColor.of("#5d2999");
    public static final ChatColor THEME_ITEM_NAME_GENERAL = ChatColor.of("#cfab1d");
    public static final ChatColor THEME_PASSIVE_GRAY = ChatColor.of("#a3a3a3");
    public static final ChatColor THEME_CLICK_INSTRUCTION = ChatColor.of("#cfab1d");
    public static final ChatColor THEME_PASSIVE_CONGRATULATE = ChatColor.of("#fff41f");

    private Messages() {
        throw new IllegalStateException("Utility class");
    }

    // region Commands

    public static String msgCmdSubcommand(EquivalencyTech plugin) {
        return PREFIX + THEME_NOTICE + plugin.getConfigMainClass().getStrings().getCommandSubcommand();
    }
    public static String msgCmdEmcMustHold(EquivalencyTech plugin) {
        return PREFIX + THEME_WARNING + plugin.getConfigMainClass().getStrings().getCommandEmcMustHold();
    }
    public static String msgCmdEmcNone(EquivalencyTech plugin) {
        return PREFIX + THEME_WARNING + plugin.getConfigMainClass().getStrings().getCommandEmcNone();
    }
    public static String msgCmdEmcDisplay(Material m, Double emc) {
        return PREFIX + THEME_WARNING + Utils.materialFriendlyName(m) + " x 1 = EMC " + emc;
    }
    public static String msgCmdEmcDisplay(String s, Double emc) {
        return PREFIX + THEME_WARNING + s + " x 1 = EMC " + emc;
    }
    public static String msgCmdEmcDisplayStack(Material m, Integer amount, Double emc) {
        return PREFIX + THEME_WARNING + Utils.materialFriendlyName(m) + " x " + amount + " = EMC " + emc;
    }
    public static String msgCmdEmcDisplayStack(String s, Integer amount, Double emc) {
        return PREFIX + THEME_WARNING + s + " x " + amount + " = EMC " + emc;
    }
    public static String messageCommandSelectItem(EquivalencyTech plugin) {
        return PREFIX + THEME_NOTICE + plugin.getConfigMainClass().getStrings().getCommandSelectItem();
    }

    public static String messageCommandOrbGiven(EquivalencyTech plugin) {
        return PREFIX + THEME_NOTICE + plugin.getConfigMainClass().getStrings().getCommandOrbGiven();
    }

    public static String messageCommandEmc(EquivalencyTech plugin, Player player) {
        return PREFIX + THEME_NOTICE + "You have " + THEME_SUCCESS + ConfigMain.getPlayerEmc(plugin, player) + THEME_NOTICE + " EMC.";
    }

    // endregion

    // region GUI

    public static String messageGuiItemLearned(EquivalencyTech plugin) {
        return PREFIX + THEME_PASSIVE_CONGRATULATE + plugin.getConfigMainClass().getStrings().getGuiItemLearned();
    }

    public static String messageGuiEmcGiven(EquivalencyTech plugin, Player player, double emcBase, double emcTotal, int itemAmt, int burnRate) {
        return PREFIX + THEME_SUCCESS + "+" + emcTotal + " EMC " + THEME_PASSIVE + "(" + emcBase + " * " + itemAmt + ")" + THEME_ERROR + " burn rate = " + burnRate + "%" + THEME_NOTICE + " : [EMC : " + ConfigMain.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiEmcRemoved(EquivalencyTech plugin, Player player, double emcBase, double emcTotal, int itemAmt) {
        return PREFIX + THEME_ERROR + "-" + emcTotal + " EMC " + THEME_PASSIVE + "(" + emcBase + " * " + itemAmt + ") : " + THEME_NOTICE + " [EMC : " + ConfigMain.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiEmcNotEnough(EquivalencyTech plugin, Player player) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getGuiNotEnoughEmc() + THEME_NOTICE + " [EMC : " + ConfigMain.getPlayerEmc(plugin, player) + "]";
    }

    public static String messageGuiNoSpace(EquivalencyTech plugin) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getGeneralNoInvSpace();
    }

    public static String messageGuiItemMeta(EquivalencyTech plugin) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getGuiItemMeta();
    }

    // endregion

    // region Events

    public static String messageEventDChestMisPlace(EquivalencyTech plugin) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getEventDisAdjPlacement();
    }

    public static String messageEventCantOpenNotOwner(EquivalencyTech plugin) {
        return PREFIX + THEME_ERROR + plugin.getConfigMainClass().getStrings().getEventCantOpenNotOwner();
    }

    // endregion

}
