package net.framedev.animations;

import net.framedev.Main;
import net.framedev.others.Coloriser;
import net.framedev.api.Actions;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
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
	
	private final Main instance = Main.getInstance();
    public boolean animationRunning = false;
    private List<ItemStack> animatedItems = new ArrayList<>();
    int currentItemIndex = 0;
    private int[] itemSlots = {20, 21, 22, 23, 24}; // Слоты от 20 до 24


    String title = Coloriser.colorify("&7Открытие..");
    public Inventory openAnimation(Player player) {
        Main.isOpen = true;
        Inventory animationMenu = Bukkit.createInventory(null, 45, title);

        // Заполняем инвентарь заглушками (заглушками может быть, например, стеки воздуха)
        for (int i = 0; i < 45; i++) {
            animationMenu.setItem(i, new ItemStack(Material.AIR));
        }

        player.openInventory(animationMenu);

        for (String items : instance.getConfig().getConfigurationSection("animation-gui.items").getKeys(false)) {
            try {
            	ConfigurationSection item = instance.getConfig().getConfigurationSection("animation-gui.items." + items);
                List<String> lores = item.getStringList(".lore");
                String name = item.getString(".name");
                String material = item.getString(".material");
                int amount = item.getInt(".amount");
                int slot = item.getInt(".slot");
                byte data = (byte) item.getInt( ".data");

                List<String> lore = new ArrayList<>();
                ItemStack itemStack = new ItemStack(Material.matchMaterial(material), amount, data);
                ItemMeta itemMeta = itemStack.getItemMeta();
                for (String s : lores)
                    lore.add(Coloriser.colorify(s));
                itemMeta.setLore(lore);
                itemMeta.setDisplayName(Coloriser.colorify(name));
                itemStack.setItemMeta(itemMeta);
                animationMenu.setItem(slot, itemStack);
            } catch (NullPointerException ignored) {

            }
        }

        animationRunning = true;
        animatedItems.clear();

        // Заполняем animatedItems рандомными предметами (5 штук)
        for (int i = 0; i < 5; i++) {
            ItemStack item = getRandomItem();
            ItemMeta meta = item.getItemMeta();
            for (String st : instance.getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                try {
                    String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                    Material material = Material.valueOf(instance.getConfig().getString(path));
                    String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                    byte data = (byte) instance.getConfig().getInt(pathData);
                    if (item.getType().equals(material) && item.getData().getData() == data) {
                        String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                        meta.setDisplayName(Coloriser.colorify(instance.getConfig().getString(path_)));
                        item.setItemMeta(meta);
                    }
                } catch (NullPointerException exception) {

                }
            }
            animatedItems.add(item);
        }

        new BukkitRunnable() {
            int ticks = 0;
            int maxTicks = instance.getConfig().getInt("animation-gui-time") * 2; // 10 секунд (20 тиков = 1 секунда)

            @Override
            public void run() {
                // Остановить анимацию через 10 секунд
                if (ticks >= maxTicks) {
                    animationRunning = false;
                    ItemStack itemToGive = animationMenu.getItem(22); // Получаем предмет из 22 слота

                    for (String st : instance.getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                        try {
                            String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                            Material material = Material.valueOf(instance.getConfig().getString(path));
                            String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                            byte data = (byte) instance.getConfig().getInt(pathData);
                            if (itemToGive.getType().equals(material) && itemToGive.getData().getData() == data) {
                                String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".commands");
                                List<String> commands = instance.getConfig().getStringList(path_);
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
        }.runTaskTimer(instance, 0L, 10L); // Период анимации (в тиках)
        return animationMenu;
    }

    private ItemStack getRandomItem() {
        Random random = new Random();
        return Main.items.get(random.nextInt(Main.items.size()));
    }

    @EventHandler
    public void guiClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (event.getView().getTitle().equals(title)) {
                event.setCancelled(true);
            }
        }
    }
}