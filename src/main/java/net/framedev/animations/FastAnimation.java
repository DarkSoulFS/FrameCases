package net.framedev.animations;

import net.framedev.Main;
import net.framedev.api.Actions;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class FastAnimation {

	private final Main instance = Main.getInstance();

	public void openCase(Player player) {
		ItemStack drop = getRandomItem();
		FileConfiguration config = instance.getConfig();
		for (String st : config.getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
			try {
				String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
				Material material = Material.valueOf(config.getString(path));
				String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
				byte data = (byte) config.getInt(pathData);
				if (drop.getType().equals(material) && drop.getData().getData() == data) {
					String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".commands");
					List<String> commands = config.getStringList(path_);
					Actions.use(commands, player);
				}
				// }
			} catch (NullPointerException exception) {

			}
		}

		Main.items.clear();
		Main.isOpen = false;
	}

	private ItemStack getRandomItem() {
		Random random = new Random();
		return Main.items.get(random.nextInt(Main.items.size()));
	}
}
