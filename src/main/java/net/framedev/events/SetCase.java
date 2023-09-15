package net.framedev.events;

import net.framedev.Main;
import net.framedev.others.Coloriser;
import net.framedev.api.Holograms;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class SetCase implements Listener {
	
	private final Main instance = Main.getInstance();
    public static List<Player> setCaseList = new ArrayList<Player>();

    @EventHandler
    public void click(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (setCaseList.contains(player)) {
            try {
                if (event.getClickedBlock().getType() != Material.AIR) {
                    Location location = event.getClickedBlock().getLocation();
                    FileConfiguration config = instance.getConfig();
                    config.set("location.world", location.getWorld().getName());
                    config.set("location.x", location.getX());
                    config.set("location.y", location.getY());
                    config.set("location.z", location.getZ());
                    config.set("location.pitch", location.getPitch());
                    config.set("location.yaw", location.getYaw());
                    instance.saveConfig();

                    player.sendMessage(Coloriser.colorify(config.getString("messages.case-position-put")));
                    event.setCancelled(true);

                    Holograms.hologram(Holograms.locationCase());
                    setCaseList.remove(player);
                }
            } catch (NullPointerException ignored) {

            }
        }
    }
}
