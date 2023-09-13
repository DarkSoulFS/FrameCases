package net.framedev.api;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CheckChoiceAnimation {

    private static Map<Player, String> animation = new HashMap<>();


    public static void setAnimation(Player player, String animtion) {
        animation.put(player, animtion);
    }

    public static String getAnimation(Player player) {
        return animation.get(player);
    }

    public static boolean contains(Player player) {
        if (animation.containsKey(player))
            return true;
        return false;
    }
}
