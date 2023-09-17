package net.framedev.others;

import net.framedev.Main;
import net.framedev.events.SetCase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	private final Main instance = Main.getInstance();
	
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    	FileConfiguration config = instance.getConfig();
        if (strings.length == 0) {
            if (!commandSender.hasPermission("framecases.cases")) {
                commandSender.sendMessage(Coloriser.colorify(config.getString("messages.no-perms")));
                return true;
            }
            commandSender.sendMessage(Coloriser.colorify("&7[&6FrameCases&7] &7- Кейсы, версия плагина: &6v" + instance.getDescription().getVersion()));
            commandSender.sendMessage(Coloriser.colorify("&7Комманды:"));
            commandSender.sendMessage(Coloriser.colorify(" &6/" + s + " setcase &7- Установить позицию для открытия кейсов."));
            commandSender.sendMessage(Coloriser.colorify(" &6/" + s + " givekey [игрок] [кейс] [кол-во] &7- Выдать кейсы игроку"));
            commandSender.sendMessage(Coloriser.colorify(" &6/" + s + " takekey [игрок] [кейс] [кол-во] &7- Забрать кейсы у игрока"));
            commandSender.sendMessage(Coloriser.colorify(" &6/" + s + " setkey [игрок] [кейс] [кол-во] &7- Установить кейсы игроку"));
            commandSender.sendMessage(Coloriser.colorify("&7"));
            commandSender.sendMessage(Coloriser.colorify(" &7Плагин поддерживается и обновляется разработчиками §6FrameDev§f, "));
            commandSender.sendMessage(Coloriser.colorify(" &7если вы заметили недоработку, или есть предложения, то:"));
            commandSender.sendMessage(Coloriser.colorify(" &7Группа ВК: §6vk.com/frame_dev"));
            commandSender.sendMessage(Coloriser.colorify("&7"));
            return true;
        }
        switch (strings[0]) {
            case "givekey": {
                if (strings.length != 4) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("framecases.givekey")) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.no-contains-case")));
                    return true;
                }
                CasesContainer.giveKey(strings[1], strings[2], Integer.parseInt(strings[3]));
                commandSender.sendMessage(Coloriser.colorify(config.getString("messages.give-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "takekey": {
                if (strings.length != 4) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("framecases.takekey")) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.no-contains-case")));
                    return true;
                }
                if (!(CasesContainer.takeKey(strings[1], strings[2], Integer.parseInt(strings[3])))) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.error-take-amount")));
                    return true;
                }
                commandSender.sendMessage(Coloriser.colorify(config.getString("messages.take-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "setkey": {
                if (strings.length != 4) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("framecaseColoriser.colorifyetkey")) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.no-contains-case")));
                    return true;
                }
                CasesContainer.setKey(strings[1], strings[2], Integer.parseInt(strings[3]));
                commandSender.sendMessage(Coloriser.colorify(config.getString("messageColoriser.colorifyet-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "setcase": {
                if (strings.length != 1) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.error-argument")));
                    return true;
                }
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(Coloriser.colorify(config.getString("messages.error-instance")));
                    return true;
                }
                Player player = (Player) commandSender;
                if (!player.hasPermission("framecaseColoriser.colorifyetcase")) {
                    player.sendMessage(Coloriser.colorify(config.getString("messages.no-perms")));
                    return true;
                }
                SetCase.setCaseList.add(player);
                player.sendMessage(Coloriser.colorify(config.getString("messages.click-set-position")));
                return true;
            }
        }
        return false;
    }
}
