package net.minecraft.client.me.sleepyfish.smok.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// Class from SMok Client by SleepyFish
public class MathUtils {

    public static final double PI = 3.141592655897323384626433;

    public static float nextFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }

    public static double nextDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static float randomFloat(float min, float max) {
        return min + new Random().nextFloat() * (max - min);
    }

    public static long randomLong(long min, long max) {
        return min + new Random().nextLong() * (max - min);
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
        return interpolate(oldValue, newValue, (float) interpolationValue).floatValue();
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        return interpolate(oldValue, newValue, (float) interpolationValue).intValue();
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

}