package net.framedev.guis;

import net.framedev.Main;
import net.framedev.animations.CaseAnimation;
import net.framedev.animations.nmsanimation.gui.CaseGUIAnimation_1_12_R2;
import net.framedev.animations.FastAnimation;
import net.framedev.animations.nmsanimation.entities.AnimationArmorStand;
import net.framedev.animations.nmsanimation.entities.RotationPlain;
import net.framedev.animations.nmsanimation.gui.CaseGUIAnimation_1_16_R3;
import net.framedev.api.CheckChoiceAnimation;
import net.framedev.api.Holograms;
import net.framedev.choice.ChoiceAnimation;
import net.framedev.others.CasesContainer;
import net.framedev.others.Coloriser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickGUI implements Listener {

    private final Main instance = Main.getInstance();
    CaseAnimation animation = new CaseAnimation();
    CaseGUIAnimation_1_12_R2 guiAnimation_1_12_r2 = new CaseGUIAnimation_1_12_R2();
    CaseGUIAnimation_1_16_R3 guiAnimation_1_16_r3 = new CaseGUIAnimation_1_16_R3();

    @EventHandler
    public void clickMenu(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(GUI.title)) {
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if (event.getSlot() == 49) {
                    player.openInventory(ChoiceAnimation.choice());
                }
                FileConfiguration config = instance.getConfig();
                for (String s : config.getConfigurationSection("cases").getKeys(false)) {
                    if (config.getInt("cases." + s + ".slot") == event.getSlot()) {
                        if (!CasesContainer.containsKey(player, s)) {
                            player.sendMessage(Coloriser.colorify(config.getString("messages.error-no-case")));
                            player.playSound(Holograms.locationCase(), Sound.valueOf(config.getString("settings.no-open-sound")), 1f, 1f); //settings.no-open-sound
                            player.closeInventory();
                            return;
                        }
                        for (String st : config.getConfigurationSection("cases." + s).getKeys(false)) {
                            //Material material = Material.valueOf(config.getString());
                            try {
                                String path = String.join(".", "cases." + s + "." + st + ".material");
                                Material material = Material.valueOf(config.getString(path));
                                String pathData = String.join(".", "cases." + s + "." + st + ".data");
                                byte data = (byte) config.getInt(pathData);
                                ItemStack item = new ItemStack(material, 1, data);
                                Main.items.add(item);
                            } catch (NullPointerException ex) {

                            }
                        }


                        if (!CheckChoiceAnimation.contains(player)) {
                            player.sendMessage(Coloriser.colorify("&cОшибка, выберите анимацию, чтобы открыть кейс!"));
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                            player.closeInventory();
                            return;
                        }

                        Main.openCaseName = s;
                        CasesContainer.takeKey(player.getName(), Main.openCaseName, 1);

                        if (CheckChoiceAnimation.getAnimation(player).equalsIgnoreCase("ANIMATION_1")) {
                            animation.startCaseAnimation(Holograms.locationCase(), player);
                            player.closeInventory();
                        } else if (CheckChoiceAnimation.getAnimation(player).equalsIgnoreCase("ANIMATION_2")) {
                            if (instance.getServer().getVersion().contains("1.16") ||
                                    instance.getServer().getVersion().contains("1.17") ||
                                    instance.getServer().getVersion().contains("1.18")) {
                                if (!guiAnimation_1_16_r3.animationRunning) {
                                    guiAnimation_1_16_r3.openAnimation(player);
                                    return;
                                }
                            } else {
                                if (!guiAnimation_1_12_r2.animationRunning) {
                                    guiAnimation_1_12_r2.openAnimation(player);
                                    return;
                                }
                                return;
                            }
                            return;
                        } else if (CheckChoiceAnimation.getAnimation(player).equalsIgnoreCase("ANIMATION_3")) {
                            AnimationArmorStand anim = new AnimationArmorStand(RotationPlain.Z);
                            anim.play(player, Main.getInstance(), Holograms.locationCase(), 6, 1.5, 5, 1, 238, true);
                            player.closeInventory();
                        } else if (CheckChoiceAnimation.getAnimation(player).equalsIgnoreCase("ANIMATION_4")) {
                            AnimationArmorStand anim = new AnimationArmorStand(RotationPlain.Y);
                            anim.play(player, Main.getInstance(), Holograms.locationCase(), 6, 1.5, 5, 1, 238, false);
                            player.closeInventory();
                        } else if (CheckChoiceAnimation.getAnimation(player).equalsIgnoreCase("ANIMATION_5")) {
                            Main.isOpen = true;
                            new FastAnimation().openCase(player);
                            player.closeInventory();
                        } else if (CheckChoiceAnimation.getAnimation(player).equalsIgnoreCase("ANIMATION_6")) {

                        }
                        return;
                    }
                }
            }
        }
    }
}