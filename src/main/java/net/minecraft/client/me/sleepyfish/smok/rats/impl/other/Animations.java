package net.minecraft.client.me.sleepyfish.smok.rats.impl.other;

import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;

// Class from SMok Client by SleepyFish
public class Animations extends Rat {

    public static SlideSetting value;
    public static BoolSetting removeHand;

    public Animations() {
        super(Var.animations_name, Category.Other, Var.animations_desc);
    }

    @Override
    public void setup() {
        this.addSetting(value = new SlideSetting(Var.animations_blockingAnimation, 0, -2, 2, 0.05));
        this.addSetting(removeHand = new BoolSetting(Var.animations_removeHand, false));
    }

}