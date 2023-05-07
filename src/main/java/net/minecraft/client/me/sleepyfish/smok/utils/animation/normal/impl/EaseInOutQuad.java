package net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.impl;

import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Animation;

public class EaseInOutQuad extends Animation {

    public EaseInOutQuad(int ms, double endPoint) {
        super(ms, endPoint);
    }

    protected double getEquation(double x1) {
        double x = x1 / duration;
        return x < 0.5 ? 2 * Math.pow(x, 2) : 1 - Math.pow(-2 * x + 2, 2) / 2;
    }

}