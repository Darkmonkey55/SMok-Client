package net.minecraft.client.me.sleepyfish.smok.rats.impl.legit;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.MouseUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.client.settings.KeyBinding;

// Class from SMok Client by SleepyFish
public class Eagle extends Rat {

    BoolSetting blocksOnly;
    BoolSetting backwardsOnly;
    BoolSetting fastMode;
    BoolSetting fixForward;
    BoolSetting onlyFixWithoutBlocks;
    BoolSetting blockChecks;

    SlideSetting fixWalkingTime;

    public Eagle() {
        super(Var.eagle_name, Category.Legit, Var.eagle_desc);
    }

    @Override
    public void setup() {
        this.addSetting(blocksOnly = new BoolSetting(Var.eagle_blocksOnly, true));
        this.addSetting(backwardsOnly = new BoolSetting(Var.eagle_backwardsOnly, true));
        this.addSetting(fastMode = new BoolSetting(Var.eagle_fastMode, false));
        this.addSetting(fixForward = new BoolSetting(Var.eagle_fixForward, false));
        this.addSetting(onlyFixWithoutBlocks = new BoolSetting(Var.eagle_onlyFixWithoutBlocks, true));
        this.addSetting(fixWalkingTime = new SlideSetting(Var.eagle_fixWalkingTime, 500, 50, 1000, 5));
        this.addSetting(blockChecks = new BoolSetting(Var.eagle_blockChecks, true));
    }

    @SmokEvent
    public void tick(EventTick e) {
        if (Utils.canLegitWork()) {

            if (blocksOnly.isToggled())
                if (!Utils.holdingBlock())
                    return;

            if (backwardsOnly.isToggled())
                if (!Utils.isMovingBackwards())
                    return;

            if (fixForward.isToggled())
                if (mc.thePlayer.isSneaking() && Utils.isMovingForward()) {
                    if (onlyFixWithoutBlocks.isToggled())
                        if (Utils.holdingBlock())
                            return;

                    if (Timer.hasTimeElapsed(fixWalkingTime.getValueToLong(), true))
                        setShift(false);
                }

            if (fastMode.isToggled()) {
                if (Utils.overAir(1) && !mc.thePlayer.isSneaking())
                    setShift(true);

                if (MouseUtils.isButtonDown(MouseUtils.MOUSE_RIGHT) && Utils.overAirCustom(1, 1, 0) && mc.thePlayer.isSneaking())
                    setShift(false);
            } else {
                if (Utils.overAir(1) && mc.thePlayer.onGround)
                    setShift(true);
                else if (!Utils.overAir(1) && mc.thePlayer.isSneaking() && mc.thePlayer.onGround)
                    setShift(false);
            }
        }
    }

    /**
     * @author SleepyFish
     * @Function: make the player sneak or un-sneak
     */
    void setShift(boolean shift) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), shift);
    }

}