package net.minecraft.client.me.sleepyfish.smok.utils.animation.normal;

// Class from SMok Client by SleepyFish
public enum Direction {

    FORWARDS,
    BACKWARDS;

    public Direction opposite() {
        if (this == Direction.FORWARDS)
            return Direction.BACKWARDS;
        else
            return Direction.FORWARDS;
    }

}