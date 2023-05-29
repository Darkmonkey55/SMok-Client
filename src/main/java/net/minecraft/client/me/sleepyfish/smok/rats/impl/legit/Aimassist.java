package net.minecraft.client.me.sleepyfish.smok.rats.impl.legit;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.BotUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FriendUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Iterator;

// Class from SMok Client by SleepyFish
public class Aimassist extends Rat {

    SlideSetting speed1;
    SlideSetting speed2;
    SlideSetting range;
    SlideSetting fov;

    BoolSetting weaponOnly;
    BoolSetting aimlock;
    BoolSetting aimlockSilent;
    BoolSetting ignoreFriends;

    private boolean allowReset;

    public Aimassist() {
        super(Var.aimassist_name, Category.Legit, Var.aimassist_desc);
    }

    @Override
    public void setup() {
        this.addSetting(speed1 = new SlideSetting(Var.aimassist_speed1, 30, 1, 100, 1));
        this.addSetting(speed2 = new SlideSetting(Var.aimassist_speed2, 30, 1, 100, 1));
        this.addSetting(fov = new SlideSetting(Var.aimassist_fov, 75, 15, 360, 5));
        this.addSetting(range = new SlideSetting(Var.aimassist_range, 3.2, 1, 6, 0.2));
        this.addSetting(weaponOnly = new BoolSetting(Var.aimassist_weapon_only, true));
        this.addSetting(aimlock = new BoolSetting(Var.aimassist_aimlock, false));
        this.addSetting(aimlockSilent = new BoolSetting(Var.aimassist_aimlock_silent, false));
        this.addSetting(ignoreFriends = new BoolSetting(Var.aimassist_ignore_friends, true));
        this.allowReset = false;
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            if (weaponOnly.isEnabled())
                if (!Utils.holdingWeapon())
                    return;

            Entity target = this.getTarget();
            if (target != null) {
                double n = Utils.Combat.fovFromEntity(target);

                if (aimlock.isEnabled()) {
                    float[] rot = Utils.Combat.getTargetRotations(target);

                    if (aimlockSilent.isEnabled()) {
                        mc.thePlayer.cameraYaw = rot[0];
                        mc.thePlayer.cameraPitch = rot[1];

                        if (this.allowReset)
                            Smok.inst.rotManager.setRotating(false);
                    } else {
                        Smok.inst.rotManager.setRotations(rot[0] + MathUtils.nextFloat(), rot[1] + MathUtils.nextFloat());
                        Smok.inst.rotManager.setRotating(true);
                        this.allowReset = true;
                    }
                } else {
                    if (this.allowReset)
                        Smok.inst.rotManager.setRotating(false);

                    if (n > 1D || n < -1D) {
                        double complimentSpeed = n * (MathUtils.nextDouble(speed2.getValue() - 1.47328, speed2.getValue() + 2.48293) / 100);
                        float val = (float) (-(complimentSpeed + n / (101.0D - (float) MathUtils.nextDouble(speed1.getValue() - 4.723847, speed1.getValue()))));
                        mc.thePlayer.rotationYaw += val;
                    }
                }
            } else
                if (this.allowReset)
                    Smok.inst.rotManager.setRotating(false);
        }
    }

    Entity getTarget() {
        Iterator<EntityPlayer> players = mc.theWorld.playerEntities.iterator();

        EntityPlayer target;
        do {
            do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!players.hasNext()) {
                                            if (this.allowReset)
                                                Smok.inst.rotManager.setRotating(false);

                                            return null;
                                        }

                                        target = players.next();
                                    } while (target == mc.thePlayer);
                                } while (BotUtils.isBot(target));
                            } while (target.isDead);
                        } while (target.isInvisible());
                    } while (mc.thePlayer.isOnSameTeam(target));
                } while (ignoreFriends.isEnabled() && FriendUtils.isFriend(target));
            } while (!Utils.Combat.inRange(target, range.getValue()));
        } while (!aimlock.isEnabled() && !Utils.Combat.isInFov(target, (float) fov.getValue()));

        return target;
    }

}