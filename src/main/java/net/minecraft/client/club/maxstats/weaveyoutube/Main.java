package net.minecraft.client.club.maxstats.weaveyoutube;

import net.minecraft.client.club.maxstats.weaveyoutube.listener.TickListener;
import net.minecraft.client.club.maxstats.weaveyoutube.listener.RenderListener;
import net.minecraft.client.club.maxstats.weaveyoutube.listener.RenderGameOverlayListener;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.EventBus;
import net.weavemc.loader.api.event.KeyboardEvent;

// Class from SMok Client by SleepyFish
public class Main implements ModInitializer {

    @Override
    public void preInit() {
        ClientUtils.checkOS();

        Smok.inst.saveAndSetTitle();
        Smok.inst.juue_tea_and_tha_bri_ischhh_init();

        EventBus.subscribe(KeyboardEvent.class, e -> {
            if (!ClientUtils.inClickGui()) {
                if (Smok.inst.mc.currentScreen == null && e.getKeyState()) {
                    if (e.getKeyCode() == ClientUtils.getBind(1))
                        Smok.inst.mc.displayGuiScreen(Smok.inst.guiManager.getClickGui());

                    for (Rat m : Smok.inst.ratManager.getBigRats())
                        if (e.getKeyCode() == m.getBind())
                            m.toggle();
                }
            }
        });

        EventBus.subscribe(new RenderListener());
        EventBus.subscribe(new TickListener());
        EventBus.subscribe(new RenderGameOverlayListener());
    }

}