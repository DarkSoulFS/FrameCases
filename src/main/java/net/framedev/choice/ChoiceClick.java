package net.framedev.choice;

import java.util.Objects;
import net.framedev.Main;
import net.framedev.others.Coloriser;
import net.framedev.api.CheckChoiceAnimation;
import net.framedev.guis.GUI;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChoiceClick implements Listener {

	private final Main instance = Main.getInstance();

	@EventHandler
	public void click(InventoryClickEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase(ChoiceAnimation.title)) {
			return;
		}
		event.setCancelled(true);
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		Player player = (Player)event.getWhoClicked();
		FileConfiguration config = instance.getConfig();

		int[] animationSlots = { 
				config.getInt("choice.items.ANIMATION_1.slot"),
				config.getInt("choice.items.ANIMATION_2.slot"), 
				config.getInt("choice.items.ANIMATION_3.slot"),
				config.getInt("choice.items.ANIMATION_4.slot"), 
				config.getInt("choice.items.ANIMATION_5.slot") 
		};

		for (int i = 0; i < animationSlots.length; i++) {
			if (event.getSlot() == animationSlots[i]) {
				String itemConfigKey = "choice.items.ANIMATION_" + (i + 1);
				String nameAnimation = "ANIMATION_" + (i + 1);

				if (!player.hasPermission(config.getString(itemConfigKey + ".perms"))) {
					for (String s : config.getStringList(itemConfigKey + ".no-perms")) {
						player.sendMessage(Coloriser.colorify(s));
					}
					player.closeInventory();
					return;
				}

				player.sendMessage(Coloriser.colorify(config.getString("messages.set-animation")));
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				CheckChoiceAnimation.setAnimation(player, nameAnimation);
				player.closeInventory();
				return;
			}
		}

		if (event.getSlot() == config.getInt("choice.items.BACK.slot")) {
			player.openInventory(GUI.inventory(player));
		}
	}
}
