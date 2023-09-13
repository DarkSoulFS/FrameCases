package net.framedev.animations.nmsanimation.entities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import net.framedev.Main;
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
            ast.setVisible(false);
            ast.getEquipment().setHelmet(new ItemStack(getRandomItem()));
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