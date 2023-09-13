package net.framedev.animations.nmsanimation.entities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import net.framedev.Main;
import net.framedev.others.S;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class FloatingArmorStand {

    AnimationArmorStand anim;
    ArmorStand ast;
    double angleRads;

    public FloatingArmorStand(AnimationArmorStand anim, double angleRads) {
        this.anim = anim;
        this.angleRads = angleRads;
    }

    public void rotate(Location center, double distance, double rads, boolean counterClockwise) {
        angleRads += counterClockwise ? rads : -rads;
        double offsetX = Math.cos(angleRads) * distance;
        double offsetY = Math.sin(angleRads) * distance;

        Location astLoc = anim.getPlain().transform(center.clone(), offsetX, offsetY);

        if (ast == null) {

            ast = center.getWorld().spawn(astLoc, ArmorStand.class);
            ast.setGravity(false);
            ast.setVisible(false);
            ast.getEquipment().setHelmet(new ItemStack(getRandomItem()));
            for (String st : Main.getInstance().getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                try {
                    String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                    Material material = Material.valueOf(Main.getInstance().getConfig().getString(path));
                    String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                    String hdPath = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                    String hd = S.s(Main.getInstance().getConfig().getString(hdPath));
                    byte data = (byte) Main.getInstance().getConfig().getInt(pathData);
                    if (ast.getEquipment().getHelmet().getType().equals(material) && ast.getEquipment().getHelmet().getData().getData() == data) {
                        ast.setCustomName(hd);
                        ast.setCustomNameVisible(true);
                    }
                } catch (NullPointerException | IndexOutOfBoundsException exception) {

                }
            }
            ast.setMetadata("case_", new FixedMetadataValue(Main.getInstance(), true));
        } else {
            ast.teleport(astLoc);
        }
    }


    public ArmorStand getArmorStand() {
        return ast;
    }

    public double getY() {
        return ast.getLocation().getY();
    }

    public void removeArmorStand() {
        ast.remove();
    }


    private ItemStack getRandomItem() {
        Random random = new Random();
        return Main.items.get(random.nextInt(Main.items.size()));
    }
}
