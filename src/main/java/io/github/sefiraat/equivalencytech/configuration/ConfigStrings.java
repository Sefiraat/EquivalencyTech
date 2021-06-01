package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigStrings {

    // General

    private final String generalNoInvSpace;
    private final String generalCraftingItem;

    public String getGeneralNoInvSpace() {
        return generalNoInvSpace;
    }

    public String getGeneralCraftingItem() {
        return generalCraftingItem;
    }

    // Commands

    private final String commandSubcommand;
    private final String commandEmcMustHold;
    private final String commandEmcNone;
    private final String commandSelectItem;
    private final String commandOrbGiven;

    public String getCommandSubcommand() {
        return commandSubcommand;
    }

    public String getCommandEmcMustHold() {
        return commandEmcMustHold;
    }

    public String getCommandEmcNone() {
        return commandEmcNone;
    }

    public String getCommandSelectItem() {
        return commandSelectItem;
    }

    public String getCommandOrbGiven() {
        return commandOrbGiven;
    }

    // Items

    private final String itemTransmutationOrbName;
    private final List<String> itemTransmutationOrbLore;
    private final String itemRightClickToOpen;
    private final String itemAlchemicalCoalName;
    private final String itemMobiusFuelName;
    private final String itemAeternalisFuelName;
    private final String itemDarkMatterName;
    private final String itemRedMatterName;

    public String getItemTransmutationOrbName() {
        return itemTransmutationOrbName;
    }

    public List<String> getItemTransmutationOrbLore() {
        return itemTransmutationOrbLore;
    }

    public String getItemRightClickToOpen() {
        return itemRightClickToOpen;
    }

    public String getItemAlchemicalCoalName() {
        return itemAlchemicalCoalName;
    }

    public String getItemMobiusFuelName() {
        return itemMobiusFuelName;
    }

    public String getItemAeternalisFuelName() {
        return itemAeternalisFuelName;
    }

    public String getItemDarkMatterName() {
        return itemDarkMatterName;
    }

    public String getItemRedMatterName() {
        return itemRedMatterName;
    }

    // GUI

    private final String guiBorderName;
    private final String guiFillerName;
    private final String guiItemLearned;
    private final String guiInfoName;
    private final String guiInfoRecipes;
    private final String guiEntryLeftClick;
    private final String guiEntryRightClick;
    private final String guiNotEnoughEmc;
    private final String guiItemMeta;

    public String getGuiBorderName() {
        return guiBorderName;
    }

    public String getGuiFillerName() {
        return guiFillerName;
    }

    public String getGuiItemLearned() {
        return guiItemLearned;
    }

    public String getGuiInfoName() {
        return guiInfoName;
    }

    public String getGuiInfoRecipes() {
        return guiInfoRecipes;
    }

    public String getGuiEntryLeftClick() {
        return guiEntryLeftClick;
    }

    public String getGuiEntryRightClick() {
        return guiEntryRightClick;
    }

    public String getGuiNotEnoughEmc() {
        return guiNotEnoughEmc;
    }

    public String getGuiItemMeta() {
        return guiItemMeta;
    }

    public ConfigStrings(EquivalencyTech plugin) {

        FileConfiguration c = plugin.getConfig();

        generalNoInvSpace = c.getString("MESSAGES.COMMANDS.GENERAL_NO_INVENTORY_SPACE");
        generalCraftingItem = c.getString("ITEMS.GENERAL_CRAFTING_ITEM");
        commandSubcommand = c.getString("TEXTS.MESSAGES.COMMANDS.COMMAND_SUBCOMMAND");
        commandEmcMustHold = c.getString("MESSAGES.COMMANDS.COMMAND_EMC_ITEM_MUST_HOLD");
        commandEmcNone = c.getString("MESSAGES.COMMANDS.COMMAND_EMC_NONE");
        commandSelectItem = c.getString("MESSAGES.COMMANDS.COMMAND_SELECT_ITEM");
        commandOrbGiven = c.getString("MESSAGES.COMMANDS.COMMAND_TRANSMUTATION_ORB_GIVEN");
        itemTransmutationOrbName = c.getString("ITEMS.TRANSMUTATION_ORB_NAME");
        itemTransmutationOrbLore = c.getStringList("ITEMS.TRANSMUTATION_ORB_LORE");
        itemRightClickToOpen = c.getString("ITEMS.TRANSMUTATION_ORB_INFO");
        itemAlchemicalCoalName = c.getString("ITEMS.ALCHEMICAL_COAL_NAME");
        itemMobiusFuelName = c.getString("ITEMS.MOBIUS_FUEL_NAME");
        itemAeternalisFuelName = c.getString("ITEMS.AETERNALIS_FUEL_NAME");
        itemDarkMatterName = c.getString("ITEMS.DARK_MATTER_NAME");
        itemRedMatterName = c.getString("ITEMS.RED_MATTER_NAME");
        guiBorderName = c.getString("GUI.SLOT_BORDER_NAME");
        guiFillerName = c.getString("GUI.SLOT_FILLER_NAME");
        guiItemLearned = c.getString("GUI.ORB_ITEM_ADDED");
        guiInfoName = c.getString("GUI.EMC_INFO_NAME");
        guiInfoRecipes = c.getString("GUI.EMC_INFO_LORE_RECIPES");
        guiEntryLeftClick = c.getString("GUI.EMC_ITEM_WITHDRAW_ONE");
        guiEntryRightClick = c.getString("GUI.EMC_ITEM_WITHDRAW_STACK");
        guiNotEnoughEmc = c.getString("GUI.EMC_NOT_ENOUGH");
        guiItemMeta = c.getString("GUI.EMC_ITEM_META");
    }

}
