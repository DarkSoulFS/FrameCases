package net.framedev.api;

import net.framedev.Main;
import net.framedev.others.Coloriser;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class Holograms {

	private static final Main instance = Main.getInstance();
    public static HolographicDisplaysAPI api = HolographicDisplaysAPI.get(instance);
    public static void hologram(Location location) {

        try {
            api.deleteHolograms();
            Hologram hologram = api.createHologram(location.add(0.45, 1.9, 0.45));
            for (String s : instance.getConfig().getStringList("holograms")) {
                TextHologramLine textHologramLine = hologram.getLines().appendText(Coloriser.colorify(s));
            }
        } catch (NullPointerException | IllegalArgumentException ignored) {

        }
    }

    public static Location locationCase() {
        try {
        	ConfigurationSection location = instance.getConfig().getConfigurationSection("location");
            String w = location.getString("world");
            World world = Bukkit.getWorld(w);
            double x = location.getDouble("x");
            double y = location.getDouble("y");
            double z = location.getDouble("z");
            float pitch = (float) location.getDouble("pitch");
            float yaw = (float) location.getDouble("yaw");
            return new Location(world, x, y, z, pitch, yaw);
        } catch (NullPointerException | IllegalArgumentException ignored) {

        }
        return null;
    }

    public static void removeHD() {
        api.deleteHolograms();
    }
}
