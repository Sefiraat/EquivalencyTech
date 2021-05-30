package io.github.sefiraat.equivalencytech.gui;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.statics.Colours;
import io.github.sefiraat.equivalencytech.statics.Config;
import io.github.sefiraat.equivalencytech.statics.GUIItems;
import io.github.sefiraat.equivalencytech.statics.Messages;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class GuiTransmutationOrb extends PaginatedGui {

    public final EquivalencyTech plugin;
    public final Player player;

    protected static final List<Integer> ARRAY_FILLER_SLOTS = Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8, 45, 47, 48, 50, 51, 53);
    protected static final Integer INFO_SLOT = 4;
    protected static final Integer INPUT_SLOT = 49;
    protected static final Integer PAGE_SIZE = 36;

    public GuiTransmutationOrb(int rows, int pageSize, @NotNull String title, EquivalencyTech plugin, Player player) {
        super(rows, pageSize, title);
        this.plugin = plugin;
        this.player = player;
    }

    public static GuiTransmutationOrb buildGui(EquivalencyTech plugin, Player player) {

        int backSlot = 46;
        int forwardSlot = 52;

        GuiTransmutationOrb gui = new GuiTransmutationOrb(
                6,
                GuiTransmutationOrb.PAGE_SIZE,
                Colours.THEME_EMC_PURPLE + plugin.getConfigClass().getStrings().getItemTransmutationOrbName(),
                plugin,
                player
        );

        gui.setItem(GuiTransmutationOrb.ARRAY_FILLER_SLOTS, GUIItems.guiOrbBorder(plugin));
        gui.setItem(GuiTransmutationOrb.INFO_SLOT, GUIItems.guiOrbInfo(plugin, player));

        List<Material> learnedItems = Config.getLearnedItems(plugin, player);

        for (Material m : learnedItems) {
            ItemStack i = new ItemStack(m);
            GuiItem guiItem = GUIItems.guiEMCItem(plugin, i.getType());
            guiItem.setAction(event -> emcItemClicked(event, plugin));
            gui.addItem(guiItem);
        }

        int leftOverSlots = GuiTransmutationOrb.PAGE_SIZE - (learnedItems.size() % GuiTransmutationOrb.PAGE_SIZE);

        for (int i = 0; i < leftOverSlots; i++) {
            gui.addItem(GUIItems.guiOrbFiller(plugin));
        }

        setInputSlot(plugin, gui);

        gui.setItem(backSlot, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event -> {
            event.setCancelled(true);
            gui.previous();
        }));

        gui.setItem(forwardSlot, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event -> {
            event.setCancelled(true);
            gui.next();
        }));

        gui.setDragAction(event -> event.setCancelled(true));
        gui.setDefaultClickAction(event -> {
            if (event.isShiftClick()) {
                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    inputItemAction(event, plugin, gui, true);
                }
                event.setCancelled(true);
            }
        });

        return gui;

    }

    private static void setInputSlot(EquivalencyTech plugin, GuiTransmutationOrb gui) {
        gui.addSlotAction(GuiTransmutationOrb.INPUT_SLOT, event -> {
            inputItemAction(event, plugin, gui, false);
            event.setCancelled(true);
        });
    }

    private static void inputItemAction(InventoryClickEvent e, EquivalencyTech plugin, PaginatedGui gui, boolean shifted) {
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack;
        if (shifted) {
            itemStack = e.getCurrentItem();
        } else {
            itemStack = player.getItemOnCursor();
        }
        if (itemStack.hasItemMeta()) {
            player.sendMessage(Messages.messageGuiItemMeta(plugin));
            return;
        }
        Material material = itemStack.getType();
        Double emcValue = plugin.getEmcDefinitions().getEmcValue(material);
        boolean mustClose = false;
        if (emcValue != null) {
            double totalEmc = emcValue * itemStack.getAmount();
            if (!Config.getLearnedItems(plugin, player).contains(material)) {
                Config.addLearnedItem(plugin, player, material);
                player.sendMessage(Messages.messageGuiItemLearned(plugin));
                mustClose = true;
            }
            Config.addPlayerEmc(plugin, player, totalEmc);
            player.sendMessage(Messages.messageGuiEmcGiven(plugin, player, emcValue, totalEmc, itemStack.getAmount()));
            itemStack.setAmount(0);
        } else {
            player.sendMessage(Messages.msgCmdEmcNone(plugin));
        }
        if (mustClose) {
            gui.close(player);
        }
    }

    private static void giveItemBack(Player player, ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
    }

    private static void emcItemClicked(InventoryClickEvent e, EquivalencyTech plugin) {
        e.setCancelled(true);
        switch (e.getClick()) {
            case LEFT:
                emcWithdrawOne(e, plugin);
                break;
            case RIGHT:
                emcWithdrawStack(e, plugin);
                break;
            default:
                break;
        }
    }

    private static void emcWithdrawOne(InventoryClickEvent e, EquivalencyTech plugin) {
        Player player = (Player) e.getWhoClicked();
        double playerEmc = Config.getPlayerEmc(plugin, player);
        Material material = e.getCurrentItem().getType();
        Double emcValue = plugin.getEmcDefinitions().getEmcValue(material);
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(Messages.messageGuiNoSpace(plugin));
            return;
        }
        if (playerEmc >= emcValue) {
            ItemStack itemStack = new ItemStack(material);
            player.getInventory().addItem(itemStack);
            Config.removePlayerEmc(plugin, player, emcValue);
            player.sendMessage(Messages.messageGuiEmcRemoved(plugin, player, emcValue, emcValue, 1));
        } else {
            player.sendMessage(Messages.messageGuiEmcNotEnough(plugin, player));
        }
    }

    private static void emcWithdrawStack(InventoryClickEvent e, EquivalencyTech plugin) {
        Player player = (Player) e.getWhoClicked();
        double playerEmc = Config.getPlayerEmc(plugin, player);
        ItemStack clickedItemStack = e.getCurrentItem();
        Material material = clickedItemStack.getType();
        Double emcValue = plugin.getEmcDefinitions().getEmcValue(material);
        int amount = clickedItemStack.getMaxStackSize();
        if (emcValue != null) {
            double emcValueStack = emcValue * amount;
            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(Messages.messageGuiNoSpace(plugin));
                return;
            }
            if (playerEmc < emcValueStack) {
                double maxPossible = playerEmc / emcValue;
                maxPossible = Math.floor(maxPossible);
                amount = (int) maxPossible;
                emcValueStack = emcValue * amount;
            }
            if (amount == 0) {
                player.sendMessage(Messages.messageGuiEmcNotEnough(plugin, player));
                return;
            }
            ItemStack itemStack = new ItemStack(material);
            itemStack.setAmount(amount);
            player.getInventory().addItem(itemStack);
            Config.removePlayerEmc(plugin, player, emcValueStack);
            player.sendMessage(Messages.messageGuiEmcRemoved(plugin, player, emcValue, emcValueStack, amount));
        }
    }



}

