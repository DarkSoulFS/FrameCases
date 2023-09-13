package net.framedev.others;

import net.framedev.Main;
import net.framedev.events.SetCase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            if (!commandSender.hasPermission("framecases.cases")) {
                commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                return true;
            }
            commandSender.sendMessage(S.s("&7[&6FrameCases&7] &7- Кейсы"));
            commandSender.sendMessage(S.s("&7Комманды:"));
            commandSender.sendMessage(S.s("&6/" + s + " setcase &7- Установить позицию для открытия кейсов."));
            commandSender.sendMessage(S.s("&6/" + s + " givekey [игрок] [кейс] [кол-во] &7- Выдать кейсы игроку"));
            commandSender.sendMessage(S.s("&6/" + s + " takekey [игрок] [кейс] [кол-во] &7- Забрать кейсы у игрока"));
            commandSender.sendMessage(S.s("&6/" + s + " setkey [игрок] [кейс] [кол-во] &7- Установить кейсы игроку"));
            commandSender.sendMessage(S.s("&7"));
            commandSender.sendMessage(S.s("&7 Группа в вк: &6vk.com/frame_dev"));
            commandSender.sendMessage(S.s("&7"));
            return true;
        }
        switch (strings[0]) {
            case "givekey" -> {
                if (strings.length != 4) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("framecases.givekey")) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-contains-case")));
                    return true;
                }
                CasesContainer.giveKey(strings[1], strings[2], Integer.parseInt(strings[3]));
                commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.give-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "takekey" -> {
                if (strings.length != 4) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("framecases.takekey")) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-contains-case")));
                    return true;
                }
                if (!(CasesContainer.takeKey(strings[1], strings[2], Integer.parseInt(strings[3])))) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-take-amount")));
                    return true;
                }
                commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.take-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "setkey" -> {
                if (strings.length != 4) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("framecases.setkey")) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-contains-case")));
                    return true;
                }
                CasesContainer.setKey(strings[1], strings[2], Integer.parseInt(strings[3]));
                commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.set-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "setcase" -> {
                if (strings.length != 1) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-argument")));
                    return true;
                }
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-instance")));
                    return true;
                }
                Player player = (Player) commandSender;
                if (!player.hasPermission("framecases.setcase")) {
                    player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                    return true;
                }
                SetCase.setCaseList.add(player);
                player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.click-set-position")));
                return true;
            }
        }
        return false;
    }
}
