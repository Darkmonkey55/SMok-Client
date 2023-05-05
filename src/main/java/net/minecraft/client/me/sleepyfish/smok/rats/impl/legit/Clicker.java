package net.minecraft.client.me.sleepyfish.smok.rats.impl.legit;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.MouseUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SpaceSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.settings.KeyBinding;

// Class from SMok Client by SleepyFish
public class Clicker extends Rat {

    BoolSetting right;
    SlideSetting rightCpsMin;
    SlideSetting rightCpsMax;
    BoolSetting onlyWhileLooking;
    BoolSetting onlyWhileHoldingBlock;

    BoolSetting left;
    SlideSetting leftCpsMax;
    SlideSetting leftCpsMin;
    BoolSetting onlyWhileTargeting;
    BoolSetting breakBlocks;
    BoolSetting onlyWeapon;
    BoolSetting hitselect;

    public Clicker() {
        super(Var.clicker_name, Category.Legit, Var.clicker_desc);
    }

    @Override
    public void setup() {
        this.addSetting(right = new BoolSetting(Var.clicker_right, true));
        this.addSetting(rightCpsMin = new SlideSetting(Var.clicker_rightCpsMin, 9, 1, 24, 1));
        this.addSetting(rightCpsMax = new SlideSetting(Var.clicker_rightCpsMax, 12, 2, 25, 1));
        this.addSetting(onlyWhileLooking = new BoolSetting(Var.clicker_onlyWhileLooking, true));
        this.addSetting(onlyWhileHoldingBlock = new BoolSetting(Var.clicker_onlyWhileHoldingBlock, true));
        this.addSetting(new SpaceSetting());
        this.addSetting(left = new BoolSetting(Var.clicker_left, true));
        this.addSetting(leftCpsMin = new SlideSetting(Var.clicker_leftCpsMin, 9, 1, 24, 1));
        this.addSetting(leftCpsMax = new SlideSetting(Var.clicker_leftCpsMax, 12, 2, 25, 1));
        this.addSetting(onlyWhileTargeting = new BoolSetting(Var.clicker_onlyWhileTargeting, false));
        this.addSetting(breakBlocks = new BoolSetting(Var.clicker_breakBlocks, true));
        this.addSetting(onlyWeapon = new BoolSetting(Var.clicker_onlyWeapon, true));
        this.addSetting(hitselect = new BoolSetting(Var.clicker_hitselect, false));
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (right.isToggled()) {
            if (MouseUtils.isButtonDown(MouseUtils.MOUSE_RIGHT)) {

                if (onlyWhileLooking.isToggled())
                    if (mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
                        return;

                if (onlyWhileHoldingBlock.isToggled())
                    if (!Utils.holdingBlock())
                        return;

                if (onlyWeapon.isToggled())
                    if (!Utils.holdingBlock())
                        return;

                if (Timer.cpsTimer(rightCpsMin.getValueToInt(), rightCpsMax.getValueToInt())) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
                }
            }
        }

        if (left.isToggled()) {
            if (MouseUtils.isButtonDown(MouseUtils.MOUSE_LEFT)) {

                if (onlyWhileTargeting.isToggled())
                    if (mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)
                        return;

                if (onlyWeapon.isToggled())
                    if (!Utils.holdingWeapon())
                        return;

                if (breakBlocks.isToggled()) {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos();

                    if (blockPos != null) {
                        Block block = Utils.getBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                        if (block != Blocks.air && block != Blocks.lava && block != Blocks.water && block != Blocks.flowing_lava && block != Blocks.flowing_water)
                            return;
                    }
                }

                if (hitselect.isToggled()) {
                    if (!Timer.hasTimeElapsed(450L, true))
                        return;
                } else
                    if (Timer.cpsTimer(leftCpsMin.getValueToInt(), leftCpsMax.getValueToInt()))
                        return;

                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
            }
        }
    }

}