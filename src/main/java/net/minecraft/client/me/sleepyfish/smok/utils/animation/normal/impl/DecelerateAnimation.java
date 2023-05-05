package net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.impl;

import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Animation;

public class DecelerateAnimation extends Animation {

    public DecelerateAnimation(int ms, double endPoint) {
        super(ms, endPoint);
    }

    protected double getEquation(double x) {
        double x1 = x / duration;
        return 1 - ((x1 - 1) * (x1 - 1));
    }

}