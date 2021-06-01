package io.github.sefiraat.equivalencytech.item;

import io.github.thebusybiscuit.slimefun4.core.attributes.NotConfigurable;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class EQSlimefunItem extends SlimefunItem implements NotPlaceable, NotConfigurable {

    public EQSlimefunItem(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
        setUseableInWorkbench(true);
    }

}