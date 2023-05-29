package net.minecraft.client.me.sleepyfish.smok.utils;

// Class from SMok Client by SleepyFish
public class Timer {

    public static long lastMS = System.currentTimeMillis();

    public static boolean hasTimeElapsed(long ms, boolean reset) {
        if (ms <= 0L)
            return true;

        if (System.currentTimeMillis() - lastMS > ms) {
            if (reset)
                lastMS = System.currentTimeMillis();

            return true;
        }
        return false;
    }

    public static boolean cpsTimer(int min, int max) {
        return Timer.hasTimeElapsed(1000 / (MathUtils.randomInt(min, max)), true);
    }

    public static class Better {

        public long lastMS = System.currentTimeMillis();

        public void reset() {
            lastMS = System.currentTimeMillis();
        }
        public boolean delay(long nextDelay) {
            return System.currentTimeMillis() - lastMS >= nextDelay;
        }

        public boolean hasTimeElapsed(long time, boolean reset) {
            if (System.currentTimeMillis() - lastMS > time) {
                if (reset) reset();
                return true;
            }

            return false;
        }

        public long getTime() {
            return System.currentTimeMillis() - lastMS;
        }

        public void setTime(long time) {
            lastMS = time;
        }

    }

}