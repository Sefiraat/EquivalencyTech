package io.github.sefiraat.equivalencytech.configuration;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigStrings {

    FileConfiguration configuration;

    // region General

    public String getGeneralNoInvSpace() {
        return configuration.getString("MESSAGES.GENERAL_NO_INVENTORY_SPACE");
    }

    public String getGeneralCraftingItem() {
        return configuration.getString("ITEMS.GENERAL_CRAFTING_ITEM");
    }

    // endregion

    // region Commands

    public String getCommandSubcommand() {
        return configuration.getString("MESSAGES.COMMAND_SUBCOMMAND");
    }

    public String getCommandEmcMustHold() {
        return configuration.getString("MESSAGES.COMMAND_EMC_ITEM_MUST_HOLD");
    }

    public String getCommandEmcNone() {
        return configuration.getString("MESSAGES.COMMAND_EMC_NONE");
    }

    public String getCommandSelectItem() {
        return configuration.getString("MESSAGES.COMMAND_SELECT_ITEM");
    }

    public String getCommandItemGiven() {
        return configuration.getString("MESSAGES.COMMAND_ITEM_GIVEN");
    }

    // endregion

    // region Items

    public String getItemTransmutationOrbName() {
        return configuration.getString("ITEMS.TRANSMUTATION_ORB_NAME");
    }

    public List<String> getItemTransmutationOrbLore() {
        return configuration.getStringList("ITEMS.TRANSMUTATION_ORB_LORE");
    }

    public String getItemRightClickToOpen() {
        return configuration.getString("ITEMS.TRANSMUTATION_ORB_INFO");
    }

    public String getItemAlchemicalCoalName() {
        return configuration.getString("ITEMS.ALCHEMICAL_COAL_NAME");
    }

    public String getItemMobiusFuelName() {
        return configuration.getString("ITEMS.MOBIUS_FUEL_NAME");
    }

    public String getItemAeternalisFuelName() {
        return configuration.getString("ITEMS.AETERNALIS_FUEL_NAME");
    }

    public String getItemDarkMatterName() {
        return configuration.getString("ITEMS.DARK_MATTER_NAME");
    }

    public String getItemRedMatterName() {
        return configuration.getString("ITEMS.RED_MATTER_NAME");
    }

    public String getItemDissolutionChestName() {
        return configuration.getString("ITEMS.DISSOLUTION_CHEST_NAME");
    }

    public List<String> getItemDissolutionChestLore() {
        return configuration.getStringList("ITEMS.DISSOLUTION_CHEST_LORE");
    }

    public String getItemCondensatorChestName() {
        return configuration.getString("ITEMS.CONDENSATOR_CHEST_NAME");
    }

    public List<String> getItemCondensatorChestLore() {
        return configuration.getStringList("ITEMS.CONDENSATOR_CHEST_LORE");
    }

    public String getItemCondensatorChestSet() {
        return configuration.getString("ITEMS.CONDENSATOR_CHEST_SET_ITEM");
    }

    // endregion

    // region GUI

    public String getGuiBorderName() {
        return configuration.getString("GUI.SLOT_BORDER_NAME");
    }

    public String getGuiFillerName() {
        return configuration.getString("GUI.SLOT_FILLER_NAME");
    }

    public String getGuiItemLearned() {
        return configuration.getString("GUI.ORB_ITEM_ADDED");
    }

    public String getGuiInfoName() {
        return configuration.getString("GUI.EMC_INFO_NAME");
    }

    public String getGuiInfoRecipes() {
        return configuration.getString("GUI.EMC_INFO_LORE_RECIPES");
    }

    public String getGuiEntryLeftClick() {
        return configuration.getString("GUI.EMC_ITEM_WITHDRAW_ONE");
    }

    public String getGuiEntryRightClick() {
        return configuration.getString("GUI.EMC_ITEM_WITHDRAW_STACK");
    }

    public String getGuiNotEnoughEmc() {
        return configuration.getString("GUI.EMC_NOT_ENOUGH");
    }

    public String getGuiItemMeta() {
        return configuration.getString("GUI.EMC_ITEM_META");
    }

    // endregion

    // region Events

    public String getEventAdjPlacement() {
        return configuration.getString("MESSAGES.EVENT_PLACE_EMC_CHEST_ADJACENT");
    }

    public String getEventCantOpenNotOwner() {
        return configuration.getString("MESSAGES.EVENT_CANT_OPEN_NOT_OWNER");
    }

    public String getEventItemSet() {
        return configuration.getString("MESSAGES.EVENT_CHEST_ITEM_SET");
    }

    public String getEventItemUnset() {
        return configuration.getString("MESSAGES.EVENT_CHEST_ITEM_UNSET");
    }


    // endregion

    public ConfigStrings(EquivalencyTech plugin) {
        configuration = plugin.getConfig();
    }

}
