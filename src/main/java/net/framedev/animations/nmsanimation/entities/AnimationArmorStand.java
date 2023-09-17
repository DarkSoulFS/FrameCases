package net.framedev.animations.nmsanimation.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import net.framedev.Main;
import net.framedev.animations.nmsanimation.events.CaseAnimationEndEvent;
import net.framedev.api.Actions;
import net.framedev.api.Holograms;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class AnimationArmorStand {
	
	private final Main instance = Main.getInstance();
    private final Random random = new Random();
    private final List<FloatingArmorStand> astColl = new ArrayList<>();
    private final RotationPlain plain;

    public AnimationArmorStand(RotationPlain plain) {
        this.plain = Objects.requireNonNull(plain);
    }

    public void play(Player player, Plugin plugin, Location at, int astCount, double distance, double step,
                     long period, long duration, boolean counterClockwise) {

        double rad = Math.PI / 180; // Degrees to radians conversion
        double astIntervalDeg = 360.0D / astCount;
        double astIntervalRad = astIntervalDeg * rad;
        double stepRad = step * rad;

        Holograms.removeHD();
        Main.isOpen = true;

        for (int i = 0; i < astCount; i++) {
            astColl.add(new FloatingArmorStand(this, astIntervalRad * i));
        }

        Location blockCenter = at.getBlock().getLocation().add(0.5, 0.5, 0.5);

        double arrowBottomX = blockCenter.getX();
        double arrowBottomY = blockCenter.getY() + distance + 0.5;
        double arrowBottomZ = blockCenter.getZ();
        
        FileConfiguration config = instance.getConfig();
        if (instance.getServer().getVersion().contains("1.16") ||
                instance.getServer().getVersion().contains("1.17") ||
                instance.getServer().getVersion().contains("1.18")) {
            DustOptions dustOptions = new Particle.DustOptions(Color.fromBGR(0, 0, 255), 1);
            BukkitTask timer = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                astColl.forEach(ast -> ast.rotate(blockCenter, distance, stepRad, counterClockwise));

                if (!plain.equals(RotationPlain.Y)) {
                    for (double d = 0; d < 1; d += 0.2) {
                        at.getWorld().spawnParticle(Particle.REDSTONE, arrowBottomX, arrowBottomY + d, arrowBottomZ, 1, dustOptions);
                    }
                } else {
                    for (double d = 0; d < 1.6; d += 0.2) {
                        at.getWorld().spawnParticle(Particle.REDSTONE, arrowBottomX, arrowBottomY - (d), arrowBottomZ - (d), 1, dustOptions);
                    }
                }
            }, 0L, period);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Material material = getHighestArmorStand().getArmorStand().getEquipment().getHelmet().getType();
                for (String st : config.getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                    try {
                        String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                        Material check = Material.valueOf(config.getString(path));
                        if (material.equals(check)) {
                            String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".commands");
                            List<String> commands = config.getStringList(path_);
                            Actions.use(commands, player);
                            Main.isOpen = false;
                            Main.items.clear();
                            Holograms.hologram(Holograms.locationCase());
                        }
                        // }
                    } catch (NullPointerException exception) {

                    }
                }

                timer.cancel();

                CaseAnimationEndEvent endEvent = new CaseAnimationEndEvent(this);
                Bukkit.getPluginManager().callEvent(endEvent);


                if (endEvent.isRemovingArmorStands()) {
                    Bukkit.getScheduler().runTaskLater(plugin, this::removeArmorStands, endEvent.getRemoveArmorStandsDelay());
                }
            }, duration);
        } else {
            //Particle dustOptions = Particle.REDSTONE;
            BukkitTask timer = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                astColl.forEach(ast -> ast.rotate(blockCenter, distance, stepRad, counterClockwise));

                if (!plain.equals(RotationPlain.Y)) {
                    for (double d = 0; d < 1; d += 0.2) {
                        at.getWorld().spawnParticle(Particle.REDSTONE, arrowBottomX, arrowBottomY + d, arrowBottomZ, 1);
                    }
                } else {
                    for (double d = 0; d < 1.6; d += 0.2) {
                        at.getWorld().spawnParticle(Particle.REDSTONE, arrowBottomX, arrowBottomY - (d), arrowBottomZ - (d), 1);
                    }
                }
            }, 0L, period);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Material material = getHighestArmorStand().getArmorStand().getEquipment().getHelmet().getType();
                byte data = Objects.requireNonNull(getHighestArmorStand().getArmorStand().getEquipment().getHelmet().getData()).getData();
                for (String st : config.getConfigurationSection("cases." + Main.openCaseName).getKeys(false)) {
                    try {
                        String path = String.join(".", "cases." + Main.openCaseName + "." + st + ".material");
                        Material check = Material.valueOf(config.getString(path));
                        String pathData = String.join(".", "cases." + Main.openCaseName + "." + st + ".data");
                        byte checkData = (byte) config.getInt(pathData);
                        if (material.equals(check) && data == checkData) {
                            String path_ = String.join(".", "cases." + Main.openCaseName + "." + st + ".commands");
                            List<String> commands = config.getStringList(path_);
                            Actions.use(commands, player);
                            Main.isOpen = false;
                            Main.items.clear();
                            Holograms.hologram(Holograms.locationCase());
                        }
                        // }
                    } catch (NullPointerException exception) {

                    }
                }

                timer.cancel();

                CaseAnimationEndEvent endEvent = new CaseAnimationEndEvent(this);
                Bukkit.getPluginManager().callEvent(endEvent);

                if (endEvent.isRemovingArmorStands()) {
                    Bukkit.getScheduler().runTaskLater(plugin, this::removeArmorStands, endEvent.getRemoveArmorStandsDelay());
                }
            }, duration);
        }
    }

    public Random getRandom() {
        return random;
    }

    public RotationPlain getPlain() {
        return plain;
    }

    public Collection<FloatingArmorStand> getFloatingArmorStands() {
        return astColl;
    }

    public FloatingArmorStand getHighestArmorStand() {
        FloatingArmorStand highest = null;
        for (FloatingArmorStand ast: astColl) {
            if (highest == null || ast.getY() > highest.getY()) {
                highest = ast;
            }
        }
        return Objects.requireNonNull(highest);
    }

    public void removeArmorStands() {
        astColl.forEach(FloatingArmorStand::removeArmorStand);
        astColl.clear();
    }

}
