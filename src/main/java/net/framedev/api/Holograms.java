package net.framedev.api;

import net.framedev.Main;
import net.framedev.others.S;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Holograms {

    public static HolographicDisplaysAPI api = HolographicDisplaysAPI.get(Main.getInstance());

    public static void hologram(Location location) {

        try {
            api.deleteHolograms();
            Hologram hologram = api.createHologram(location.add(0.45, 1.9, 0.45));
            for (String s : Main.getInstance().getConfig().getStringList("holograms")) {
                TextHologramLine textHologramLine = hologram.getLines().appendText(S.s(s));
            }
        } catch (NullPointerException | IllegalArgumentException ignored) {

        }
    }

    public static Location locationCase() {
        try {
            String w = Main.getInstance().getConfig().getString("location.world");
            World world = Bukkit.getWorld(w);
            double x = Main.getInstance().getConfig().getDouble("location.x");
            double y = Main.getInstance().getConfig().getDouble("location.y");
            double z = Main.getInstance().getConfig().getDouble("location.z");
            float pitch = (float) Main.getInstance().getConfig().getDouble("location.pitch");
            float yaw = (float) Main.getInstance().getConfig().getDouble("location.yaw");
            return new Location(world, x, y, z, pitch, yaw);
        } catch (NullPointerException | IllegalArgumentException ignored) {

        }
        return null;
    }

    public static void removeHD() {
        api.deleteHolograms();
    }
}
