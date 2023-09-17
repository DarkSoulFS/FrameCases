package net.framedev.animations;

import net.framedev.events.ClickBlock;
import net.framedev.Main;
import net.framedev.others.Coloriser;
import net.framedev.api.Actions;
import net.framedev.api.Holograms;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.List;
import java.util.Random;

public class CaseAnimation implements Listener {
	
	private final Main instance = Main.getInstance();
    private HolographicDisplaysAPI api = HolographicDisplaysAPI.get(instance);
    private ItemStack lastItem;
    Hologram hologram;

    public boolean isShulkerBox(Material material) {
    	if (material.toString().contains("SHULKER_BOX")) {
    		return true;
    	}
    	return false;
    }
    
    public void startCaseAnimation(Location location, Player player) {
        Main.isOpen = true;
        Block block = Holograms.locationCase().getBlock();
        if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.ENDER_CHEST) || isShulkerBox(block.getType()) || block.getType().equals(Material.TRAPPED_CHEST)) {
            ClickBlock.setChestOpened(block, true);
        }
        Holograms.removeHD();

        hologram = api.createHologram(Holograms.locationCase().add(0.5, 2, 0.5));
        hologram.getLines().appendText(Coloriser.colorify("&7Открытие.."));
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
                
                FileConfiguration config = instance.getConfig();
                
                if (timer % 30 == 0) {
                    ItemStack randomItem = getRandomItem();
                    if (lastItem != null) {
                        location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5)
                                .stream()
                                .filter(entity -> entity instanceof ArmorStand)
                                .map(entity -> (ArmorStand) entity)
                                .filter(armorStand -> armorStand.getEquipment().getHelmet() != null && armorStand.getEquipment().getHelmet().getType() == lastItem.getType())
                                .forEach(ArmorStand::remove);
                    }
                    
                    // Добавление нового предмета к стойке для брони
                    if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.ENDER_CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
                        if (randomItem.getType().isBlock()) {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -0.5, 0.5), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(instance, true));
                            armorStand.getEquipment().setHelmet(randomItem);
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        } else {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -1.0, 0.7), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(instance, true));
                            armorStand.getEquipment().setHelmet(randomItem);
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        }
                        for (String st : config.getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                            try {
                                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                                Material material = Material.valueOf(config.getString(path));
                                String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                                byte data = (byte) config.getInt(pathData);
                                if (lastItem.getType().equals(material) && lastItem.getData().getData() == data) {
                                    hologram.getLines().remove(0);
                                    String path_name = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                                    hologram.getLines().appendText(Coloriser.colorify(config.getString(path_name)));
                                }
                            } catch (NullPointerException | IndexOutOfBoundsException exception) {

                            }
                        }
                        particleLocation.getWorld().playSound(particleLocation, Sound.valueOf(config.getString("settings.animation-sound")), 1f, 1f);
                    } else if (isShulkerBox(block.getType())) {
                        if (randomItem.getType().isBlock()) {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -0.9, 0.5), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(instance, true));
                            armorStand.getEquipment().setHelmet(randomItem);
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        } else {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -1.35, 0.6), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(instance, true));
                            armorStand.getEquipment().setHelmet(randomItem);
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        }
                        for (String st : config.getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                            try {
                                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                                Material material = Material.valueOf(config.getString(path));
                                String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                                byte data = (byte) config.getInt(pathData);
                                if (lastItem.getType().equals(material) && lastItem.getData().getData() == data) {
                                    hologram.setPosition(Holograms.locationCase().add(1.6, 1, 0.5));
                                    hologram.getLines().remove(0);
                                    String path_name = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                                    hologram.getLines().appendText(Coloriser.colorify(config.getString(path_name)));
                                }
                            } catch (NullPointerException | IndexOutOfBoundsException exception) {

                            }
                        }
                        particleLocation.getWorld().playSound(particleLocation, Sound.valueOf(config.getString("settings.animation-sound")), 1f, 1f);
                    } else if (!(block.getType().equals(Material.CHEST)) && !(block.getType().equals(Material.ENDER_CHEST)) && !(isShulkerBox(block.getType())) &&
                            !(block.getType().equals(Material.TRAPPED_CHEST))) {
                        if (randomItem.getType().isBlock()) {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, 0.0, 0.5), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(instance, true));
                            armorStand.getEquipment().setHelmet(randomItem);
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        } else {
                            ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0.5, -0.5, 0.7), ArmorStand.class);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setArms(true);
                            armorStand.setMetadata("case_", new FixedMetadataValue(instance, true));
                            armorStand.getEquipment().setHelmet(randomItem);
                            armorStand.setHeadPose(new EulerAngle(0, 0, 0));
                            lastItem = randomItem;
                        }
                        for (String st : config.getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                            try {
                                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                                Material material = Material.valueOf(config.getString(path));
                                String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                                byte data = (byte) config.getInt(pathData);
                                if (lastItem.getType().equals(material) && lastItem.getData().getData() == data) {
                                    hologram.setPosition(Holograms.locationCase().add(1.55, 1.8, 0.5));
                                    hologram.getLines().remove(0);
                                    String path_name = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                                    hologram.getLines().appendText(Coloriser.colorify(config.getString(path_name)));
                                }
                            } catch (NullPointerException | IndexOutOfBoundsException exception) {

                            }
                        }
                        particleLocation.getWorld().playSound(particleLocation, Sound.valueOf(config.getString("settings.animation-sound")), 1f, 1f);
                    }
                }

                if (timer >= 200) {
                    location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5)
                            .stream()
                            .filter(entity -> entity instanceof ArmorStand)
                            .forEach(Entity::remove);

                    for (String st : config.getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                        try {
                                String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                                Material material = Material.valueOf(config.getString(path));
                                String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                                byte data = (byte) config.getInt(pathData);
                                if (lastItem.getType().equals(material) && lastItem.getData().getData() == data) {
                                    String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".commands");
                                    List<String> commands = config.getStringList(path_);
                                    Actions.use(commands, player);
                                }
                           // }
                        } catch (NullPointerException ignored) {

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
        }.runTaskTimer(instance, 0, 1);
    }

    private ItemStack getRandomItem() {
        Random random = new Random();
        return Main.items.get(random.nextInt(Main.items.size()));
    }
}
