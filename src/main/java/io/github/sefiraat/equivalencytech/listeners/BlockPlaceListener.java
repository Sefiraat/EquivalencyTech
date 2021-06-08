package io.github.sefiraat.equivalencytech.listeners;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final EquivalencyTech plugin;

    public BlockPlaceListener(EquivalencyTech plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChestPlace(BlockPlaceEvent e) {
        if (ContainerStorage.isCraftable(e.getItemInHand(), plugin) && e.getItemInHand().getType() != Material.CHEST) {
            e.setCancelled(true);
        }
    }

}
