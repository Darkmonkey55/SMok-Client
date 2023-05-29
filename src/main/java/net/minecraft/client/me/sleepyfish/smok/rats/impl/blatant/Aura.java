package net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.*;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SpaceSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Iterator;

// Class from SMok Client by SleepyFish
public class Aura extends Rat {

    BoolSetting fov;
    SlideSetting fovRange;

    public static SlideSetting attackRange;
    SlideSetting rotationRange;
    SlideSetting cpsMax;
    SlideSetting cpsMin;

    BoolSetting thruWalls;
    BoolSetting blockBlocking;
    BoolSetting autoBlockAnimation;
    public static BoolSetting esp;
    BoolSetting ignoreFriends;

    ModeSetting<Enum<?>> rotationMode;
    SlideSetting randomYawMin;
    SlideSetting randomYawMax;

    public static Timer.Better renderFakeBlockTimer;
    public static boolean renderFakeBlock;

    public Aura() {
        super(Var.aura_name, Category.Blatant, Var.aura_desc);
    }

    @Override
    public void setup() {
        this.addSetting(attackRange = new SlideSetting(Var.aura_attack_range, 3.2, 3, 5, 0.2));
        this.addSetting(rotationRange = new SlideSetting(Var.aura_rotating_range, 3.6, 3.4, 5.6, 0.2));
        this.addSetting(cpsMin = new SlideSetting(Var.aura_cpsMin, 9, 1, 24, 1));
        this.addSetting(cpsMax = new SlideSetting(Var.aura_cpsMax, 13, 2, 25, 1));
        this.addSetting(fov = new BoolSetting(Var.aura_fov, true));
        this.addSetting(fovRange = new SlideSetting(Var.aura_fovRange, 90, 20, 180, 10));
        this.addSetting(ignoreFriends = new BoolSetting(Var.aura_ignoreFriends, true));
        this.addSetting(thruWalls = new BoolSetting(Var.aura_thruWalls, true));
        this.addSetting(blockBlocking = new BoolSetting(Var.aura_blockBlocking, true));
        this.addSetting(autoBlockAnimation = new BoolSetting(Var.aura_autoBlockAnimation, true));
        this.addSetting(new SpaceSetting());
        this.addSetting(esp = new BoolSetting(Var.aura_esp, true));
        this.addSetting(new SpaceSetting());
        this.addSetting(rotationMode = new ModeSetting<>(Var.aura_mode, modes.Derp));
        this.addSetting(randomYawMin = new SlideSetting(Var.aura_yawMin, 2, 0, 20, 0.2));
        this.addSetting(randomYawMax = new SlideSetting(Var.aura_yawMax, 4, 0, 22, 0.2));
        renderFakeBlockTimer = new Timer.Better();
        renderFakeBlock = false;
    }

    @Override
    public void onEnableEvent() {
        Smok.inst.rotManager.setRotating(false);
        if (autoBlockAnimation.isEnabled()) {
            renderFakeBlockTimer.reset();
            renderFakeBlock = false;
        }
    }

    @Override
    public void onDisableEvent() {
        Smok.inst.rotManager.setRotating(false);
        TargetUtils.setTarget(null);
        if (autoBlockAnimation.isEnabled()) {
            renderFakeBlockTimer.reset();
            renderFakeBlock = false;
        }
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.inInv() || ClientUtils.inClickGui())
            if (TargetUtils.getTarget() != null)
                this.rotateUrBalls();

        if (Utils.canLegitWork()) {
            TargetUtils.setTarget(this.getTarget());

            if (TargetUtils.getTarget() == null || !Utils.holdingWeapon())
                return;

            this.rotateUrBalls();

            if (TargetUtils.getTarget() != mc.thePlayer) {
                if (Utils.Combat.inRange(TargetUtils.getTarget(), attackRange.getValue())) {
                    if (Timer.cpsTimer(cpsMin.getValueToInt(), cpsMax.getValueToInt())) {
                        if (blockBlocking.isEnabled())
                            if (mc.thePlayer.isBlocking())
                                return;

                        mc.thePlayer.swingItem();
                        mc.playerController.attackEntity(mc.thePlayer, TargetUtils.getTarget());
                    }
                }
            }
        }
    }

    private float[] getRotations(Entity target) {
        float[] rots = Utils.Combat.getTargetRotations(target);

        float yaw = mc.thePlayer.rotationYaw;
        float pitch = mc.thePlayer.rotationPitch;

        if (rotationMode.getMode() == modes.Derp) {
            return new float[]{rots[0], rots[1]};
        }

        if (rotationMode.getMode() == modes.Random) {
            yaw = rots[0] + MathUtils.randomFloat(randomYawMin.getValueToFloat(), randomYawMax.getValueToFloat());
            pitch = rots[1] + MathUtils.randomFloat(-2.2F, 3.8F);

            return new float[]{yaw, pitch};
        }

        if (rotationMode.getMode() == modes.Smooth) {
            float yaww = 0;
            float pitchh = 0;

            if (rots[0] > yaw)
                yaww++;
            else yaww--;

            if (rots[1] > pitch)
                pitchh++;
            else pitchh--;

            return new float[]{yaww, pitchh};
        }

        return new float[]{yaw, pitch};
    }

    private void rotateUrBalls() {
        float[] rotations = this.getRotations(TargetUtils.getTarget());

        float sens = Smok.inst.rotManager.getSensitivity();

        if (TargetUtils.getTarget() == null || rotations == null || !Utils.holdingWeapon()) {
            Smok.inst.rotManager.setRotating(false);
            return;
        }

        if (TargetUtils.getTarget() != mc.thePlayer) {

            if (Utils.Combat.inRange(TargetUtils.getTarget(), rotationRange.getValue())) {
                if (autoBlockAnimation.isEnabled()) {
                    renderFakeBlock = true;
                }

                Smok.inst.rotManager.setRotations(rotations[0] + MathUtils.nextFloat(), rotations[1] + MathUtils.nextFloat());
                Smok.inst.rotManager.rayTrace(TargetUtils.getTarget());
                Smok.inst.rotManager.setRotating(true);
            }

            if (TargetUtils.getTarget().posY < 0)
                rotations[1] = 1;
            else if (TargetUtils.getTarget().posY > 255)
                rotations[1] = 90;

            if (Math.abs(TargetUtils.getTarget().posX - TargetUtils.getTarget().lastTickPosX) > 0.50 || Math.abs(TargetUtils.getTarget().posZ - TargetUtils.getTarget().lastTickPosZ) > 0.50) {
                ClientUtils.addMessage("resolved angle: X: " + (int) TargetUtils.getTarget().posX + ", Y: " + (int) TargetUtils.getTarget().posY + ", Z: " + (int) TargetUtils.getTarget().posZ);
                return;

                //TargetUtils.getTarget().setEntityBoundingBox(new AxisAlignedBB(TargetUtils.getTarget().posX, TargetUtils.getTarget().posY, TargetUtils.getTarget().posZ, TargetUtils.getTarget().lastTickPosX, TargetUtils.getTarget().lastTickPosY, TargetUtils.getTarget().lastTickPosZ));
            }

            rotations[0] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationYaw, rotations[0], 360);
            rotations[1] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationPitch, rotations[1], 90);

            rotations[0] = Math.round(rotations[0] / sens) * sens;
            rotations[1] = Math.round(rotations[1] / sens) * sens;
        }
    }

    private Entity getTarget() {
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
                                        do {
                                            if (!players.hasNext()) {
                                                Smok.inst.rotManager.setRotating(false);
                                                return null;
                                            }

                                            target = players.next();
                                        } while (target == mc.thePlayer);
                                    } while (BotUtils.isBot(target));
                                } while (target.isDead);
                            } while (target.isInvisible());
                        } while (!thruWalls.isEnabled() && !mc.thePlayer.canEntityBeSeen(target));
                    } while (mc.thePlayer.isOnSameTeam(target));
                } while (ignoreFriends.isEnabled() && FriendUtils.isFriend(target));
            } while (!Utils.Combat.inRange(target, rotationRange.getValue()));
        } while (fov.isEnabled() && !Utils.Combat.isInFov(target, fovRange.getValueToFloat()));

        return target;
    }

    public enum modes {
        Random, Smooth, Derp
    }

}