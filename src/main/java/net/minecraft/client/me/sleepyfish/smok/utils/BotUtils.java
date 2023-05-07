package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

// Class from SMok Client by SleepyFish
public class BotUtils {

    public static boolean isBot(Entity target) {
        return target.getUniqueID().version() == 2 && target.ticksExisted > 9999 || target.getName().contains("-") || Smok.inst.mc.thePlayer.isInvisibleToPlayer((EntityPlayer) target);
    }

}