package net.minecraft.client.club.maxstats.weaveyoutube.listener;

import club.maxstats.weave.loader.api.event.SubscribeEvent;
import club.maxstats.weave.loader.api.event.RenderWorldEvent;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.TargetUtils;
import org.lwjgl.input.Keyboard;

// Class from SMok Client by SleepyFish
public class MinecraftTickListener {

    @SubscribeEvent
    public void onMyEvent(RenderWorldEvent e) {
        if (Utils.canLegitWork() && !Utils.inGui()) {
            if (Keyboard.isKeyDown(Smok.inst.getBind(1)))
                if (Timer.hasTimeElapsed(200L, true))
                    Smok.inst.mc.displayGuiScreen(Smok.inst.guiManager.getClickGui());

            for (Rat m : Smok.inst.ratManager.getBigRats())
                if (Keyboard.isKeyDown(m.getKeycode()))
                    if (Timer.hasTimeElapsed(200L, true))
                        m.toggle();
        }

        TargetUtils.onUpdate();
    }

}