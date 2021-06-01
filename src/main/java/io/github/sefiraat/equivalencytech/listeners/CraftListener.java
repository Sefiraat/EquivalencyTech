package io.github.sefiraat.equivalencytech.listeners;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftListener implements Listener {

    private final EquivalencyTech plugin;

    public CraftListener(EquivalencyTech plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPreCraft(PrepareItemCraftEvent e) {

        if (e.getView().getPlayer() instanceof Player) {

            Player player = (Player) e.getView().getPlayer();

            if (e.getRecipe() != null && e.getRecipe().getResult().getType() == Material.PLAYER_HEAD) {
                player.sendMessage("output valid");
                ItemStack[] contents = e.getInventory().getMatrix();
                List<ItemStack> contentlayer = new ArrayList<>();

                for (ItemStack itemStack : contents) {
                    ItemStack clone = null;
                    if (itemStack != null) {
                        clone = itemStack.clone();
                        clone.setAmount(1);
                        player.sendMessage(clone.getType().toString());
                    }
                    contentlayer.add(clone);
                }

                if (contentlayer.equals(Recipes.recipeCoal2Check(plugin))) {
                    player.sendMessage("coal2");
                    e.getInventory().setResult(plugin.getEqItems().getMobiusFuel().getItemClone());
                } else if (contentlayer.equals(Recipes.recipeCoal3Check(plugin))) {
                    player.sendMessage("coal3");
                    e.getInventory().setResult(plugin.getEqItems().getAeternalisFuel().getItemClone());
                } else if (contentlayer.equals(Recipes.recipeDarkMatterCheck(plugin))) {
                    player.sendMessage("dark");
                    e.getInventory().setResult(plugin.getEqItems().getDarkMatter().getItemClone());
                } else if (contentlayer.equals(Recipes.recipeRedMatterCheck(plugin))) {
                    player.sendMessage("red");
                    e.getInventory().setResult(plugin.getEqItems().getRedMatter().getItemClone());
                } else if (contentlayer.equals(Recipes.recipeTransmutationOrbCheck(plugin))) {
                    player.sendMessage("orb");
                    e.getInventory().setResult(plugin.getEqItems().getTransmutationOrb().getItemClone());
                } else if (contentsEQ(contentlayer)) {
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                }

            }



        }
    }

    private boolean contentsEQ(List<ItemStack> itemStacks) {
        for (ItemStack itemStack : itemStacks) {
            if (ContainerStorage.isCrafting(itemStack, plugin)) {
                return true;
            }
        }
        return false;
    }
}
