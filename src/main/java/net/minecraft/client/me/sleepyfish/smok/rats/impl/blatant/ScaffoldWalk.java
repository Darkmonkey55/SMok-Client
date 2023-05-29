package net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class ScaffoldWalk extends Rat {

    BoolSetting changeItem;

    private float yaw;
    private float pitch;
    private Scaffold.BlockData blockData;

    private boolean allowPlace;
    private int lastItem;

    public ScaffoldWalk() {
        super("Scaffold 2", Category.Blatant, "Walk with a blunt");
        this.allowPlace = false;
    }

    @Override
    public void onEnableEvent() {
        if (changeItem.isEnabled()) {
            if (!Utils.holdingBlock()) {
                lastItem = mc.thePlayer.inventory.currentItem;
                Utils.changeToBlock();
            }
        }

        this.resetRotation();
    }

    @Override
    public void onDisableEvent() {
        if (changeItem.isEnabled()) {
            mc.thePlayer.inventory.currentItem = lastItem;
        }

        this.resetRotation();
    }

    @SmokEvent
    public void aufLock(EventTick e) {
        BlockPos targetPosition = null;
        EnumFacing targetFacing = EnumFacing.getFacingFromAxis(EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.Y);

        if (this.checks(false)) {
            BlockPos playerPosition = mc.thePlayer.getPosition().add(0, -1, 0);

            if (Utils.getBlock(playerPosition) != Blocks.air) {
                targetPosition = playerPosition;
            }

            if (this.allowPlace) {
                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, Utils.getCurrentItem(), targetPosition, targetFacing, new Vec3(0, 0, 0))) {
                    mc.thePlayer.swingItem();
                    this.allowPlace = false;
                }
            }
        } else {
            this.resetRotation();
        }

        if (this.checks(true)) {
            if (targetPosition != null) {
                float[] rotation = Utils.Combat.getBlockRotations(targetPosition, targetFacing);
                this.startRotation(rotation[0], rotation[1]);
            } else {
                this.startRotation(mc.thePlayer.rotationYaw, MathUtils.randomFloat(83.2F, 89.4F));
            }

            this.allowPlace = true;
        } else {
            this.resetRotation();
        }
    }

    private boolean checks(boolean rotation) {
        if (!mc.thePlayer.onGround || !Utils.canLegitWork())
            return false;

        if (!Utils.holdingBlock() || (!rotation && !Utils.isMoving()))
            return false;

        return Utils.overAir(1);
    }

    private void startRotation(float yaw, float pitch) {
        float sens = Smok.inst.rotManager.getSensitivity();

        yaw = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationYaw, yaw, 360);
        pitch = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationPitch, pitch, 90);

        yaw = Math.round(yaw / sens) * sens;
        pitch = Math.round(pitch / sens) * sens;

        //if (Smok.inst.debugMode) {
        //    if (!changeItem.isEnabled()) {
                mc.thePlayer.rotationYaw = yaw;
                mc.thePlayer.rotationPitch = pitch;
        //    }
        //}

        Smok.inst.rotManager.setRotations(yaw, pitch);
        Smok.inst.rotManager.setRotating(true);
    }

    private void resetRotation() {
        Smok.inst.rotManager.setRotating(false);
        this.blockData = null;
    }

}