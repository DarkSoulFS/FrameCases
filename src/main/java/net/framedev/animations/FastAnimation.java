package net.framedev.animations;

import net.framedev.Main;
import net.framedev.api.Actions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class FastAnimation {
    public void openCase(Player player) {
        ItemStack drop = getRandomItem();
        for (String st : Main.getInstance().getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
            try {
                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                Material material = Material.valueOf(Main.getInstance().getConfig().getString(path));
                String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                byte data = (byte) Main.getInstance().getConfig().getInt(pathData);
                if (drop.getType().equals(material) && drop.getData().getData() == data) {
                    String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".commands");
                    List<String> commands = Main.getInstance().getConfig().getStringList(path_);
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
