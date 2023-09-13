package net.framedev.guis;

import net.framedev.others.CasesContainer;
import net.framedev.Main;
import net.framedev.others.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUI implements Listener {


    public static String title = S.s("&7Кейсы");

    Main plugin;

    public GUI(Main plugin) {
        this.plugin = plugin;
    }



    public static Inventory inventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, title);

        for (String items : Main.getInstance().getConfig().getConfigurationSection("gui.items").getKeys(false)) {
            try {
                List<String> lores = ((Main) Main.getPlugin(Main.class)).getConfig().getStringList("gui.items." + items + ".lore");
                String name = ((Main) Main.getPlugin(Main.class)).getConfig().getString("gui.items." + items + ".name");
                String material = ((Main) Main.getPlugin(Main.class)).getConfig().getString("gui.items." + items + ".material");
                int amount = ((Main) Main.getPlugin(Main.class)).getConfig().getInt("gui.items." + items + ".amount");
                int slot = ((Main) Main.getPlugin(Main.class)).getConfig().getInt("gui.items." + items + ".slot");
                byte data = (byte) ((Main) Main.getPlugin(Main.class)).getConfig().getInt("gui.items." + items + ".data");

                List<String> lore = new ArrayList<>();
                ItemStack itemStack = new ItemStack(Material.matchMaterial(material), amount, data);
                ItemMeta itemMeta = itemStack.getItemMeta();
                String case_ = ((Main) Main.getPlugin(Main.class)).getConfig().getString("gui.items." + items + ".case");
                for (String s : lores)
                    lore.add(S.s(s).replace("%keys_" + case_ + "%", String.valueOf(CasesContainer.getKey(player, case_))));
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
