package net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;

// Class from SMok Client by SleepyFish
public class No_Slow extends Rat {

    public No_Slow() {
        super(Var.noslow_name, Category.Blatant, Var.noslow_desc);
    }

    @SmokEvent
    public void onRender(EventTick e) {
        if (Utils.canLegitWork()) {
            if (!mc.thePlayer.onGround)
                return;

            if (mc.thePlayer.isBlocking()) {
                double currentPlayerSpeed = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                double yaw = Math.toRadians(this.getDirection());
                mc.thePlayer.motionZ = Math.cos(yaw) * currentPlayerSpeed * 1.6;
                mc.thePlayer.motionX = -Math.sin(yaw) * currentPlayerSpeed * 1.6;
            }
        }
    }

    private double getDirection() {
        float rotYaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0F) rotYaw += 180F;
        float forward = 1F;
        if (mc.thePlayer.moveForward < 0F) forward = -0.5F;
        else if (mc.thePlayer.moveForward > 0F) forward = 0.5F;
        if (mc.thePlayer.moveStrafing > 0F) rotYaw -= 90F * forward;
        if (mc.thePlayer.moveStrafing < 0F) rotYaw += 90F * forward;
        return rotYaw;
    }

}