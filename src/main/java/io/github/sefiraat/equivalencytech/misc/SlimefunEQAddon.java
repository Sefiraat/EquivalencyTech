package io.github.sefiraat.equivalencytech.misc;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class SlimefunEQAddon implements SlimefunAddon {

    private EquivalencyTech parent;

    private Category eqCategory;

    public SlimefunEQAddon(EquivalencyTech parent) {
        this.parent = parent;
    }

    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return parent;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return null;
    }
}
