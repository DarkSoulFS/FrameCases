package net.framedev.choice;

import net.framedev.Main;
import net.framedev.others.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChoiceAnimation {

    public static String title = S.s("&7Кейсы > Выбор анимации");

    Main plugin;

    public ChoiceAnimation(Main plugin) {
        this.plugin = plugin;
    }

    public static Inventory choice(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, title);
        for (String items : Main.getInstance().getConfig().getConfigurationSection("choice.items").getKeys(false)) {
            try {
                List<String> lores = ((Main) Main.getPlugin(Main.class)).getConfig().getStringList("choice.items." + items + ".lore");
                String name = ((Main) Main.getPlugin(Main.class)).getConfig().getString("choice.items." + items + ".name");
                String material = ((Main) Main.getPlugin(Main.class)).getConfig().getString("choice.items." + items + ".material");
                int amount = ((Main) Main.getPlugin(Main.class)).getConfig().getInt("choice.items." + items + ".amount");
                int slot = ((Main) Main.getPlugin(Main.class)).getConfig().getInt("choice.items." + items + ".slot");

                List<String> lore = new ArrayList<>();
                ItemStack itemStack = new ItemStack(Material.matchMaterial(material), amount);
                ItemMeta itemMeta = itemStack.getItemMeta();
                for (String s : lores)
                    lore.add(S.s(s));
                itemMeta.setLore(lore);
                itemMeta.setDisplayName(S.s(name));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(slot, itemStack);
            } catch (NullPointerException ignored) {

            }
        }
        return inventory;
    }
}
