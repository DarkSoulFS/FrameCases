package net.framedev.choice;

import net.framedev.Main;
import net.framedev.others.S;
import net.framedev.api.CheckChoiceAnimation;
import net.framedev.guis.GUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class ChoiceClick implements Listener {
    @EventHandler
    public void click(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(ChoiceAnimation.title)) {
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
            if (event.getSlot() == Main.getInstance().getConfig().getInt("choice.items.BACK.slot")) {
                    player.openInventory(GUI.inventory(player));
                } else if (event.getSlot() == Main.getInstance().getConfig().getInt("choice.items.ANIMATION_1.slot")) {
                if (!player.hasPermission(Objects.requireNonNull(Main.getInstance().getConfig().getString("choice.items.ANIMATION_1.perms")))) {
                    for (String s : Main.getInstance().getConfig().getStringList("choice.items.ANIMATION_1.no-perms")) {
                        player.sendMessage(S.s(s));
                    }
                    player.closeInventory();
                    return;
                }
                player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.set-animation")));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                    CheckChoiceAnimation.setAnimation(player, "ANIMATION_1");
                    player.closeInventory();
                } else if (event.getSlot() == Main.getInstance().getConfig().getInt("choice.items.ANIMATION_2.slot")) {
                if (!player.hasPermission(Objects.requireNonNull(Main.getInstance().getConfig().getString("choice.items.ANIMATION_2.perms")))) {
                    for (String s : Main.getInstance().getConfig().getStringList("choice.items.ANIMATION_2.no-perms")) {
                        player.sendMessage(S.s(s));
                    }
                    player.closeInventory();
                    return;
                }
                player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.set-animation")));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                    CheckChoiceAnimation.setAnimation(player, "ANIMATION_2");
                    player.closeInventory();
                } else if (event.getSlot() == Main.getInstance().getConfig().getInt("choice.items.ANIMATION_3.slot")) {
                if (!player.hasPermission(Objects.requireNonNull(Main.getInstance().getConfig().getString("choice.items.ANIMATION_3.perms")))) {
                    for (String s : Main.getInstance().getConfig().getStringList("choice.items.ANIMATION_3.no-perms")) {
                        player.sendMessage(S.s(s));
                    }
                    player.closeInventory();
                    return;
                }
                player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.set-animation")));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                    CheckChoiceAnimation.setAnimation(player, "ANIMATION_3");
                    player.closeInventory();
                } else if (event.getSlot() == Main.getInstance().getConfig().getInt("choice.items.ANIMATION_4.slot")) {
                if (!player.hasPermission(Objects.requireNonNull(Main.getInstance().getConfig().getString("choice.items.ANIMATION_4.perms")))) {
                    for (String s : Main.getInstance().getConfig().getStringList("choice.items.ANIMATION_4.no-perms")) {
                        player.sendMessage(S.s(s));
                    }
                    player.closeInventory();
                    return;
                }
                player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.set-animation")));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                    CheckChoiceAnimation.setAnimation(player, "ANIMATION_4");
                    player.closeInventory();
                } else if (event.getSlot() == Main.getInstance().getConfig().getInt("choice.items.ANIMATION_5.slot")) {
                if (!player.hasPermission(Objects.requireNonNull(Main.getInstance().getConfig().getString("choice.items.ANIMATION_5.perms")))) {
                    for (String s : Main.getInstance().getConfig().getStringList("choice.items.ANIMATION_5.no-perms")) {
                        player.sendMessage(S.s(s));
                    }
                    player.closeInventory();
                    return;
                }
                player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.set-animation")));
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                CheckChoiceAnimation.setAnimation(player, "ANIMATION_5");
                player.closeInventory();
            }
            }
        }
    }
}
