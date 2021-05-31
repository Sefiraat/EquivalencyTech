package io.github.sefiraat.equivalencytech;

import co.aikar.commands.PaperCommandManager;
import io.github.sefiraat.equivalencytech.commands.Commands;
import io.github.sefiraat.equivalencytech.configuration.Config;
import io.github.sefiraat.equivalencytech.listeners.CraftListener;
import io.github.sefiraat.equivalencytech.listeners.OrbOpenListener;
import io.github.sefiraat.equivalencytech.misc.EQItems;
import io.github.sefiraat.equivalencytech.misc.EmcDefinitions;
import io.github.sefiraat.equivalencytech.misc.SlimefunEQAddon;
import io.github.sefiraat.equivalencytech.statics.Recipes;
import io.github.sefiraat.equivalencytech.timers.TimerSave;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;

public class EquivalencyTech extends JavaPlugin {

    private EquivalencyTech instance;
    private PaperCommandManager commandManager;

    private Config configClass;
    private EmcDefinitions emcDefinitions;
    private EQItems eqItems;

    private boolean isUnitTest = false;

    private boolean mcMMO = false;
    private boolean slimefun = false;
    private SlimefunEQAddon slimefunAddon;

    private File learnedItemsConfigFile;
    public File getLearnedItemsConfigFile() {
        return learnedItemsConfigFile;
    }
    private FileConfiguration learnedItemsConfig;
    public FileConfiguration getLearnedItemsConfig() {
        return learnedItemsConfig;
    }

    private File playerEMCConfigFile;
    public File getPlayerEMCConfigFile() {
        return playerEMCConfigFile;
    }
    private FileConfiguration playerEMCConfig;
    public FileConfiguration getPlayerEMCConfig() {
        return playerEMCConfig;
    }

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }
    public EquivalencyTech getInstance() {
        return instance;
    }

    public Config getConfigClass() {
        return configClass;
    }
    public EmcDefinitions getEmcDefinitions() {
        return emcDefinitions;
    }
    public EQItems getEqItems() {
        return eqItems;
    }

    public boolean isMcMMO() {
        return mcMMO;
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

        sortConfigs();
        setupRunnables();
        registerCommands();

        new OrbOpenListener(this.getInstance());

        configClass = new Config(this.getInstance());
        eqItems = new EQItems(this.getInstance());
        emcDefinitions = new EmcDefinitions(this.getInstance());

        addRecipes();

        new CraftListener(this.getInstance());

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
        saveLearnedConfig();
        saveEmcConfig();
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this.getInstance());
        commandManager.registerCommand(new Commands(this.getInstance()));
    }

    private void setupRunnables() {
        TimerSave timerSave = new TimerSave(this.instance);
        timerSave.runTaskTimer(this.instance, 0, 100L);
    }

    private void sortConfigs() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        createLearnedConfig();
        createEmcConfig();
    }

    private void createLearnedConfig() {
        learnedItemsConfigFile = new File(getDataFolder(), "learned_items.yml");
        if (!learnedItemsConfigFile.exists()) {
            learnedItemsConfigFile.getParentFile().mkdirs();
            saveResource("learned_items.yml", false);
        }
        learnedItemsConfig = new YamlConfiguration();
        try {
            learnedItemsConfig.load(learnedItemsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveLearnedConfig() {
        try {
            learnedItemsConfig.save(learnedItemsConfigFile);
        } catch (IOException e) {
            this.getLogger().warning("Unable to save " + learnedItemsConfigFile.getName());
        }
    }

    private void createEmcConfig() {
        playerEMCConfigFile = new File(getDataFolder(), "player_emc.yml");
        if (!playerEMCConfigFile.exists()) {
            playerEMCConfigFile.getParentFile().mkdirs();
            saveResource("player_emc.yml", false);
        }
        playerEMCConfig = new YamlConfiguration();
        try {
            playerEMCConfig.load(playerEMCConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveEmcConfig() {
        try {
            playerEMCConfig.save(playerEMCConfigFile);
        } catch (IOException e) {
            this.getLogger().warning("Unable to save " + playerEMCConfigFile.getName());
        }
    }

    private void addRecipes() {
        this.getServer().addRecipe(Recipes.recipeCoal1(this.getInstance()));
        this.getServer().addRecipe(Recipes.recipeCoal2(this.getInstance()));
        this.getServer().addRecipe(Recipes.recipeCoal3(this.getInstance()));
        this.getServer().addRecipe(Recipes.recipeDarkMatter(this.getInstance()));
        this.getServer().addRecipe(Recipes.recipeRedMatter(this.getInstance()));
        this.getServer().addRecipe(Recipes.recipeTransmutationOrb(this.getInstance()));
    }

}
