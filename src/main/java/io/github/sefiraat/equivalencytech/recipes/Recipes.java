package io.github.sefiraat.equivalencytech.recipes;

import io.github.sefiraat.equivalencytech.EquivalencyTech;;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipes {

    public Recipes(EquivalencyTech plugin) {
        addRecipes(plugin);
    }

    private void addRecipes(EquivalencyTech plugin) {
        plugin.getServer().addRecipe(Recipes.recipeCoal1(plugin));
        plugin.getServer().addRecipe(Recipes.recipeCoal2(plugin));
        plugin.getServer().addRecipe(Recipes.recipeCoal3(plugin));
        plugin.getServer().addRecipe(Recipes.recipeDarkMatter(plugin));
        plugin.getServer().addRecipe(Recipes.recipeRedMatter(plugin));
        plugin.getServer().addRecipe(Recipes.recipeTransmutationOrb(plugin));
        plugin.getServer().addRecipe(Recipes.recipeDissolutionChest(plugin));
        plugin.getServer().addRecipe(Recipes.recipeCondensatorChest(plugin));
    }

    public static Recipe recipeCoal1(EquivalencyTech plugin) {
        ItemStack i = plugin.getEqItems().getAlchemicalCoal().getItemClone();
        NamespacedKey key = new NamespacedKey(plugin, "coal1");
        ShapedRecipe r = new ShapedRecipe(key, i);
        r.shape("NNN","NN ","   ");
        r.setIngredient('N', Material.COAL);
        return r;
    }

    public static List<ItemStack> recipeCoal1Check() {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(new ItemStack(Material.COAL));
        itemStacks.add(new ItemStack(Material.COAL));
        itemStacks.add(new ItemStack(Material.COAL));
        itemStacks.add(new ItemStack(Material.COAL));
        itemStacks.add(new ItemStack(Material.COAL));
        itemStacks.add(null);
        itemStacks.add(null);
        itemStacks.add(null);
        itemStacks.add(null);
        return itemStacks;
    }

    public static Recipe recipeCoal2(EquivalencyTech plugin) {
        ItemStack i = plugin.getEqItems().getMobiusFuel().getItemClone();
        NamespacedKey key = new NamespacedKey(plugin, "coal2");
        ShapedRecipe r = new ShapedRecipe(key, i);
        r.shape("NNN","NN ","   ");
        r.setIngredient('N', Material.PLAYER_HEAD);
        return r;
    }

    public static List<ItemStack> recipeCoal2Check(EquivalencyTech plugin) {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(plugin.getEqItems().getAlchemicalCoal().getItemClone());
        itemStacks.add(plugin.getEqItems().getAlchemicalCoal().getItemClone());
        itemStacks.add(plugin.getEqItems().getAlchemicalCoal().getItemClone());
        itemStacks.add(plugin.getEqItems().getAlchemicalCoal().getItemClone());
        itemStacks.add(plugin.getEqItems().getAlchemicalCoal().getItemClone());
        itemStacks.add(null);
        itemStacks.add(null);
        itemStacks.add(null);
        itemStacks.add(null);
        return itemStacks;
    }

    public static Recipe recipeCoal3(EquivalencyTech plugin) {
        ItemStack i = plugin.getEqItems().getAeternalisFuel().getItemClone();
        NamespacedKey key = new NamespacedKey(plugin, "coal3");
        ShapedRecipe r = new ShapedRecipe(key, i);
        r.shape("NNN","NN ","   ");
        r.setIngredient('N', Material.PLAYER_HEAD);
        return r;
    }

    public static List<ItemStack> recipeCoal3Check(EquivalencyTech plugin) {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(null);
        itemStacks.add(null);
        itemStacks.add(null);
        itemStacks.add(null);
        return itemStacks;
    }


    public static Recipe recipeDarkMatter(EquivalencyTech plugin) {
        ItemStack i = plugin.getEqItems().getDarkMatter().getItemClone();
        NamespacedKey key = new NamespacedKey(plugin, "darkmatter");
        ShapedRecipe r = new ShapedRecipe(key, i);
        r.shape("NNN","NEN","NNN");
        r.setIngredient('N', Material.PLAYER_HEAD);
        r.setIngredient('E', Material.NETHERITE_BLOCK);
        return r;
    }

    public static List<ItemStack> recipeDarkMatterCheck(EquivalencyTech plugin) {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(new ItemStack(Material.NETHERITE_BLOCK));
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        return itemStacks;
    }

    public static Recipe recipeRedMatter(EquivalencyTech plugin) {
        ItemStack i = plugin.getEqItems().getDarkMatter().getItemClone();
        NamespacedKey key = new NamespacedKey(plugin, "redmatter");
        ShapedRecipe r = new ShapedRecipe(key, i);
        r.shape("NNN","EEE","NNN");
        r.setIngredient('N', Material.PLAYER_HEAD);
        r.setIngredient('E', Material.PLAYER_HEAD);
        return r;
    }

    public static List<ItemStack> recipeRedMatterCheck(EquivalencyTech plugin) {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getDarkMatter().getItemClone());
        itemStacks.add(plugin.getEqItems().getDarkMatter().getItemClone());
        itemStacks.add(plugin.getEqItems().getDarkMatter().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        return itemStacks;
    }

    public static Recipe recipeTransmutationOrb(EquivalencyTech plugin) {
        ItemStack i = plugin.getEqItems().getDarkMatter().getItemClone();
        NamespacedKey key = new NamespacedKey(plugin, "t_orb");
        ShapedRecipe r = new ShapedRecipe(key, i);
        r.shape("BDB","DRD","BDB");
        r.setIngredient('B', Material.DIAMOND_BLOCK);
        r.setIngredient('D', Material.PLAYER_HEAD);
        r.setIngredient('R', Material.PLAYER_HEAD);
        return r;
    }


    public static List<ItemStack> recipeTransmutationOrbCheck(EquivalencyTech plugin) {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(new ItemStack(Material.DIAMOND_BLOCK));
        itemStacks.add(plugin.getEqItems().getDarkMatter().getItemClone());
        itemStacks.add(new ItemStack(Material.DIAMOND_BLOCK));
        itemStacks.add(plugin.getEqItems().getDarkMatter().getItemClone());
        itemStacks.add(plugin.getEqItems().getRedMatter().getItemClone());
        itemStacks.add(plugin.getEqItems().getDarkMatter().getItemClone());
        itemStacks.add(new ItemStack(Material.DIAMOND_BLOCK));
        itemStacks.add(plugin.getEqItems().getDarkMatter().getItemClone());
        itemStacks.add(new ItemStack(Material.DIAMOND_BLOCK));
        return itemStacks;
    }

    public static List<ItemStack> recipeDissolutionChestCheck(EquivalencyTech plugin) {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(new ItemStack(Material.DIAMOND_BLOCK));
        itemStacks.add(new ItemStack(Material.CHEST));
        itemStacks.add(new ItemStack(Material.DIAMOND_BLOCK));
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getMobiusFuel().getItemClone());
        return itemStacks;
    }

    public static Recipe recipeDissolutionChest(EquivalencyTech plugin) {
        ItemStack i = plugin.getEqItems().getDarkMatter().getItemClone();
        NamespacedKey key = new NamespacedKey(plugin, "d_chest");
        ShapedRecipe r = new ShapedRecipe(key, i);
        r.shape("MMM","DCD","MMM");
        r.setIngredient('M', Material.PLAYER_HEAD);
        r.setIngredient('D', Material.DIAMOND_BLOCK);
        r.setIngredient('C', Material.CHEST);
        return r;
    }

    public static List<ItemStack> recipeCondensatorChestCheck(EquivalencyTech plugin) {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(new ItemStack(Material.NETHERITE_BLOCK));
        itemStacks.add(plugin.getEqItems().getDissolutionChest().getItemClone());
        itemStacks.add(new ItemStack(Material.NETHERITE_BLOCK));
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        itemStacks.add(plugin.getEqItems().getAeternalisFuel().getItemClone());
        return itemStacks;
    }

    public static Recipe recipeCondensatorChest(EquivalencyTech plugin) {
        ItemStack i = plugin.getEqItems().getDarkMatter().getItemClone();
        NamespacedKey key = new NamespacedKey(plugin, "c_chest");
        ShapedRecipe r = new ShapedRecipe(key, i);
        r.shape("MMM","DCD","MMM");
        r.setIngredient('M', Material.PLAYER_HEAD);
        r.setIngredient('D', Material.NETHERITE_BLOCK);
        r.setIngredient('C', Material.CHEST);
        return r;
    }

    public static ItemStack[] getSFRecipeCoal1() {
        return new ItemStack[] {
                new ItemStack(Material.COAL),   new ItemStack(Material.COAL),   new ItemStack(Material.COAL),
                new ItemStack(Material.COAL),   new ItemStack(Material.COAL),   null,
                null,                           null,                           null
        };
    }

    public static ItemStack[] getSFRecipeCoal2(SlimefunItemStack itemStack) {
        return new ItemStack[] {
                itemStack,   itemStack,   itemStack,
                itemStack,   itemStack,   null,
                null,        null,        null
        };
    }

    public static ItemStack[] getSFRecipeCoal3(SlimefunItemStack itemStack) {
        return new ItemStack[] {
                itemStack,   itemStack,   itemStack,
                itemStack,   itemStack,   null,
                null,        null,        null
        };
    }

    public static ItemStack[] getSFRecipeDarkMatter(SlimefunItemStack itemStack) {
        return new ItemStack[] {
                itemStack,   itemStack,                               itemStack,
                itemStack,   new ItemStack(Material.NETHERITE_BLOCK),   itemStack,
                itemStack,   itemStack,                               itemStack
        };
    }

    public static ItemStack[] getSFRecipeRedMatter(SlimefunItemStack fuelStack, SlimefunItemStack matterStack) {
        return new ItemStack[] {
                fuelStack,  fuelStack,  fuelStack,
                matterStack,matterStack,matterStack,
                fuelStack,  fuelStack,  fuelStack
        };
    }

    public static ItemStack[] getSFRecipeTransmutationOrb(SlimefunItemStack darkMatterStack, SlimefunItemStack redMatterStack) {
        return new ItemStack[] {
                new ItemStack(Material.DIAMOND_BLOCK),  darkMatterStack,    new ItemStack(Material.DIAMOND_BLOCK),
                darkMatterStack,                        redMatterStack,     darkMatterStack,
                new ItemStack(Material.DIAMOND_BLOCK),  darkMatterStack,    new ItemStack(Material.DIAMOND_BLOCK)
        };
    }

    public static ItemStack[] getSFRecipeDissolutionChest(SlimefunItemStack mobiusStack) {
        return new ItemStack[] {
                mobiusStack,                            mobiusStack,                    mobiusStack,
                new ItemStack(Material.DIAMOND_BLOCK),  new ItemStack(Material.CHEST),  new ItemStack(Material.DIAMOND_BLOCK),
                mobiusStack,                            mobiusStack,                    mobiusStack
        };
    }

    public static ItemStack[] getSFRecipeCondensatorChest(SlimefunItemStack mobiusStack, SlimefunItemStack disChestStack) {
        return new ItemStack[] {
                mobiusStack,                            mobiusStack,    mobiusStack,
                new ItemStack(Material.NETHERITE_BLOCK),disChestStack,  new ItemStack(Material.NETHERITE_BLOCK),
                mobiusStack,                            mobiusStack,    mobiusStack
        };
    }

    public static Map<List<ItemStack>, ItemStack> getEQRecipes(EquivalencyTech plugin) {
        Map<List<ItemStack>, ItemStack> recipes = new HashMap<>();
        recipes.put(recipeCoal1Check(), plugin.getEqItems().getAlchemicalCoal().getItemClone());
        recipes.put(recipeCoal2Check(plugin), plugin.getEqItems().getMobiusFuel().getItemClone());
        recipes.put(recipeCoal3Check(plugin), plugin.getEqItems().getAeternalisFuel().getItemClone());
        recipes.put(recipeDarkMatterCheck(plugin), plugin.getEqItems().getDarkMatter().getItemClone());
        recipes.put(recipeRedMatterCheck(plugin), plugin.getEqItems().getRedMatter().getItemClone());
        recipes.put(recipeTransmutationOrbCheck(plugin), plugin.getEqItems().getTransmutationOrb().getItemClone());
        recipes.put(recipeDissolutionChestCheck(plugin), plugin.getEqItems().getDissolutionChest().getItemClone());
        recipes.put(recipeCondensatorChestCheck(plugin), plugin.getEqItems().getCondensatorChest().getItemClone());
        return recipes;
    }

    public static List<ItemStack> getEQRecipe(EquivalencyTech plugin, ItemStack itemStack) {
        if (itemStack.equals(plugin.getEqItems().getAlchemicalCoal().getItem())) {
            return recipeCoal1Check();
        } else if (itemStack.equals(plugin.getEqItems().getMobiusFuel().getItem())) {
            return recipeCoal2Check(plugin);
        } else if (itemStack.equals(plugin.getEqItems().getAeternalisFuel().getItem())) {
            return recipeCoal3Check(plugin);
        } else if (itemStack.equals(plugin.getEqItems().getDarkMatter().getItem())) {
            return recipeDarkMatterCheck(plugin);
        } else if (itemStack.equals(plugin.getEqItems().getRedMatter().getItem())) {
            return recipeRedMatterCheck(plugin);
        } else if (itemStack.equals(plugin.getEqItems().getTransmutationOrb().getItem())) {
            return recipeTransmutationOrbCheck(plugin);
        } else if (itemStack.equals(plugin.getEqItems().getDissolutionChest().getItem())) {
            return recipeDissolutionChestCheck(plugin);
        } else if (itemStack.equals(plugin.getEqItems().getCondensatorChest().getItem())) {
            return recipeCondensatorChestCheck(plugin);
        }
        return new ArrayList<>();
    }

}
