package net.minecraft.client.club.maxstats.weaveyoutube;

import club.maxstats.weave.loader.api.event.EventBus;
import club.maxstats.weave.loader.api.ModInitializer;
import club.maxstats.weave.loader.api.command.CommandBus;
import net.minecraft.client.club.maxstats.weaveyoutube.listener.RenderListener;
import net.minecraft.client.club.maxstats.weaveyoutube.command.ToggleESPCommand;
import net.minecraft.client.club.maxstats.weaveyoutube.listener.MinecraftTickListener;
import net.minecraft.client.me.sleepyfish.smok.Smok;

// Class from SMok Client by SleepyFish
public class Main implements ModInitializer {

    @Override
    public void init() {
        Smok.inst.saveAndSetTitle();
        Smok.inst.juue_tea_and_tha_bri_ischhh_init();
        RenderListener renderListener = new RenderListener();
        MinecraftTickListener minecraftTickListener = new MinecraftTickListener();
        EventBus.subscribe(renderListener);
        EventBus.subscribe(minecraftTickListener);
        CommandBus.register(new ToggleESPCommand(renderListener));
    }

}