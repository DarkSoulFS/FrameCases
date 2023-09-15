package net.framedev.others;

import net.framedev.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CasesContainer {

	private static final Main instance = Main.getInstance();
	
	// В пизду

	public static void giveKey(String name, String case_, int amount) {
		try {
			String s = String.join(".", "players." + name + "." + case_);
			int i = instance.getConfig().getInt(s);
			i = i + amount;
			instance.getConfig().set(s, i);
			instance.saveConfig();
		} catch (NullPointerException ex) {

		}
	}

	public static boolean takeKey(String name, String case_, int amount) {
		try {
			String s = String.join(".", "players." + name + "." + case_);
			int i = instance.getConfig().getInt(s);
			if (i >= amount) {
				i = i - amount;
				instance.getConfig().set(s, i);
				instance.saveConfig();
				return true;
			} else {

			}
		} catch (NullPointerException ex) {

		}
		return false;
	}

	public static void setKey(String name, String case_, int amount) {
		try {
			String s = String.join(".", "players." + name + "." + case_);
			instance.getConfig().set(s, amount);
			instance.saveConfig();
		} catch (NullPointerException ex) {

		}
	}

	public static int getKey(Player player, String case_) {
		String s = String.join(".", "players." + player.getName() + "." + case_);
		if (instance.getConfig().getInt(s) > 0) {
			return instance.getConfig().getInt(s);
		}
		return 0;
	}

	public static boolean containsKey(Player player, String case_) {
		String s = String.join(".", "players." + player.getName() + "." + case_);
		if (instance.getConfig().getInt(s) > 0) {
			return true;
		}
		return false;
	}

	public static boolean isValidateCase(String case_) {
		String s = String.join(".", "cases." + case_);
		if (instance.getConfig().contains(s)) {
			return true;
		}
		return false;
	}

	public static List<String> cases() {
		List<String> list = new ArrayList<String>(
				Objects.requireNonNull(instance.getConfig().getConfigurationSection("cases")).getKeys(false));
		return list;
	}
}
