package net.minecraft.client.me.sleepyfish.smok.rats.impl.useless;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;

// Class from SMok Client by SleepyFish
public class Head_Hitter extends Rat {

    SlideSetting delay;

    BoolSetting movingOnly;

    public Head_Hitter() {
        super(Var.head_hitter_name, Category.Useless, Var.head_hitter_desc);
    }

    @Override
    public void setup() {
        this.addSetting(movingOnly = new BoolSetting(Var.head_hitter_move_only, true));
        this.addSetting(delay = new SlideSetting(Var.head_hitter_delay, 100, 50, 500, 5));
    }

    @SmokEvent
    public void tick(EventTick e) {
        if (Utils.canLegitWork()) {

            if (movingOnly.isToggled())
                if (!Utils.isMoving())
                    return;

            if (mc.thePlayer.onGround && !Utils.overAir(-2))
                if (Timer.hasTimeElapsed(delay.getValueToLong(), true))
                    mc.thePlayer.jump();
        }
    }

}