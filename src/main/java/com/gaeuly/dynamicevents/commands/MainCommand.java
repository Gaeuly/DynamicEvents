package com.gaeuly.dynamicevents.commands;

import com.gaeuly.dynamicevents.DynamicEvents;
import com.gaeuly.dynamicevents.events.WorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainCommand implements TabExecutor {

    private final DynamicEvents plugin;

    public MainCommand(DynamicEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            // Show help message if no arguments provided
            sender.sendMessage(plugin.getConfigManager().getMessage("prefix") + "Use /de [reload|start|list]");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "reload":
                if (!sender.hasPermission("dynamicevents.admin.reload")) {
                    sender.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
                    return true;
                }
                plugin.getConfigManager().loadConfigs();
                sender.sendMessage(plugin.getConfigManager().getMessage("reload-success"));
                break;

            case "start":
                if (!sender.hasPermission("dynamicevents.admin.start")) {
                    sender.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(plugin.getConfigManager().getMessage("prefix") + "Usage: /de start <eventName> [playerName]");
                    return true;
                }

                Player target;
                if (args.length > 2) {
                    target = Bukkit.getPlayer(args[2]);
                    if (target == null) {
                        sender.sendMessage(plugin.getConfigManager().getMessage("prefix") + "Player " + args[2] + " is not online.");
                        return true;
                    }
                } else {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getConfigManager().getMessage("player-only"));
                        return true;
                    }
                    target = (Player) sender;
                }

                String eventName = args[1];
                boolean started = plugin.getEventManager().startEventByName(eventName, target);

                if (started) {
                    sender.sendMessage(plugin.getConfigManager().getMessage("event-started").replace("%event%", eventName));
                } else {
                    sender.sendMessage(plugin.getConfigManager().getMessage("event-not-found").replace("%event%", eventName));
                }
                break;

            // You can add 'list' or 'stop' subcommands here later

            default:
                sender.sendMessage(plugin.getConfigManager().getMessage("prefix") + "Unknown command. Use /de help.");
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        // Tab complete for first argument
        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("reload", "start", "list");
            for (String sub : subCommands) {
                if (sub.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(sub);
                }
            }
        }
        // Tab complete for second argument after 'start'
        else if (args.length == 2 && args[0].equalsIgnoreCase("start")) {
            // Get all registered event names
            List<String> eventNames = plugin.getEventManager().getRegisteredEvents().stream()
                    .map(WorldEvent::getName)
                    .collect(Collectors.toList());

            for (String name : eventNames) {
                if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(name);
                }
            }
        }
        // Tab complete for third argument after 'start <eventName>'
        else if (args.length == 3 && args[0].equalsIgnoreCase("start")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                    completions.add(player.getName());
                }
            }
        }
        return completions;
    }
}