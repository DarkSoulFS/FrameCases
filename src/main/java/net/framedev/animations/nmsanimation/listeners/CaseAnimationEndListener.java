package net.framedev.animations.nmsanimation.listeners;

import net.framedev.Main;
import net.framedev.animations.nmsanimation.events.CaseAnimationEndEvent;
import net.framedev.api.Holograms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CaseAnimationEndListener implements Listener {

    @EventHandler
    private void onCaseAnimationEnd(CaseAnimationEndEvent event) {
        Main.isOpen = false;
        Main.items.clear();
        Holograms.hologram(Holograms.locationCase());
    }
}
