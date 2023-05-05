package net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.client.settings.KeyBinding;

// Class from SMok Client by SleepyFish
public class Bunny_Hop extends Rat {

    SlideSetting speed;

    ModeSetting<Enum<?>> mode;

    public Bunny_Hop() {
        super(Var.bunny_hop_name, Category.Blatant, Var.bunny_hop_desc);
    }

    @Override
    public void setup() {
        this.addSetting(mode = new ModeSetting<>(Var.bunny_hop_mode, modes.Smok));
        this.addSetting(speed = new SlideSetting(Var.bunny_hop_speed, 1.2, 1, 5, 0.1));
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            if (Utils.isMoving()) {
                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump();

                if (mode.getMode() == modes.Smok) {
                    if (mc.thePlayer.jumpMovementFactor >= 0.4F) {

                        mc.thePlayer.velocityChanged = false;
                        mc.thePlayer.noClip = true;
                        mc.thePlayer.jumpMovementFactor /= 0.8F;
                    }
                }

                if (mode.getMode() == modes.NCP) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);

                    mc.thePlayer.noClip = true;
                    mc.thePlayer.setSprinting(true);

                    double spd = 0.0025D * speed.getValue();
                    double motionX = mc.thePlayer.motionX * mc.thePlayer.motionX;
                    double motionY = mc.thePlayer.motionZ * mc.thePlayer.motionZ;
                    double forward = mc.thePlayer.movementInput.moveForward;
                    double strafe = mc.thePlayer.movementInput.moveStrafe;
                    float yaw = mc.thePlayer.rotationYaw;

                    double speed = (float) (Math.sqrt(motionX + motionY) + spd);

                    if (forward == 0 && strafe == 0) {
                        mc.thePlayer.motionX = 0;
                        mc.thePlayer.motionZ = 0;
                    } else {
                        if (forward != 0) {
                            if (strafe > 0) {
                                yaw += (float) (forward > 0 ? -45 : 45);
                            } else if (strafe < 0)
                                yaw += (float) (forward > 0 ? 45 : -45);

                            strafe = 0;
                            if (forward > 0)
                                forward = 1;
                            else if (forward < 0)
                                forward = -1;

                            double rad = Math.toRadians(yaw + 90);
                            mc.thePlayer.motionX = forward * speed * Math.cos(rad) + strafe * speed * Math.sin(rad);
                            mc.thePlayer.motionZ = forward * speed * Math.sin(rad) - strafe * speed * Math.cos(rad);
                        }
                    }
                }

                if (mode.getMode() == modes.Spartan)
                    this.ve(speed.getValue());

                if (mode.getMode() == modes.Verus)
                    this.ve(1);
            }
        }
    }

    private void ve(double speed) {
        double currentPlayerSpeed = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
        double yaw = Math.toRadians(this.getDirection());
        mc.thePlayer.motionZ = Math.cos(yaw) * currentPlayerSpeed * speed;
        mc.thePlayer.motionX = -Math.sin(yaw) * currentPlayerSpeed * speed;
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

    public enum modes {
        Smok, NCP, Spartan, Verus;
    }

}