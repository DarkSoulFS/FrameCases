package net.framedev.others;

import me.filoghost.holographicdisplays.api.hologram.Hologram;
import net.framedev.Main;
import net.framedev.api.Holograms;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class BlockParticles {

    private static final boolean isPower = Main.getInstance().getConfig().getBoolean("block-particles.power");

    public static void startStarfallAnimation() {
        if (isPower) {
            new BukkitRunnable() {
                public void run() {
                    try {
                        Location centerLocation = Holograms.locationCase().add(0.45, 0.3, 0.45);
                        for (int i = 0; i < 30; i++) {
                            double offsetX = Math.random() * 4.0D - 2.0D;
                            double offsetY = Math.random() * 4.0D - 2.0D;
                            double offsetZ = Math.random() * 4.0D - 2.0D;
                            Location particleLocation = centerLocation.clone().add(offsetX, offsetY, offsetZ);
                            centerLocation.getWorld().spawnParticle(Particle.valueOf(Main.getInstance().getConfig().getString("block-particles.particle")), centerLocation, 1, 0.0D, 0.0D, 0.0D, 0.1D);
                        }
                    } catch (NullPointerException exception) {

                    }
                }
            }.runTaskTimer(Main.getInstance(), 0L, 20L);
        }
    }
}
