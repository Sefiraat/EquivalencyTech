package io.github.sefiraat.equivalencytech.misc;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.DebugLogging;
import io.github.sefiraat.equivalencytech.statics.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmcDefinitions {

    private final Map<Material, Double> emcBase = new EnumMap<> (Material.class);
    public Map<Material, Double> getEmcBase() {
        return emcBase;
    }
    private final Map<Material, Double> emcExtended = new EnumMap<> (Material.class);
    public Map<Material, Double> getEmcExtended() {
        return emcExtended;
    }
    private final Map<String, Double> emcEQ = new HashMap<>();
    public Map<String, Double> getEmcEQ() {
        return emcEQ;
    }

    public EmcDefinitions(EquivalencyTech plugin) {
        fillBase(plugin);
        fillSpecialCases();
        fillExtended(plugin);
        fillEQItems(plugin);
    }

    private void fillBase(EquivalencyTech plugin) {
        Map<String, Double> h = plugin.getConfigClass().getEmc().getEmcBaseValues();
        for (Map.Entry<String, Double> entry : h.entrySet()) {
            if (entry.getValue() > 0) {
                emcBase.put(Material.matchMaterial(entry.getKey()), entry.getValue());
                DebugLogging.logEmcBaseValueLoaded(plugin, entry.getKey(), entry.getValue());
            }
        }
    }

    private void fillSpecialCases() {
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
                    DebugLogging.logEmcPosted(plugin, emcValue, 1);
                    emcExtended.put(i.getType(), roundDown(emcValue,2));
                } else {
                    DebugLogging.logEmcNull(plugin, 1);
                }
            }
        }
    }

    private void fillEQItems(EquivalencyTech plugin) {
        for (Map.Entry<List<ItemStack>, ItemStack> recipeMap : Recipes.getEQRecipes(plugin).entrySet()) {
            ItemStack checkedItem = recipeMap.getValue();
            DebugLogging.logBoring(plugin, checkedItem.getItemMeta().getDisplayName());
            Double itemAmount = 0D;
            for (ItemStack recipeItem : recipeMap.getKey()) {
                Double testAmount = getEQEmcValue(plugin, recipeItem, 1);
                if (testAmount != null) {
                    itemAmount += testAmount;
                }
            }
            DebugLogging.logBoring(plugin, checkedItem.getItemMeta().getDisplayName() + " added to EQ for : " + itemAmount);
            emcEQ.put(checkedItem.getItemMeta().getDisplayName(), roundDown(itemAmount,2));
        }
    }

    @Nullable
    private Double getEQEmcValue(EquivalencyTech plugin, ItemStack itemStack, Integer nestLevel) {
        if (itemStack != null) {
            DebugLogging.logEQStart(plugin, nestLevel, itemStack);
            if (ContainerStorage.isCrafting(itemStack, plugin)) {
                double amount = 0D;
                DebugLogging.logEQisCrafting(plugin, nestLevel);
                if (emcEQ.containsKey(itemStack.getItemMeta().getDisplayName())) {
                    amount = getEmcEQ().get(itemStack.getItemMeta().getDisplayName());
                    DebugLogging.logEmcIsRegisteredExtended(plugin, amount, nestLevel);
                } else {
                    List<ItemStack> itemStacks = Recipes.getEQRecipe(plugin, itemStack);
                    for (ItemStack itemStack1 : itemStacks) {
                        if (itemStack1 != null) {
                            Double stackAmount = getEQEmcValue(plugin, itemStack1, nestLevel + 1);
                            if (stackAmount != null) {
                                amount += stackAmount;
                            } else {
                                DebugLogging.logEmcNull(plugin, nestLevel);
                                return null;
                            }
                        }
                    }
                }
                return amount;
            } else {
                DebugLogging.logEQisNotCrafting(plugin, nestLevel);
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
        DebugLogging.logEmcTestingItemStack(plugin, i.getType().name(), nestLevel);
        if (emcBase.containsKey(m)) {
            eVal = emcBase.get(m);
            DebugLogging.logEmcIsBase(plugin, eVal, nestLevel);
        } else if (emcExtended.containsKey(m)) {
            eVal = emcExtended.get(m);
            DebugLogging.logEmcIsRegisteredExtended(plugin, eVal, nestLevel);
        } else if (recipeList.isEmpty()) {
            DebugLogging.logEmcNoRecipes(plugin, nestLevel);
            return null;
        } else {

            for (Recipe r : Bukkit.getServer().getRecipesFor(i)) {
                Double tempVal = checkRecipe(plugin, r,nestLevel + 1);
                if (tempVal != null && (eVal.equals(0D) || tempVal < eVal)) {
                    DebugLogging.logRecipeCheaper(plugin, nestLevel);
                    eVal = tempVal;
                } else if (tempVal != null) {
                    DebugLogging.logRecipeNotCheaper(plugin, nestLevel);
                }
            }

        }
        DebugLogging.logEmcRecipeResult(plugin, eVal, nestLevel);
        return eVal;
    }

    private Double roundDown(Double value, int places) {
        BigDecimal decimal = new BigDecimal(value);
        decimal = decimal.setScale(places, RoundingMode.DOWN);
        return decimal.doubleValue();
    }

    @Nullable
    private Double checkRecipe(EquivalencyTech plugin, Recipe recipe, Integer nestLevel) {

        double eVal = 0D;

        DebugLogging.logCheckingRecipe(plugin, nestLevel);

        if (recipe instanceof ShapedRecipe) {

            DebugLogging.logRecipeType(plugin, "Shaped", nestLevel);
            ShapedRecipe r = (ShapedRecipe) recipe;
            for (ItemStack i2 : r.getIngredientMap().values()) {
                if (i2 != null) {
                    Double prVal = 0D;
                    if (!i2.getType().equals(Material.AIR)) {
                        prVal = getEmcValue(plugin, i2, nestLevel + 1);
                    }
                    if (prVal != null) {
                        if (r.getResult().getAmount() > 1) {
                            DebugLogging.logRecipeMultipleOutputs(plugin, prVal, r.getResult().getAmount(), nestLevel);
                            eVal = eVal + (prVal / r.getResult().getAmount());
                        } else {
                            eVal = eVal + prVal;
                        }
                    } else {
                        return null;
                    }
                }
            }

        } else if (recipe instanceof ShapelessRecipe) {

            DebugLogging.logRecipeType(plugin, "Shapeless", nestLevel);
            ShapelessRecipe r = (ShapelessRecipe) recipe;
            for (ItemStack i2 : r.getIngredientList()) {
                Double prVal;
                prVal = getEmcValue(plugin, i2, nestLevel + 1);
                if (prVal != null) {
                    if (r.getResult().getAmount() > 1) {
                        DebugLogging.logRecipeMultipleOutputs(plugin, prVal, r.getResult().getAmount(), nestLevel);
                        eVal = eVal + (prVal / r.getResult().getAmount());
                    } else {
                        eVal = eVal + prVal;
                    }
                } else {
                    return null;
                }
            }

        } else if (recipe instanceof FurnaceRecipe) {

            DebugLogging.logRecipeType(plugin, "Furnace", nestLevel);
            FurnaceRecipe r = (FurnaceRecipe) recipe;
            Double prVal;
            prVal = getEmcValue(plugin, r.getInput(), nestLevel + 1);
            if (prVal != null) {
                if (r.getResult().getAmount() > 1) {
                    DebugLogging.logRecipeMultipleOutputs(plugin, prVal, r.getResult().getAmount(), nestLevel);
                    return prVal / r.getResult().getAmount();
                } else {
                    return prVal;
                }
            } else {
                return null;
            }
        } else if (recipe instanceof StonecuttingRecipe) {

            DebugLogging.logRecipeType(plugin, "Stonecutting", nestLevel);
            StonecuttingRecipe r = (StonecuttingRecipe) recipe;
            Double prVal;
            prVal = getEmcValue(plugin, r.getInput(), nestLevel + 1);
            if (prVal != null) {
                if (r.getResult().getAmount() > 1) {
                    DebugLogging.logRecipeMultipleOutputs(plugin, prVal, r.getResult().getAmount(), nestLevel);
                    return prVal / r.getResult().getAmount();
                } else {
                    return prVal;
                }
            } else {
                return null;
            }
        }

        return eVal;
    }

    @Nullable
    public Double getEmcValue(Material material) {
        if (emcExtended.containsKey(material)) {
            return emcExtended.get(material);
        }
        return null;
    }

}
