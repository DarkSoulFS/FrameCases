package net.framedev.others;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;
import java.util.stream.Collectors;

public class TabCommands implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return List.of("givekey", "setkey", "takekey", "setcase");
        } else if (strings.length == 2 && strings[0].equalsIgnoreCase("givekey")) {
            return Bukkit.getServer().getOnlinePlayers().stream().map(player -> player.getName()).collect(Collectors.toList());
        } else if (strings.length == 3 && strings[0].equalsIgnoreCase("givekey")) {
            return CasesContainer.cases();
        } else if (strings.length == 4 && strings[0].equalsIgnoreCase("givekey")) {
            return List.of("1", "5", "10", "30", "50", "100");
        } else if (strings.length == 2 && strings[0].equalsIgnoreCase("setkey")) {
            return Bukkit.getServer().getOnlinePlayers().stream().map(player -> player.getName()).collect(Collectors.toList());
        } else if (strings.length == 3 && strings[0].equalsIgnoreCase("setkey")) {
            return CasesContainer.cases();
        } else if (strings.length == 4 && strings[0].equalsIgnoreCase("setkey")) {
            return List.of("1", "5", "10", "30", "50", "100");
        } else if (strings.length == 2 && strings[0].equalsIgnoreCase("takekey")) {
            return Bukkit.getServer().getOnlinePlayers().stream().map(player -> player.getName()).collect(Collectors.toList());
        } else if (strings.length == 3 && strings[0].equalsIgnoreCase("takekey")) {
            return CasesContainer.cases();
        } else if (strings.length == 4 && strings[0].equalsIgnoreCase("takekey")) {
            return List.of("1", "5", "10", "30", "50", "100");
        }
        return null;
    }
}
