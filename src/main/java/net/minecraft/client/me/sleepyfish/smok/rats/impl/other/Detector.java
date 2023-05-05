package net.minecraft.client.me.sleepyfish.smok.rats.impl.other;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.*;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SpaceSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.Notification;
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
    ModeSetting<Enum<?>> mode;

    public Detector() {
        super(Var.detector_name, Category.Other, Var.detector_desc);
    }

    @Override
    public void setup() {
        this.addSetting(flyChecks = new BoolSetting(Var.detector_fly, true));
        this.addSetting(speedChecks = new BoolSetting(Var.detector_speed, true));
        this.addSetting(speedValue = new SlideSetting(Var.detector_speed_value, 1.06, 1.001, 3, 0.001));
        this.addSetting(veloChecks = new BoolSetting(Var.detector_velocity, true));
        this.addSetting(veloValue = new SlideSetting(Var.detector_velocity_value, 1.86, 1.6, 3, 0.02));
        this.addSetting(reachChecks = new BoolSetting(Var.detector_reach, true));
        this.addSetting(reachValue = new SlideSetting(Var.detector_reach_value, 3.08, 3, 5, 0.02));
        this.addSetting(gooberChecks = new BoolSetting(Var.detector_goober, true));
        this.addSetting(new SpaceSetting());
        this.addSetting(mode = new ModeSetting<>(Var.detector_mode, modes.Notification));

        if (Smok.inst.debugMode)
            this.addSetting(chatMessage = new BoolSetting("Tick message", false));
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (mc.theWorld != null)
            if (!mc.isSingleplayer()) {
                for (EntityPlayer target : mc.theWorld.playerEntities) {
                    if (!BotUtils.isBot(target)) {
                        if (target != mc.thePlayer) {
                            if (this.gooberChecks(target))
                                this.send(target, "big goober");

                            if (this.flyChecks(target))
                                this.send(target, "fly");

                            if (this.speedChecks(target))
                                this.send(target, "speed");

                            if (this.veloChecks(target))
                                this.send(target, "velocity");

                            if (this.reachChecks(target))
                                this.send(target, "reach");
                        }
                    }
                }
            }
    }

    /**
     * @author sleepyfish
     * @info checks for goobers
     */
    private boolean gooberChecks(EntityPlayer target) {
        if (gooberChecks.isToggled()) {
            if (Smok.inst.gooberUtils.getGoobers().contains(target.getName().toLowerCase()))
                return true;

            if (target.randomUnused1 == Smok.inst.gooberUtils.randomUnused1)
                return true;

            if (chatMessage.isToggled())
                ClientUtils.addDebug("goober check tick");
        }

        return false;
    }

    /**
     * @author sleepyfish
     * @info basic / garbage fly checks by sleepyfish
     */
    private boolean flyChecks(EntityPlayer target) {
        if (flyChecks.isToggled()) {
            if (target.capabilities.isFlying && !target.isSpectator() && !target.isDead && target.moveForward > 0.9F)
                return true;

            if (chatMessage.isToggled())
                ClientUtils.addDebug("fly check tick");
        }

        return false;
    }

    /**
     * @author sleepyfish
     * @info garbage speed checks by sleepyfish
     */
    private boolean speedChecks(EntityPlayer target) {
        if (speedChecks.isToggled()) {

            if (!target.capabilities.isFlying)
                if (target.moveForward > speedValue.getValue() || target.moveStrafing > speedValue.getValue())
                    return true;

            if (target.isSneaking() && target.onGround && !target.velocityChanged && target.hurtTime != 0)
                return true;

            if (chatMessage.isToggled())
                ClientUtils.addDebug("speed check tick");
        }

        return false;
    }

    /**
     * @author sleepyfish
     * @info mid velocity checks by sleepyfish
     */
    private boolean veloChecks(EntityPlayer target) {
        if (veloChecks.isToggled()) {
            if (target.motionX > veloValue.getValue() || target.motionZ > veloValue.getValue() || target.motionY > veloValue.getValue())
                return true;

            if (chatMessage.isToggled())
                ClientUtils.addDebug("velo check tick");
        }

        return false;
    }

    /**
     * @author sleepyfish
     * @info insane reach checks by sleepyfish
     */
    private boolean reachChecks(EntityPlayer target) {
        if (reachChecks.isToggled()) {
            if (mc.thePlayer.hurtTime != 0) {
                if (mc.thePlayer.getPosition().getX() - target.getPosition().getX() > reachValue.getValue())
                    return true;

                if (mc.thePlayer.getPosition().getY() - target.getPosition().getY() > reachValue.getValue())
                    return true;

                if (mc.thePlayer.getPosition().getZ() - target.getPosition().getZ() > reachValue.getValue())
                    return true;
            }

            if (!target.canAttackPlayer(mc.thePlayer) && mc.thePlayer.getLastAttacker() == target)
                return true;

            if (chatMessage.isToggled())
                ClientUtils.addDebug("reach check tick");
        }

        return false;
    }

    private void send(EntityPlayer target, String str) {
        if (mode.getMode() == modes.Chat)
            if (Timer.hasTimeElapsed(500L, true))
                ClientUtils.addMessage(Var.detector_name + ": " + target.getName() + ChatFormatting.RED + " failed " + ChatFormatting.WHITE + str + ".");

        if (mode.getMode() == modes.Notification)
            if (Timer.hasTimeElapsed(2200L, true))
                ClientUtils.notify(ChatFormatting.RED + Var.detector_name, target.getName() + ChatFormatting.RED + " failed " + ChatFormatting.WHITE + str + ".", Notification.Icon.Info, 2);
    }

    public enum modes {
        Chat, Notification
    }

}