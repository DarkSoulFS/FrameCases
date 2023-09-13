package net.framedev.animations;

import net.framedev.events.ClickBlock;
import net.framedev.Main;
import net.framedev.others.S;
import net.framedev.api.Actions;
import net.framedev.api.Holograms;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.List;
import java.util.Random;

public class CaseAnimation implements Listener {

    private Material lastItem;
    private static HolographicDisplaysAPI api = HolographicDisplaysAPI.get(Main.getInstance());
    Hologram hologram;

    public boolean isShulkerBox(Material material) {
        return material == Material.SHULKER_BOX || material == Material.BLACK_SHULKER_BOX ||
                material == Material.BLUE_SHULKER_BOX || material == Material.BROWN_SHULKER_BOX ||
                material == Material.CYAN_SHULKER_BOX || material == Material.GRAY_SHULKER_BOX ||
                material == Material.GREEN_SHULKER_BOX || material == Material.LIGHT_BLUE_SHULKER_BOX ||
                material == Material.LIGHT_GRAY_SHULKER_BOX || material == Material.LIME_SHULKER_BOX ||
                material == Material.MAGENTA_SHULKER_BOX || material == Material.ORANGE_SHULKER_BOX ||
                material == Material.PINK_SHULKER_BOX || material == Material.PURPLE_SHULKER_BOX ||
                material == Material.RED_SHULKER_BOX || material == Material.WHITE_SHULKER_BOX ||
                material == Material.YELLOW_SHULKER_BOX;
    }
    public void startCaseAnimation(Location location, Player player) {
        Main.isOpen = true;
        Block block = Holograms.locationCase().getBlock();
        if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.ENDER_CHEST) || isShulkerBox(block.getType()) || block.getType().equals(Material.TRAPPED_CHEST)) {
            ClickBlock.setChestOpened(block, true);
        }
        Holograms.removeHD();

        hologram = api.createHologram(Holograms.locationCase().add(0.5, 2, 0.5));
        hologram.getLines().appendText(S.s("&7Открытие.."));
        // Запуск анимации
        new BukkitRunnable() {
            double angle = 1;
            double radius = 1.0;
            int timer = 0;


            @Override
            public void run() {
                timer++;

                Location particleLocation = location.clone().add(0.5, 0.5, 0.5);

                double offsetX = radius * Math.cos(angle);
                double offsetZ = radius * Math.sin(angle);

                particleLocation.add(offsetX, 0, offsetZ);

                particleLocation.getWorld().spawnParticle(Particle.HEART, particleLocation, 1, 0, 0, 0, 0);

                angle += Math.PI / 16;

                if (timer % 30 == 0) {
                    Material randomItem = getRandomItem();
                    if (lastItem != null) {
                        location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5)
                                .stream()
                                .filter(entity -> entity instanceof ArmorStand)
                                .map(entity -> (ArmorStand) entity)
                                .filter(armorStand -> armorStand.getHelmet() != null && armorStand.getHelmet().getType() == lastItem)
                                .forEach(ArmorStand::remove);
                    }

                    // Добавление нового предмета к стойке для брони
                    if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.ENDER_CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
                        if (randomItem.isBlock()) {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -0.5, 0.5), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(Main.getInstance(), true));
                            armorStand.setHelmet(new ItemStack(randomItem));
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        } else {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -1.0, 0.7), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(Main.getInstance(), true));
                            armorStand.setHelmet(new ItemStack(randomItem));
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        }
                        for (String st : Main.getInstance().getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                            try {
                                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                                Material material = Material.valueOf(Main.getInstance().getConfig().getString(path));
                                if (lastItem.equals(material)) {
                                    hologram.getLines().remove(0);
                                    String path_name = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                                    hologram.getLines().appendText(S.s(Main.getInstance().getConfig().getString(path_name)));
                                }
                            } catch (NullPointerException | IndexOutOfBoundsException exception) {

                            }
                        }
                        particleLocation.getWorld().playSound(particleLocation, Sound.valueOf(Main.getInstance().getConfig().getString("settings.animation-sound")), 1f, 1f);
                    } else if (isShulkerBox(block.getType())) {
                        if (randomItem.isBlock()) {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -0.9, 0.5), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(Main.getInstance(), true));
                            armorStand.setHelmet(new ItemStack(randomItem));
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        } else {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -1.35, 0.6), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(Main.getInstance(), true));
                            armorStand.setHelmet(new ItemStack(randomItem));
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        }
                        for (String st : Main.getInstance().getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                            try {
                                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                                Material material = Material.valueOf(Main.getInstance().getConfig().getString(path));
                                if (lastItem.equals(material)) {
                                    hologram.setPosition(Holograms.locationCase().add(1.6, 1, 0.5));
                                    hologram.getLines().remove(0);
                                    String path_name = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                                    hologram.getLines().appendText(S.s(Main.getInstance().getConfig().getString(path_name)));
                                }
                            } catch (NullPointerException | IndexOutOfBoundsException exception) {

                            }
                        }
                        particleLocation.getWorld().playSound(particleLocation, Sound.valueOf(Main.getInstance().getConfig().getString("settings.animation-sound")), 1f, 1f);
                    } else if (!(block.getType().equals(Material.CHEST)) && !(block.getType().equals(Material.ENDER_CHEST)) && !(isShulkerBox(block.getType())) &&
                            !(block.getType().equals(Material.TRAPPED_CHEST))) {
                        if (randomItem.isBlock()) {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, 0.0, 0.5), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(Main.getInstance(), true));
                            armorStand.setHelmet(new ItemStack(randomItem));
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        } else {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -0.5, 0.7), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(Main.getInstance(), true));
                            armorStand.setHelmet(new ItemStack(randomItem));
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        }
                        for (String st : Main.getInstance().getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                            try {
                                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                                Material material = Material.valueOf(Main.getInstance().getConfig().getString(path));
                                if (lastItem.equals(material)) {
                                    hologram.setPosition(Holograms.locationCase().add(1.55, 1.8, 0.5));
                                    hologram.getLines().remove(0);
                                    String path_name = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                                    hologram.getLines().appendText(S.s(Main.getInstance().getConfig().getString(path_name)));
                                }
                            } catch (NullPointerException | IndexOutOfBoundsException exception) {

                            }
                        }
                        particleLocation.getWorld().playSound(particleLocation, Sound.valueOf(Main.getInstance().getConfig().getString("settings.animation-sound")), 1f, 1f);
                    }
                }


                if (timer >= 200) {
                    location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5)
                            .stream()
                            .filter(entity -> entity instanceof ArmorStand)
                            .forEach(Entity::remove);

                    for (String st : Main.getInstance().getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                        try {
                                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                                Material material = Material.valueOf(Main.getInstance().getConfig().getString(path));
                                if (lastItem.equals(material)) {
                                    String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".commands");
                                    List<String> commands = Main.getInstance().getConfig().getStringList(path_);
                                    Actions.use(commands, player);
                                }
                           // }
                        } catch (NullPointerException exception) {

                        }
                    }

                    if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.ENDER_CHEST) || isShulkerBox(block.getType()) ||
                            block.getType().equals(Material.TRAPPED_CHEST)) {
                        ClickBlock.setChestOpened(block, false);
                    }
                    Main.isOpen = false;
                    Main.items.clear();
                    Holograms.hologram(Holograms.locationCase());
                    cancel();

                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    private Material getRandomItem() {
        Random random = new Random();
        return Main.items.get(random.nextInt(Main.items.size()));
    }
}
