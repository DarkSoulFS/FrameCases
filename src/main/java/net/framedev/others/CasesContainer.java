package net.framedev.others;

import net.framedev.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CasesContainer {



    public static void giveKey(String name, String case_, int amount) {
        try {
            String s = String.join(".", "players." + name + "." + case_);
            int i = Main.getInstance().getConfig().getInt(s);
            i = i + amount;
            Main.getInstance().getConfig().set(s, i);
            Main.getInstance().saveConfig();
        } catch (NullPointerException ex) {

        }
    }

    public static boolean takeKey(String name, String case_, int amount) {
        try {
            String s = String.join(".", "players." + name + "." + case_);
            int i = Main.getInstance().getConfig().getInt(s);
            if (i >= amount) {
                i = i - amount;
                Main.getInstance().getConfig().set(s, i);
                Main.getInstance().saveConfig();
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
            Main.getInstance().getConfig().set(s, amount);
            Main.getInstance().saveConfig();
        } catch (NullPointerException ex) {

        }
    }

    public static int getKey(Player player, String case_) {
        String s = String.join(".", "players." + player.getName() + "." + case_);
        if (Main.getInstance().getConfig().getInt(s) > 0) {
            return Main.getInstance().getConfig().getInt(s);
        }
        return 0;
    }

    public static boolean containsKey(Player player, String case_) {
        String s = String.join(".", "players." + player.getName() + "." + case_);
        if (Main.getInstance().getConfig().getInt(s) > 0) {
            return true;
        }
        return false;
    }

    public static boolean isValidateCase(String case_) {
        String s = String.join(".", "cases." + case_);
        if (Main.getInstance().getConfig().contains(s)) {
            return true;
        }
        return false;
    }

    public static List<String> cases() {
        List<String> list = new ArrayList<String>(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("cases")).getKeys(false));
        return list;
    }
}
