package net.framedev.others;

import org.bukkit.ChatColor;

public class Coloriser {

    public static String colorify(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
