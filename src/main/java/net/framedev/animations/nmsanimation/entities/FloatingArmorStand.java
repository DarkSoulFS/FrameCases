package net.framedev.animations.nmsanimation.entities;

import net.framedev.Main;
import net.framedev.others.Coloriser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

public class FloatingArmorStand {
	
	private final Main instance = Main.getInstance();
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
            for (String st : instance.getConfig().getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                try {
                    String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                    Material material = Material.valueOf(instance.getConfig().getString(path));
                    String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                    String hdPath = String.join(".", "cases." + Main.openCaseName + "." + st + ".name");
                    String hd = Coloriser.colorify(instance.getConfig().getString(hdPath));
                    byte data = (byte) instance.getConfig().getInt(pathData);
                    if (ast.getEquipment().getHelmet().getType().equals(material) && ast.getEquipment().getHelmet().getData().getData() == data) {
                        ast.setCustomName(hd);
                        ast.setCustomNameVisible(true);
                    }
                } catch (NullPointerException | IndexOutOfBoundsException exception) {

                }
            }
            ast.setMetadata("case_", new FixedMetadataValue(Main.getInstance(), true));
        } else {
            if (ast.getEquipment().getHelmet().getType().isBlock()) {
                ast.teleport(astLoc);
                return;
            }
            ast.teleport(astLoc.add(0, -0.5, 0));
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
