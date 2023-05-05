package net.minecraft.client.club.maxstats.weaveyoutube.event;

import net.minecraft.client.me.sleepyfish.smok.rats.event.Event;
import net.minecraft.client.gui.ScaledResolution;

// Class from SMok Client by SleepyFish
public class EventRender2D extends Event {

    private final float var1;
    private final ScaledResolution var2;

    public EventRender2D(ScaledResolution sr, float partialTicks) {
        this.var1 = partialTicks;
        this.var2 = sr;
    }

    public float getPartialTicks() {
        return var1;
    }

    public ScaledResolution getSr() {
        return var2;
    }

}