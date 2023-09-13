package net.framedev.animations;

import net.framedev.Main;
import net.framedev.others.S;
import net.framedev.api.Actions;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaseGUIAnimation implements Listener {

    public boolean animationRunning = false;
    private List<ItemStack> animatedItems = new ArrayList<>();
    private int currentItemIndex = 0;
    private int[] itemSlots = {20, 21, 22, 23, 24}; // Слоты от 20 до 24



    String title = S.s("&7Открытие..");
    public Inventory openAnimation(Player player) {
        Main.isOpen = true;
        Inventory animationMenu = Bukkit.createInventory(null, 45, title);

        // Заполняем инвентарь заглушками (заглушками может быть, например, стеки воздуха)
        for (int i = 0; i < 45; i++) {
            animationMenu.setItem(i, new ItemStack(Material.AIR));
        }

        player.openInventory(animationMenu);

        for (String items : Main.getInstance().getConfig().getConfigurationSection("animation-gui.items").getKeys(false)) {
            try {
                List<String> lores = ((Main) Main.getPlugin(Main.class)).getConfig().getStringList("animation-gui.items." + items + ".lore");
                String name = ((Main) Main.getPlugin(Main.class)).getConfig().getString("animation-gui.items." + items + ".name");
                String material = ((Main) Main.getPlugin(Main.class)).getConfig().getString("animation-gui.items." + items + ".material");
                int amount = ((Main) Main.getPlugin(Main.class)).getConfig().getInt("animation-gui.items." + items + ".amount");
                int slot = ((Main) Main.getPlugin(Main.class)).getConfig().getInt("animation-gui.items." + items + ".slot");

                List<String> lore = new ArrayList<>();
                ItemStack itemStack = new ItemStack(Material.matchMaterial(material), amount);
                ItemMeta itemMeta = itemStack.getItemMeta();
                for (String s : lores)
                    lore.add(S.s(s));
                itemMeta.setLore(lore);
                itemMeta.setDisplayName(S.s(name));
                itemStack.setItemMeta(itemMeta);
                animationMenu.setItem(slot, itemStack);
            } catch (NullPointerException ignored) {

            }
        }

        animationRunning = true;
        animatedItems.clear();

        // Заполняем animatedItems рандомными предметами (5 штук)
        for (int i = 0; i < 5; i++) {
            ItemStack item = new ItemStack(getRandomItem());
            ItemMeta meta = item.getItemMeta();
            for (String st : Main.getInstance().getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                try {
                    String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                    Material material = Material.valueOf(Main.getInstance().getConfig().getString(path));
                    if (item.getType().equals(material)) {
                        String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                        meta.setDisplayName(S.s(Main.getInstance().getConfig().getString(path_)));
                        item.setItemMeta(meta);
                    }
                } catch (NullPointerException exception) {

                }
            }
            animatedItems.add(item);
        }

        new BukkitRunnable() {
            int ticks = 0;
            int maxTicks = 7; // 10 секунд (20 тиков = 1 секунда)

            @Override
            public void run() {
                // Остановить анимацию через 10 секунд
                if (ticks >= maxTicks) {
                    animationRunning = false;
                    ItemStack itemToGive = animationMenu.getItem(22); // Получаем предмет из 22 слота

                    for (String st : Main.getInstance().getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                        try {
                            String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                            Material material = Material.valueOf(Main.getInstance().getConfig().getString(path));
                            if (itemToGive.getType().equals(material)) {
                                String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".commands");
                                List<String> commands = Main.getInstance().getConfig().getStringList(path_);
                                Actions.use(commands, player);
                            }
                        } catch (NullPointerException exception) {

                        }
                    }
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    player.closeInventory(); // Закрыть инвентарь анимации
                    Main.isOpen = false;
                    Main.items.clear();
                    this.cancel();
                    return;
                }

                // Очищаем текущие слоты
                for (int slot : itemSlots) {
                    animationMenu.setItem(slot, new ItemStack(Material.AIR));
                }

                // Устанавливаем следующие предметы на новых слотах
                for (int i = 0; i < 5; i++) {
                    animationMenu.setItem(itemSlots[i], animatedItems.get((ticks + i) % 5));
                }

                player.updateInventory();

                ticks++;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 15L); // Период анимации (в тиках)
        return animationMenu;
    }

    private Material getRandomItem() {
        Random random = new Random();
        return Main.items.get(random.nextInt(Main.items.size()));
    }

    @EventHandler
    public void guiClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getView().getTitle().equals(title)) {
                event.setCancelled(true);
            }
        }
    }
}