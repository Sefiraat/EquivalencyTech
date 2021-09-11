package io.github.sefiraat.equivalencytech.recipes;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.DebugLogs;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.backpacks.SlimefunBackpack;
import jdk.internal.joptsimple.internal.Strings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.StonecuttingRecipe;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmcDefinitions {

    private final Map<Material, Double> emcBase = new EnumMap<> (Material.class);
    private final Map<String, Double> emcSFBase = new HashMap<>();
    private final Map<Material, Double> emcExtended = new EnumMap<> (Material.class);
    private final Map<String, Double> emcEQ = new HashMap<>();
    private final Map<String, Double> emcSlimefun = new HashMap<>();

    public Map<Material, Double> getEmcExtended() {
        return emcExtended;
    }

    public Map<String, Double> getEmcEQ() {
        return emcEQ;
    }

    public Map<String, Double> getEmcSlimefun() {
        return emcSlimefun;
    }

    public EmcDefinitions(EquivalencyTech plugin) {
        fillBase(plugin);
        fillSpecialCases();
        fillExtended(plugin);
        fillEQItems(plugin);
        fillSlimefun(plugin);
    }

    private void fillBase(EquivalencyTech plugin) {
        Map<String, Double> h = plugin.getConfigMainClass().getEmc().getEmcBaseValues();
        for (Map.Entry<String, Double> entry : h.entrySet()) {
            if (Material.matchMaterial(entry.getKey()) == null) {
                continue;
            }
            emcBase.put(Material.matchMaterial(entry.getKey()), entry.getValue());
            DebugLogs.logEmcBaseValueLoaded(plugin, entry.getKey(), entry.getValue());
        }
        Map<String, Double> slimefunBase = plugin.getConfigMainClass().getEmc().getEmcSlimefunValues();
        for (Map.Entry<String, Double> entry : slimefunBase.entrySet()) {
            emcSFBase.put(entry.getKey(), entry.getValue());
            DebugLogs.logEmcBaseValueLoaded(plugin, entry.getKey(), entry.getValue());
        }
    }

    private void fillSpecialCases() {
        // Used to escape loops where the item isn't BASe but also has a reversing craft.
        emcExtended.put(Material.NETHERITE_INGOT, specialCaseNetheriteIngot());
        emcExtended.put(Material.DRIED_KELP, specialCaseDriedKelp());
        emcExtended.put(Material.BONE_MEAL, specialCaseBoneMeal());
    }

    private Double specialCaseNetheriteIngot() {
        return (emcBase.get(Material.GOLD_INGOT) * 4) + (emcBase.get(Material.NETHERITE_SCRAP) * 4);
    }
    private Double specialCaseDriedKelp() {
        return emcBase.get(Material.KELP);
    }
    private Double specialCaseBoneMeal() {
        return emcBase.get(Material.BONE) / 3;
    }

    private void fillExtended(EquivalencyTech plugin) {
        for (Material m : Material.values()) {
            if (!m.isLegacy() && m.isItem()) {
                ItemStack i = new ItemStack(m);
                Double emcValue = getEmcValue(plugin, i, 1);
                if (emcValue != null) {
                    DebugLogs.logEmcPosted(plugin, emcValue, 1);
                    emcExtended.put(i.getType(), roundDown(emcValue,2));
                } else {
                    DebugLogs.logEmcNull(plugin, 1);
                }
            }
        }
    }

    private void fillEQItems(EquivalencyTech plugin) {
        for (Map.Entry<List<ItemStack>, ItemStack> recipeMap : Recipes.getEQRecipes(plugin).entrySet()) {
            ItemStack checkedItem = recipeMap.getValue();
            DebugLogs.logBoring(plugin, checkedItem.getItemMeta().getDisplayName());
            Double itemAmount = 0D;
            for (ItemStack recipeItem : recipeMap.getKey()) {
                Double testAmount = getEQEmcValue(plugin, recipeItem, 1);
                if (testAmount != null) {
                    itemAmount += testAmount;
                }
            }
            DebugLogs.logBoring(plugin, checkedItem.getItemMeta().getDisplayName() + " added to EQ for : " + itemAmount);
            emcEQ.put(checkedItem.getItemMeta().getDisplayName(), roundDown(itemAmount,2));
        }
    }

    private void fillSlimefun(EquivalencyTech plugin) {
        if (EquivalencyTech.getInstance().getManagerSupportedPlugins().isInstalledSlimefun()) {
            for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
                if (item instanceof SlimefunBackpack || !item.getAddon().getName().equals("Slimefun")) {
                    continue;
                }
                DebugLogs.logBoring(plugin, item.getId() + " being checked");
                Double emcValue = getSFEmcValue(plugin, item.getItem(), 1);
                if (emcValue != null && emcValue != 0D) {
                    DebugLogs.logEmcPosted(plugin, emcValue, 1);
                    emcSlimefun.put(item.getId(), roundDown(emcValue,2));
                } else {
                    DebugLogs.logEmcNull(plugin, 1);
                }
            }
        }
    }

    private Double getSFEmcValue(EquivalencyTech plugin, ItemStack item, Integer nestLevel) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);
        if (sfItem == null) { // Vanilla
            DebugLogs.logBoring(plugin, item.getType().toString() + StringUtils.repeat(" >", nestLevel) + " Vanilla - getting found vanilla value");
            return getEmcValue(plugin, item, nestLevel + 1);
        }
        if (emcSFBase.containsKey(sfItem.getId())) {
            DebugLogs.logBoring(plugin, sfItem.getId() + StringUtils.repeat(" >", nestLevel) + " Item in SF Base");
            Double emcBaseValue = emcSFBase.get(sfItem.getId());
            if (emcBaseValue == 0) {
                DebugLogs.logBoring(plugin, sfItem.getId() + StringUtils.repeat(" >", nestLevel) + " Zero base val");
                return null;
            } else {
                DebugLogs.logEmcIsBase(plugin, emcBaseValue, nestLevel);
                return emcBaseValue;
            }
        }
        if (emcSlimefun.containsKey(sfItem.getId())) { // Is Slimefun and already calculated
            DebugLogs.logBoring(plugin, sfItem.getId() + StringUtils.repeat(" >", nestLevel) + " Already calculated at : " + emcSlimefun.get(sfItem.getId()));
            return emcSlimefun.get(sfItem.getId());
        }
        ItemStack[] recipe = sfItem.getRecipe();
        Double amount = 0D;
        if (recipe == null) {
            DebugLogs.logBoring(plugin, sfItem.getId() + StringUtils.repeat(" >", nestLevel) + " Not in base and no recipes. Nulling out.");
            return null;
        }
        for (ItemStack recipeItem : recipe) {
            if (recipeItem != null) {
                DebugLogs.logBoring(plugin, sfItem.getId() + StringUtils.repeat(" >", nestLevel) + " Checking recipe item");
                Double stackAmount = getSFEmcValue(plugin, recipeItem, nestLevel + 1);
                if (stackAmount == null) {
                    DebugLogs.logBoring(plugin, sfItem.getId() + StringUtils.repeat(" >", nestLevel) + " Null");
                    return null;
                }
                amount += (stackAmount / sfItem.getRecipeOutput().getAmount());
            }
        }
        if (amount == 0D) {
            DebugLogs.logBoring(plugin, sfItem.getId() + StringUtils.repeat(" >", nestLevel) + " Stack is null due to 0. If base items is missing?");
            return null;
        }
        return amount;
    }

    @Nullable
    private Double getEQEmcValue(EquivalencyTech plugin, ItemStack itemStack, Integer nestLevel) {
        if (itemStack != null) {
            DebugLogs.logEQStart(plugin, nestLevel, itemStack);
            if (ContainerStorage.isCraftable(itemStack, plugin)) {
                double amount = 0D;
                DebugLogs.logEQisCrafting(plugin, nestLevel);
                if (emcEQ.containsKey(itemStack.getItemMeta().getDisplayName())) {
                    amount = getEmcEQ().get(itemStack.getItemMeta().getDisplayName());
                    DebugLogs.logEmcIsRegisteredExtended(plugin, amount, nestLevel);
                } else {
                    List<ItemStack> itemStacks = Recipes.getEQRecipe(plugin, itemStack);
                    for (ItemStack itemStack1 : itemStacks) {
                        if (itemStack1 != null) {
                            Double stackAmount = getEQEmcValue(plugin, itemStack1, nestLevel + 1);
                            if (stackAmount != null) {
                                amount += stackAmount;
                            } else {
                                DebugLogs.logEmcNull(plugin, nestLevel);
                                return null;
                            }
                        }
                    }
                }
                return amount;
            } else {
                DebugLogs.logEQisNotCrafting(plugin, nestLevel);
                return getEmcValue(plugin, itemStack, nestLevel + 1);
            }
        } else {
            return 0D;
        }
    }

    @Nullable
    private Double getEmcValue(EquivalencyTech plugin, ItemStack i, Integer nestLevel) {
        List<Recipe> recipeList = Bukkit.getServer().getRecipesFor(i);
        Material m = i.getType();
        Double eVal = 0D;
        DebugLogs.logEmcTestingItemStack(plugin, i.getType().name(), nestLevel);
        if (nestLevel > 15) {
            // Recipe is most likely looping and should abort. Need a better method
            return null;
        }
        if (emcBase.containsKey(m)) {
            // Item is in the base list (config.yml) draw from there first
            Double emcBaseValue = emcBase.get(m);
            if (emcBaseValue == 0) {
                return null;
            } else {
                DebugLogs.logEmcIsBase(plugin, emcBaseValue, nestLevel);
                return emcBaseValue;
            }
        } else if (emcExtended.containsKey(m)) {
            // Item is in the extended list (already registered during fillExtended)
            DebugLogs.logEmcIsRegisteredExtended(plugin, emcExtended.get(m), nestLevel);
            return emcExtended.get(m);
        } else if (recipeList.isEmpty()) {
            // Recipe is not in Base and has no recipes, so it cannot be EMC'd
            DebugLogs.logEmcNoRecipes(plugin, nestLevel);
            return null;
        } else {
            // Item not yet registered but DOES have valid recipes, lets check them out!
            for (Recipe r : Bukkit.getServer().getRecipesFor(i)) {
                Double tempVal = checkRecipe(plugin, r,nestLevel + 1);
                if (tempVal != null && (eVal.equals(0D) || tempVal < eVal)) {
                    DebugLogs.logRecipeCheaper(plugin, nestLevel);
                    eVal = tempVal;
                } else if (tempVal != null) {
                    DebugLogs.logRecipeNotCheaper(plugin, nestLevel);
                }
            }
        }
        DebugLogs.logEmcRecipeResult(plugin, eVal, nestLevel);
        return eVal;
    }

    private Double roundDown(Double value, int places) {
        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(places, RoundingMode.DOWN);
        return decimal.doubleValue();
    }

    @Nullable
    private Double checkRecipe(EquivalencyTech plugin, Recipe recipe, Integer nestLevel) {

        DebugLogs.logCheckingRecipe(plugin, nestLevel);

        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
            return checkShaped(plugin, shapedRecipe, nestLevel);
        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
            return checkShapeless(plugin, shapelessRecipe, nestLevel);
        } else if (recipe instanceof FurnaceRecipe) {
            FurnaceRecipe furnaceRecipe = (FurnaceRecipe) recipe;
            return checkFurnace(plugin, furnaceRecipe, nestLevel);
        } else if (recipe instanceof StonecuttingRecipe) {
            StonecuttingRecipe stonecuttingRecipe = (StonecuttingRecipe) recipe;
            return checkStoneCutter(plugin, stonecuttingRecipe, nestLevel);
        } else if (recipe instanceof SmithingRecipe) {
            SmithingRecipe smithingRecipe = (SmithingRecipe) recipe;
            return checkSmithing(plugin, smithingRecipe, nestLevel);
        }

        return null;
    }

    @Nullable
    private  Double checkShaped(EquivalencyTech plugin, ShapedRecipe recipe, int nestLevel) {
        DebugLogs.logRecipeType(plugin, "Shaped", nestLevel);
        double eVal= 0D;
        for (ItemStack i2 : recipe.getIngredientMap().values()) {
            if (i2 != null) {
                Double prVal = 0D;
                if (!i2.getType().equals(Material.AIR)) {
                    prVal = getEmcValue(plugin, i2, nestLevel + 1);
                }
                if (prVal != null) {
                    if (recipe.getResult().getAmount() > 1) {
                        DebugLogs.logRecipeMultipleOutputs(plugin, prVal, recipe.getResult().getAmount(), nestLevel);
                        eVal = eVal + (prVal / recipe.getResult().getAmount());
                    } else {
                        eVal = eVal + prVal;
                    }
                } else {
                    return null;
                }
            }
        }
        return eVal;
    }

    @Nullable
    private Double checkShapeless(EquivalencyTech plugin, ShapelessRecipe recipe, int nestLevel) {
        DebugLogs.logRecipeType(plugin, "Shapeless", nestLevel);
        Double eVal = 0D;
        for (ItemStack i2 : recipe.getIngredientList()) {
            Double prVal;
            prVal = getEmcValue(plugin, i2, nestLevel + 1);
            if (prVal != null) {
                if (recipe.getResult().getAmount() > 1) {
                    DebugLogs.logRecipeMultipleOutputs(plugin, prVal, recipe.getResult().getAmount(), nestLevel);
                    eVal = eVal + (prVal / recipe.getResult().getAmount());
                } else {
                    eVal = eVal + prVal;
                }
            } else {
                return null;
            }
        }
        return eVal;
    }

    @Nullable
    private Double checkFurnace(EquivalencyTech plugin, FurnaceRecipe recipe, int nestLevel) {
        DebugLogs.logRecipeType(plugin, "Furnace", nestLevel);
        Double prVal;
        prVal = getEmcValue(plugin, recipe.getInput(), nestLevel + 1);
        if (prVal != null) {
            if (recipe.getResult().getAmount() > 1) {
                DebugLogs.logRecipeMultipleOutputs(plugin, prVal, recipe.getResult().getAmount(), nestLevel);
                return prVal / recipe.getResult().getAmount();
            } else {
                return prVal;
            }
        } else {
            return null;
        }
    }

    @Nullable
    private Double checkStoneCutter(EquivalencyTech plugin, StonecuttingRecipe recipe, int nestLevel) {
        DebugLogs.logRecipeType(plugin, "Stonecutting", nestLevel);
        Double prVal;
        prVal = getEmcValue(plugin, recipe.getInput(), nestLevel + 1);
        if (prVal != null) {
            if (recipe.getResult().getAmount() > 1) {
                DebugLogs.logRecipeMultipleOutputs(plugin, prVal, recipe.getResult().getAmount(), nestLevel);
                return prVal / recipe.getResult().getAmount();
            } else {
                return prVal;
            }
        } else {
            return null;
        }
    }

    @Nullable
    private Double checkSmithing(EquivalencyTech plugin, SmithingRecipe recipe, int nestLevel) {
        DebugLogs.logRecipeType(plugin, "Smithing", nestLevel);
        Double baseVal;
        Double additionVal;
        baseVal = getEmcValue(plugin, recipe.getBase().getItemStack(), nestLevel + 1);
        additionVal = getEmcValue(plugin, recipe.getAddition().getItemStack(), nestLevel + 1);
        if (baseVal != null && additionVal != null) {
            double combinedVal = (baseVal + additionVal);
            if (recipe.getResult().getAmount() > 1) {
                DebugLogs.logRecipeMultipleOutputs(plugin, baseVal, recipe.getResult().getAmount(), nestLevel);
                return combinedVal / recipe.getResult().getAmount();
            } else {
                return combinedVal;
            }
        } else {
            return null;
        }
    }

    @Nullable
    public Double getEmcValue(Material material) {
        if (emcExtended.containsKey(material)) {
            return emcExtended.get(material);
        }
        return null;
    }

}
