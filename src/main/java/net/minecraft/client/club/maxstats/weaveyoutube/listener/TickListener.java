package net.minecraft.client.club.maxstats.weaveyoutube.listener;

import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.RenderWorldEvent;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.TargetUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;

// Class from SMok Client by SleepyFish
public class TickListener {

    private Timer.Better timer = new Timer.Better();

    @SubscribeEvent
    public void onMyEvent(RenderWorldEvent e) {
        if (Smok.inst.rotManager.raytracePos != null) {
            RenderUtils.drawBlock(Smok.inst.rotManager.raytracePos, ColorUtils.getClientColor(1).getRGB());
        }

        TargetUtils.onUpdate();
    }

}