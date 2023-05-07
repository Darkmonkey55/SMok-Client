package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.Notification;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;

// Class from SMok Client by SleepyFish
public class TargetUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final Notification.Timer timer = new Notification.Timer();
    private static Entity target;

    public static void onUpdate() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
            if (mc.objectMouseOver.entityHit instanceof EntityPlayer && inTab(mc.objectMouseOver.entityHit)) {
                target = mc.objectMouseOver.entityHit;
                timer.reset();
            }
        } else if (timer.delay(2500)) {
            target = null;
            timer.reset();
        }

        if (target != null) {
            if (target.isDead)
                target = null;

            if (mc.thePlayer.isDead)
                target = null;

            if (target != null)
                if (target.isInvisible())
                    target = null;

            if (mc.thePlayer != null && target != null)
                if (target.getDistanceToEntity(mc.thePlayer) > 12)
                    target = null;
        }
    }

    public static Entity getTarget() {
        return target;
    }

    public static void setTarget(Entity target) {
        TargetUtils.target = target;
    }

    public static boolean inTab(Entity entity) {
        if (mc.getCurrentServerData() != null)
            for (NetworkPlayerInfo item : mc.getNetHandler().getPlayerInfoMap())
                if (item != null && item.getGameProfile() != null && item.getGameProfile().getName().contains(entity.getName()))
                    return true;

        return false;
    }

}