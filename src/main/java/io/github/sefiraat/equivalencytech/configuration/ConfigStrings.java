package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigStrings {

    // General

    private final String generalNoInvSpace;
    public String getGeneralNoInvSpace() {
        return generalNoInvSpace;
    }

    private final String generalCraftingItem;
    public String getGeneralCraftingItem() {
        return generalCraftingItem;
    }

    // Commands

    private final String commandSubcommand;
    public String getCommandSubcommand() {
        return commandSubcommand;
    }

    private final String commandEmcMustHold;
    public String getCommandEmcMustHold() {
        return commandEmcMustHold;
    }

    private final String commandEmcNone;
    public String getCommandEmcNone() {
        return commandEmcNone;
    }

    private final String commandSelectItem;
    public String getCommandSelectItem() {
        return commandSelectItem;
    }

    private final String commandOrbGiven;
    public String getCommandOrbGiven() {
        return commandOrbGiven;
    }

    // Items

    private final String itemTransmutationOrbName;
    public String getItemTransmutationOrbName() {
        return itemTransmutationOrbName;
    }

    private final List<String> itemTransmutationOrbLore;
    public List<String> getItemTransmutationOrbLore() {
        return itemTransmutationOrbLore;
    }

    private final String itemRightClickToOpen;
    public String getItemRightClickToOpen() {
        return itemRightClickToOpen;
    }

    private final String itemAlchemicalCoalName;
    public String getItemAlchemicalCoalName() {
        return itemAlchemicalCoalName;
    }

    private final String itemMobiusFuelName;
    public String getItemMobiusFuelName() {
        return itemMobiusFuelName;
    }

    private final String itemAeternalisFuelName;
    public String getItemAeternalisFuelName() {
        return itemAeternalisFuelName;
    }

    private final String itemDarkMatterName;
    public String getItemDarkMatterName() {
        return itemDarkMatterName;
    }

    private final String itemRedMatterName;
    public String getItemRedMatterName() {
        return itemRedMatterName;
    }

    // GUI

    private final String guiBorderName;
    public String getGuiBorderName() {
        return guiBorderName;
    }

    private final String guiFillerName;
    public String getGuiFillerName() {
        return guiFillerName;
    }

    private final String guiItemLearned;
    public String getGuiItemLearned() {
        return guiItemLearned;
    }

    private final String guiInfoName;
    public String getGuiInfoName() {
        return guiInfoName;
    }

    private final String guiInfoRecipes;
    public String getGuiInfoRecipes() {
        return guiInfoRecipes;
    }

    private final String guiEntryLeftClick;
    public String getGuiEntryLeftClick() {
        return guiEntryLeftClick;
    }

    private final String guiEntryRightClick;
    public String getGuiEntryRightClick() {
        return guiEntryRightClick;
    }

    private final String guiNotEnoughEmc;
    public String getGuiNotEnoughEmc() {
        return guiNotEnoughEmc;
    }

    private final String guiItemMeta;
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
