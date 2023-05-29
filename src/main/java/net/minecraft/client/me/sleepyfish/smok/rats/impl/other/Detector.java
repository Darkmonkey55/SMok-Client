package net.minecraft.client.me.sleepyfish.smok.rats.impl.other;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.*;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SpaceSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.entity.player.EntityPlayer;
import com.mojang.realmsclient.gui.ChatFormatting;

// Class from SMok Client by SleepyFish
public class Detector extends Rat {

    BoolSetting flyChecks;

    BoolSetting speedChecks;
    SlideSetting speedValue;

    BoolSetting reachChecks;
    SlideSetting reachValue;

    BoolSetting veloChecks;
    SlideSetting veloValue;

    BoolSetting gooberChecks;
    BoolSetting chatMessage;

    private Timer.Better timer;

    public Detector() {
        super(Var.detector_name, Category.Other, Var.detector_desc);
    }

    @Override
    public void setup() {
        this.addSetting(flyChecks = new BoolSetting(Var.detector_fly, false));
        this.addSetting(speedChecks = new BoolSetting(Var.detector_speed, false));
        this.addSetting(speedValue = new SlideSetting(Var.detector_speed_value, 1.06, 1.001, 3, 0.02));
        this.addSetting(veloChecks = new BoolSetting(Var.detector_velocity, true));
        this.addSetting(veloValue = new SlideSetting(Var.detector_velocity_value, 1.86, 1.6, 3, 0.02));
        this.addSetting(reachChecks = new BoolSetting(Var.detector_reach, true));
        this.addSetting(reachValue = new SlideSetting(Var.detector_reach_value, 3.1, 3, 5, 0.02));
        this.addSetting(gooberChecks = new BoolSetting(Var.detector_goober, false));
        this.addSetting(new SpaceSetting());

        if (Smok.inst.debugMode)
            this.addSetting(chatMessage = new BoolSetting("Tick message", false));

        this.timer = new Timer.Better();
    }

    @SmokEvent
    public void onTick(EventTick e) {
        for (EntityPlayer target : mc.theWorld.playerEntities)
            if (target != mc.thePlayer)
                if (!BotUtils.isBot(target)) {
                    if (this.gooberChecks(target))
                        this.send(target, "big goober");

                    if (this.flyChecks(target))
                        this.send(target, "fly");

                    if (this.speedChecks(target))
                        this.send(target, "speed");

                    if (this.veloABhopChecks(target))
                        this.send(target, "velocity / bhop");
                }
    }

    /**
     * @author sleepyfish
     * @info checks for goobers
     */
    private boolean gooberChecks(EntityPlayer target) {
        if (gooberChecks.isEnabled()) {
            if (Smok.inst.gooberUtils.getGoobers().contains(target.getName().toLowerCase()))
                return true;

            if (target.randomUnused1 == Smok.inst.gooberUtils.randomUnused1)
                return true;

            if (chatMessage.isEnabled())
                ClientUtils.addDebug("goober check tick");
        }

        return false;
    }

    /**
     * @author sleepyfish
     * @info basic / garbage fly checks by sleepyfish
     */
    private boolean flyChecks(EntityPlayer target) {
        if (flyChecks.isEnabled()) {
            if (target.capabilities.isFlying && !target.isSpectator() && !target.isDead && target.moveForward > 0.9F)
                return true;

            if (chatMessage.isEnabled())
                ClientUtils.addDebug("fly check tick");
        }

        return false;
    }

    /**
     * @author sleepyfish
     * @info garbage speed checks by sleepyfish
     */
    private boolean speedChecks(EntityPlayer target) {
        if (speedChecks.isEnabled()) {

            if (!target.capabilities.isFlying)
                if (target.moveForward > speedValue.getValue() || target.moveStrafing > speedValue.getValue())
                    return true;

            if (target.isSneaking() && target.onGround && !target.velocityChanged && target.hurtTime != 0)
                return true;

            if (chatMessage.isEnabled())
                ClientUtils.addDebug("speed check tick");
        }

        return false;
    }

    /**
     * @author sleepyfish
     * @info mid velocity checks by sleepyfish
     */
    private boolean veloABhopChecks(EntityPlayer target) {
        if (veloChecks.isEnabled()) {
            if (target.motionX > veloValue.getValue() || target.motionZ > veloValue.getValue() || target.motionY > veloValue.getValue())
                return true;

            if (chatMessage.isEnabled())
                ClientUtils.addDebug("velo & bhop check tick");
        }

        return false;
    }

    /**
     * @author sleepyfish
     * @info insane reach checks by sleepyfish
     */
    private boolean reachChecks(EntityPlayer target) {
        if (reachChecks.isEnabled()) {
            if (Utils.Combat.inRange(target, reachValue.getValue() + 4D) && target.swingProgress > 0) {
                if (mc.thePlayer.posX - target.posX > reachValue.getValue())
                    return true;

                if (mc.thePlayer.posY - target.posY > reachValue.getValue())
                    return true;

                if (mc.thePlayer.posZ - target.posZ > reachValue.getValue())
                    return true;
            }

            if (!target.canAttackPlayer(mc.thePlayer))
                return true;

            if (chatMessage.isEnabled())
                if (Utils.Combat.inRange(target, reachValue.getValue() + 3.2D) && mc.thePlayer.hurtTime != 0) {
                    ClientUtils.addDebug("X: " + (mc.thePlayer.posX - target.posX));
                    ClientUtils.addDebug("Y: " + (mc.thePlayer.posY - target.posY));
                    ClientUtils.addDebug("Z: " + (mc.thePlayer.posZ - target.posZ));
                }

            if (chatMessage.isEnabled())
                ClientUtils.addDebug("reach check tick");
        }

        return false;
    }

    private void send(EntityPlayer target, String str) {
        if (target != null && str != null)
            if (timer.delay(300L)) {
                ClientUtils.addMessage(Var.detector_name + ": " + target.getName() + ChatFormatting.RED + " failed " + ChatFormatting.WHITE + str + ".");
                timer.reset();
            }
    }

}
