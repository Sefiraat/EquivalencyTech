package io.github.sefiraat.equivalencytech.misc;

import dev.dbassett.skullcreator.SkullCreator;
import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.statics.Recipes;
import io.github.sefiraat.equivalencytech.statics.SkullTextures;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.recipes.MinecraftRecipe;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;


public class SlimefunEQAddon implements SlimefunAddon {

    private final EquivalencyTech plugin;

    public SlimefunEQAddon(EquivalencyTech plugin) {

        this.plugin = plugin;

        // Category
        NamespacedKey categoryIdMain = new NamespacedKey(plugin, "danktech_main");

        ItemStack categoryItemMain = SkullCreator.itemFromBase64(SkullTextures.ITEM_TRANSMUTATION_ORB);
        ItemMeta im = categoryItemMain.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "EquivalencyTech");
        im.setLore(Collections.singletonList("&a> Click to open"));
        categoryItemMain.setItemMeta(im);
        Category eqCategory = new Category(categoryIdMain, categoryItemMain);

        // Items
        // Coal 1
        SlimefunItemStack coal1Stack = new SlimefunItemStack( "EQ_COAL_1", plugin.getEqItems().getAlchemicalCoal().getClone());
        SlimefunItem coal1Item = new EQSlimefunItem(eqCategory, coal1Stack, new RecipeType(MinecraftRecipe.SHAPED_CRAFTING), Recipes.getSFRecipeCoal1());
        coal1Item.register(this);
        // Coal 2
        SlimefunItemStack coal2Stack = new SlimefunItemStack( "EQ_COAL_2", plugin.getEqItems().getMobiusFuel().getClone());
        SlimefunItem coal2Item = new EQSlimefunItem(eqCategory, coal2Stack, new RecipeType(MinecraftRecipe.SHAPED_CRAFTING), Recipes.getSFRecipeCoal2(coal1Stack));
        coal2Item.register(this);
        // Coal 3
        SlimefunItemStack coal3Stack = new SlimefunItemStack( "EQ_COAL_3", plugin.getEqItems().getAeternalisFuel().getClone());
        SlimefunItem coal3Item = new EQSlimefunItem(eqCategory, coal3Stack, new RecipeType(MinecraftRecipe.SHAPED_CRAFTING), Recipes.getSFRecipeCoal3(coal2Stack));
        coal3Item.register(this);
        // Dark Matter
        SlimefunItemStack darkStack = new SlimefunItemStack( "EQ_DARK_MATTER", plugin.getEqItems().getDarkMatter().getClone());
        SlimefunItem darkItem = new EQSlimefunItem(eqCategory, darkStack, new RecipeType(MinecraftRecipe.SHAPED_CRAFTING), Recipes.getSFRecipeDarkMatter(coal3Stack));
        darkItem.register(this);
        // Red Matter
        SlimefunItemStack redStack = new SlimefunItemStack( "EQ_RED_MATTER", plugin.getEqItems().getRedMatter().getClone());
        SlimefunItem redItem = new EQSlimefunItem(eqCategory, redStack, new RecipeType(MinecraftRecipe.SHAPED_CRAFTING), Recipes.getSFRecipeRedMatter(coal3Stack, darkStack));
        redItem.register(this);
        // Transmutation Orb
        SlimefunItemStack orbStack = new SlimefunItemStack( "EQ_T_ORB", plugin.getEqItems().getTransmutationOrb().getClone());
        SlimefunItem orbItem = new EQSlimefunItem(eqCategory, orbStack, new RecipeType(MinecraftRecipe.SHAPED_CRAFTING), Recipes.getSFRecipeTransmutationOrb(darkStack, redStack));
        orbItem.register(this);


    }

    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return plugin;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return null;
    }
}
