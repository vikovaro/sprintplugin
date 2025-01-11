package com.vikovaro.sprintplugin.Handlers;

import com.vikovaro.sprintplugin.Plugin;
import com.vikovaro.sprintplugin.Utilities.Helpers;
import com.vikovaro.sprintplugin.Utilities.Load;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler implements CommandExecutor {
    private final Plugin plugin;

    public CommandHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("itemmeta")) {
            getItemMeta(sender);
        }

        if (command.getName().equalsIgnoreCase("sprintplugin")) {
            if (args.length > 0) {
                sender.sendMessage("Provide a valid argument.");
            } else {
                handleOpCommands(sender, args[0]);
            }
        }

        return true;
    }

    private void getItemMeta(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand.getType() != Material.AIR) {
                player.sendMessage(itemInHand.getType().toString());
            } else {
                player.sendMessage("You are not holding any item!");
            }
        } else {
            sender.sendMessage("This command can only be executed by a player.");
        }
    }

    private void handleOpCommands(CommandSender sender, String command) {
        if (!sender.isOp()) {
            sender.sendMessage("No rights.");
        }

        if (command.equalsIgnoreCase("reload")) {
            try {
                Load.loadConfig();
                sender.sendMessage("Done reload config.");
            } catch (Exception e) {
                sender.sendMessage("Error by loading config.");
            }
        } else if (command.equalsIgnoreCase("config")) {
            sender.sendMessage(Helpers.getConfigMessage());
        } else if (command.equalsIgnoreCase("getinventoryweight")) {
            if (sender instanceof Player) {
                int weight = Helpers.getPlayerInventoryWeight((Player) sender);
                sender.sendMessage("Your inventory weight: "+weight);
            }
        } else {
            sender.sendMessage("Wrong command.");
        }
    }
}
