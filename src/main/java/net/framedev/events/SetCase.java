package net.framedev.events;

import net.framedev.Main;
import net.framedev.others.S;
import net.framedev.api.Holograms;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class SetCase implements Listener {

    public static List<Player> setCaseList = new ArrayList<Player>();

    @EventHandler
    public void click(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (setCaseList.contains(player)) {
            try {
                if (event.getClickedBlock().getType() != Material.AIR) {
                    Location location = event.getClickedBlock().getLocation();
                    Main.getInstance().getConfig().set("location.world", location.getWorld().getName());
                    Main.getInstance().saveConfig();
                    Main.getInstance().getConfig().set("location.x", location.getX());
                    Main.getInstance().saveConfig();
                    Main.getInstance().getConfig().set("location.y", location.getY());
                    Main.getInstance().saveConfig();
                    Main.getInstance().getConfig().set("location.z", location.getZ());
                    Main.getInstance().saveConfig();
                    Main.getInstance().getConfig().set("location.pitch", location.getPitch());
                    Main.getInstance().saveConfig();
                    Main.getInstance().getConfig().set("location.yaw", location.getYaw());
                    Main.getInstance().saveConfig();

                    player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.case-position-put")));
                    event.setCancelled(true);

                    Holograms.hologram(Holograms.locationCase());
                    setCaseList.remove(player);
                }
            } catch (NullPointerException ignored) {

            }
        }
    }
}
