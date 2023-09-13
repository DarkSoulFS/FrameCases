package net.framedev.animations.nmsanimation.events;

import net.framedev.animations.nmsanimation.entities.AnimationArmorStand;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class CaseAnimationEndEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    private final AnimationArmorStand anim;
    private boolean removeArmorStands = true;
    private long removeArmorStandsDelay;

    public CaseAnimationEndEvent(AnimationArmorStand anim) {
        this.anim = anim;
    }

    public AnimationArmorStand getAnimation() {
        return anim;
    }



    public boolean isRemovingArmorStands() {
        return removeArmorStands;
    }

    public void setRemovingArmorStands(boolean value) {
        removeArmorStands = value;
    }

    public long getRemoveArmorStandsDelay() {
        return removeArmorStandsDelay;
    }

    public void setRemoveArmorStandsDelay(long value) {
        removeArmorStandsDelay = value;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
