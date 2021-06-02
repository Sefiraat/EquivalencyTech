package io.github.sefiraat.equivalencytech.statics;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public final class DebugLogs {

    private DebugLogs() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NEST = "  |";

    // Logs
    public static void logEmcBaseValueLoaded(EquivalencyTech plugin, String materialName, Double emcValue) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"EMC BASE LOADED : {0} : {1}", new Object[]{materialName, emcValue});
        }
    }

    public static void logEmcTestingItemStack(EquivalencyTech plugin, String itemStackName, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}EMC TESTING : {1}", new Object[]{NEST.repeat(nestLevel), itemStackName});
        }
    }

    public static void logEmcNoRecipes(EquivalencyTech plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}No recipes - not condensable", NEST.repeat(nestLevel));
        }
    }

    public static void logEmcIsBase(EquivalencyTech plugin, Double emcValue, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Is Base : passing EMC value up : {1}", new Object[]{NEST.repeat(nestLevel), emcValue});
        }
    }

    public static void logEmcIsRegisteredExtended(EquivalencyTech plugin, Double emcValue, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Is Registered in extended : passing EMC value up : {1}", new Object[]{NEST.repeat(nestLevel), emcValue});
        }
    }

    public static void logCheckingRecipe(EquivalencyTech plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe found : starting recursion.", NEST.repeat(nestLevel));
        }
    }

    public static void logEmcPosted(EquivalencyTech plugin, Double emcValue, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}All items completed : EMC : {1}", new Object[]{NEST.repeat(nestLevel), emcValue});
        }
    }

    public static void logEmcNull(EquivalencyTech plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}All items completed : One or more items returned null base EMC. Item not added.", NEST.repeat(nestLevel));
        }
    }

    public static void logRecipeType(EquivalencyTech plugin, String type, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe type : {1}", new Object[]{NEST.repeat(nestLevel), type});
        }
    }

    public static void logRecipeMultipleOutputs(EquivalencyTech plugin, Double emcTotal, Integer outputVolume, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe has multiple output items. Using : ({1}/{2}", new Object[]{NEST.repeat(nestLevel), emcTotal, outputVolume});
        }
    }

    public static void logEmcRecipeResult(EquivalencyTech plugin, Double emcValue, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe resulted in EMC of: {1}", new Object[]{NEST.repeat(nestLevel), emcValue});
        }
    }

    public static void logRecipeCheaper(EquivalencyTech plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe is either first/only or cheapest found so far.", NEST.repeat(nestLevel));
        }
    }

    public static void logRecipeNotCheaper(EquivalencyTech plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}Recipe is valid but a previous recipe is cheaper - this recipe ignored.", NEST.repeat(nestLevel));
        }
    }

    public static void logEQStart(EquivalencyTech plugin, Integer nestLevel, ItemStack itemStack) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}EQ Recipe check starting : ItemStack is : {1}", new Object[]{NEST.repeat(nestLevel), itemStack.getType()});
        }
    }

    public static void logEQisCrafting(EquivalencyTech plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}ItemStack is a EQ craftable - ", new Object[]{NEST.repeat(nestLevel), });
        }
    }

    public static void logEQisNotCrafting(EquivalencyTech plugin, Integer nestLevel) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,"{0}ItemStack is NOT EQ craftable - moving to extended/base - ", new Object[]{NEST.repeat(nestLevel)});
        }
    }

    public static void logBoring(EquivalencyTech plugin, String string) {
        if (plugin.getConfigMainClass().getBools().getDebuggingLogs()) {
            plugin.getLogger().log(Level.INFO,string);
        }
    }

}
