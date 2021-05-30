package io.github.sefiraat.equivalencytech.misc;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.statics.DebugLogging;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;

import javax.annotation.Nullable;
import java.util.EnumMap;
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

    public EmcDefinitions(EquivalencyTech plugin) {
        fillBase(plugin);
        fillSpecialCases();
        fillExtended(plugin);
    }

    private void fillBase(EquivalencyTech plugin) {
        Map<String, Double> h = plugin.getConfigClass().getEmc().getEmcBaseValues();
        for (Map.Entry<String, Double> entry : h.entrySet()) {
            emcBase.put(Material.matchMaterial(entry.getKey()), entry.getValue());
            DebugLogging.logEmcBaseValueLoaded(plugin, entry.getKey(), entry.getValue());
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
                    emcExtended.put(i.getType(), emcValue);
                } else {
                    DebugLogging.logEmcNull(plugin, 1);
                }
            }
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
