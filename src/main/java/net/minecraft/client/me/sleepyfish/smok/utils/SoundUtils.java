package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.entity.player.EntityPlayer;

// Class from SMok Client by SleepyFish
public class SoundUtils {

    public final static String chestClose = "random.chestclosed";
    public final static String chestOpen = "random.chestopen";
    public final static String click = "random.click";

    public static void playSound(String name, float volume, float pitch) {
        EntityPlayer player = Smok.inst.mc.thePlayer;

        switch (name) {
            case SoundUtils.click:
                player.playSound(SoundUtils.click, volume, pitch);
                break;

            case SoundUtils.chestOpen:
                player.playSound(SoundUtils.chestOpen, volume, pitch);
                break;

            case SoundUtils.chestClose:
                player.playSound(SoundUtils.chestClose, volume, pitch);
                break;

            default:
                player.playSound(name, volume, pitch);
                break;
        }
    }

}