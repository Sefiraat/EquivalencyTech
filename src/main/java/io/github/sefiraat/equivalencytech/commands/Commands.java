package io.github.sefiraat.equivalencytech.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import io.github.sefiraat.equivalencytech.EquivalencyTech;
import io.github.sefiraat.equivalencytech.misc.Utils;
import io.github.sefiraat.equivalencytech.statics.ContainerStorage;
import io.github.sefiraat.equivalencytech.statics.Messages;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("EquivalencyTech|ET")
@Description("EquivalencyTech Main")
public class Commands extends BaseCommand {

    private final EquivalencyTech plugin;

    public EquivalencyTech getPlugin() {
        return plugin;
    }

    public Commands(EquivalencyTech plugin) {
        this.plugin = plugin;
    }

    @Default
    public void onDefault(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(Messages.msgCmdSubcommand(plugin));
        }
    }

    @Subcommand("ItemEmc")
    @Description("Displays the EMC value for the held item.")
    public class ItemEmc extends BaseCommand {

        @Default
        public void onDefault(CommandSender sender) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack i = player.getInventory().getItemInMainHand();
                if (i.getType() != Material.AIR) {
                    if (ContainerStorage.isCrafting(i, plugin)) {
                        if (plugin.getEmcDefinitions().getEmcEQ().containsKey(i.getItemMeta().getDisplayName())) {
                            player.sendMessage(Messages.msgCmdEmcDisplay(i.getItemMeta().getDisplayName(), Utils.getEmcEq(plugin, i)));
                            player.sendMessage(Messages.msgCmdEmcDisplayStack(i.getItemMeta().getDisplayName(), i.getAmount(), Utils.getEmcEq(plugin, i) * i.getAmount()));
                        } else {
                            player.sendMessage(Messages.msgCmdEmcNone(plugin));
                        }
                        return;
                    }
                    if (plugin.getEmcDefinitions().getEmcExtended().containsKey(i.getType())) {
                        player.sendMessage(Messages.msgCmdEmcDisplay(i.getType(), Utils.getEMC(plugin, i.getType())));
                        player.sendMessage(Messages.msgCmdEmcDisplayStack(i.getType(), i.getAmount(), Utils.getEMC(plugin, i.getType()) * i.getAmount()));
                    } else {
                        player.sendMessage(Messages.msgCmdEmcNone(plugin));
                    }
                } else {
                    player.sendMessage(Messages.msgCmdEmcMustHold(plugin));
                }
            }
        }
    }

    @Subcommand("Emc")
    @Description("Displays the player's emc.")
    public class Emc extends BaseCommand {

        @Default
        public void onDefault(CommandSender sender) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(Messages.messageCommandEmc(plugin, player));
            }
        }
    }

    @Subcommand("GiveItem")
    @CommandPermission("EquiTech.Admin")
    @Description("Gives Debug Items")
    public class GiveItem extends BaseCommand {

        @Default
        public void onDefault(CommandSender sender) {
            if (sender instanceof Player) {
                sender.sendMessage(Messages.messageCommandSelectItem(plugin));
            }
        }

        @Subcommand("TransmutationOrb")
        @CommandPermission("EquivalencyTech.Admin")
        @CommandCompletion("@players")
        public void onGiveItemDank(CommandSender sender, OnlinePlayer player) {
            if (sender instanceof Player) {
                Utils.givePlayerOrb(plugin, player.getPlayer());
            }
        }
    }

}