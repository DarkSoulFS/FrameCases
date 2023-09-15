package net.framedev.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EquItems implements Listener {

    @EventHandler
    public void clickEvent(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            if (event.getRightClicked().hasMetadata("case_")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ArmorStand) {
            if (event.getEntity().hasMetadata("case_")) {
                event.setCancelled(true);
            }
        }
    }
}
