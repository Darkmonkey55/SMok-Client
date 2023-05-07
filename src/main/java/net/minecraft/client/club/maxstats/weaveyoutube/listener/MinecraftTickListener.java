package net.minecraft.client.club.maxstats.weaveyoutube.listener;

import club.maxstats.weave.loader.api.event.SubscribeEvent;
import club.maxstats.weave.loader.api.event.RenderWorldEvent;
import net.minecraft.client.me.sleepyfish.smok.utils.TargetUtils;

// Class from SMok Client by SleepyFish
public class MinecraftTickListener {

    @SubscribeEvent
    public void onMyEvent(RenderWorldEvent e) {
        TargetUtils.onUpdate();
    }

}