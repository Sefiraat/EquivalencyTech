package io.github.sefiraat.equivalencytech;

import co.aikar.commands.PaperCommandManager;
import io.github.sefiraat.equivalencytech.commands.Commands;
import io.github.sefiraat.equivalencytech.configuration.ConfigMain;
import io.github.sefiraat.equivalencytech.item.EQItems;
import io.github.sefiraat.equivalencytech.listeners.ManagerEvents;
import io.github.sefiraat.equivalencytech.misc.SlimefunEQAddon;
import io.github.sefiraat.equivalencytech.recipes.EmcDefinitions;
import io.github.sefiraat.equivalencytech.recipes.Recipes;
import io.github.sefiraat.equivalencytech.runnables.ManagerRunnables;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class EquivalencyTech extends JavaPlugin {

    private EquivalencyTech instance;
    private PaperCommandManager commandManager;

    private ConfigMain configMainClass;
    private EmcDefinitions emcDefinitions;
    private EQItems eqItems;
    private Recipes recipes;
    private ManagerEvents managerEvents;
    private ManagerRunnables managerRunnables;

    private boolean isUnitTest = false;

    private boolean slimefun = false;
    private SlimefunEQAddon slimefunAddon;

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }

    public EquivalencyTech getInstance() {
        return instance;
    }

    public ConfigMain getConfigMainClass() {
        return configMainClass;
    }

    public EmcDefinitions getEmcDefinitions() {
        return emcDefinitions;
    }

    public EQItems getEqItems() {
        return eqItems;
    }

    public Recipes getRecipes() {
        return recipes;
    }

    public ManagerEvents getManagerEvents() {
        return managerEvents;
    }

    public ManagerRunnables getManagerRunnables() {
        return managerRunnables;
    }

    public boolean isSlimefun() {
        return slimefun;
    }

    public SlimefunEQAddon getSlimefunAddon() {
        return slimefunAddon;
    }

    public EquivalencyTech() {
        super();
    }

    protected EquivalencyTech(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        isUnitTest = true;
    }

    @Override
    public void onEnable() {

        getLogger().info("########################################");
        getLogger().info("");
        getLogger().info("             EquivalencyTech            ");
        getLogger().info("           Created by Sefiraat          ");
        getLogger().info("");
        getLogger().info("########################################");

        instance = this;

        configMainClass = new ConfigMain(this.getInstance());
        eqItems = new EQItems(this.getInstance());
        emcDefinitions = new EmcDefinitions(this.getInstance());
        recipes = new Recipes(this.getInstance());
        managerEvents = new ManagerEvents(this.getInstance());
        managerRunnables = new ManagerRunnables(this.getInstance());

        registerCommands();

        slimefun = getServer().getPluginManager().isPluginEnabled("Slimefun");

        if (isSlimefun()) {
            slimefunAddon = new SlimefunEQAddon(this.getInstance());
        }

        if (!isUnitTest) {
            int pluginId = 11527;
            Metrics metrics = new Metrics(this, pluginId);
        }

    }

    @Override
    public void onDisable() {
        saveConfig();
        configMainClass.saveAdditionalConfigs();
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this.getInstance());
        commandManager.registerCommand(new Commands(this.getInstance()));
    }






}
