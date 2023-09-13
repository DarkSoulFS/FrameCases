package net.framedev.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import net.framedev.guis.GUI;
import net.framedev.Main;
import net.framedev.others.S;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class ClickBlock implements Listener {

    @EventHandler
    public void click(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        try {
            String w = Main.getInstance().getConfig().getString("location.world");
            World world = Bukkit.getWorld(w);
            double x = Main.getInstance().getConfig().getDouble("location.x");
            double y = Main.getInstance().getConfig().getDouble("location.y");
            double z = Main.getInstance().getConfig().getDouble("location.z");
            float pitch = (float) Main.getInstance().getConfig().getDouble("location.pitch");
            float yaw = (float) Main.getInstance().getConfig().getDouble("location.yaw");

            Location locationCase = new Location(world, x, y, z, pitch, yaw);

            if (Objects.requireNonNull(event.getClickedBlock()).getLocation().equals(locationCase)) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (Main.isOpen) {
                        player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.case-is-opened")));
                        event.setCancelled(true);
                        return;
                    }
                    event.setCancelled(true);
                    player.openInventory(GUI.inventory(player));
                }
            }
        } catch (NullPointerException | IllegalArgumentException exception) {

        }
    }

    public static void setChestOpened(Block block, boolean opened) {
        try {
            PacketContainer libPacket = new PacketContainer(PacketType.Play.Server.BLOCK_ACTION);
            libPacket.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
            libPacket.getIntegers().write(0, 1);
            libPacket.getIntegers().write(1, opened ? 1 : 0);
            libPacket.getBlocks().write(0, block.getType());
            int distanceSquared = 64 * 64;
            Location loc = block.getLocation();
            ProtocolManager manager = ProtocolLibrary.getProtocolManager();
            for (Player player : block.getWorld().getPlayers()) {
                if (player.getLocation().distanceSquared(loc) < distanceSquared) {
                    manager.sendServerPacket(player, libPacket);
                }
            }
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Невозможно отправить пакет на открытие сундука, проверьте, установлен ли ProtocolLib");
        }
    }
}
