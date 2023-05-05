package net.minecraft.client.me.sleepyfish.smok.rats.impl.useless;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;

// Class from SMok Client by SleepyFish
public class Parkour extends Rat {

    BoolSetting movingOnly;

    public Parkour() {
        super(Var.parkour_name, Category.Useless, Var.parkour_desc);
    }

    @Override
    public void setup() {
        this.addSetting(movingOnly = new BoolSetting(Var.parkour_move_only, true));
    }

    @SmokEvent
    public void tick(EventTick e) {
        if (Utils.canLegitWork()) {

            if (movingOnly.isToggled())
                if (!Utils.isMoving())
                    return;

            if (mc.thePlayer.onGround && Utils.overAir(1))
                mc.thePlayer.jump();
        }
    }

}