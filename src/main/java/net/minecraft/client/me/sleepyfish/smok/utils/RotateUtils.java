package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

// Class from SMok Client by SleepyFish
public class RotateUtils {

    public float yaw;
    public float pitch;
    private boolean ballsRotatin;

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setRotating(boolean rotating) {
        this.ballsRotatin = rotating;
    }

    public boolean isRotating() {
        return ballsRotatin;
    }

    public void rayTrace(Entity target) {
        // Real raytrace
        if (isRotating()) {
            Smok.inst.mc.pointedEntity = target;
            Smok.inst.mc.objectMouseOver.entityHit = target;
        }
    }

    public void setRotations(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    // Thank you Smellon
    public float smoothRotation(float from, float to, float speed) {
        float angle = MathHelper.wrapAngleTo180_float(to - from);

        if (angle > speed)
            angle = speed;

        if (angle < -speed)
            angle = -speed;

        return from + angle;
    }

    // Thank you Smellon
    public float getSensitivity() {
        float sensitivity = Smok.inst.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return (sensitivity * sensitivity * sensitivity * 8.0F) * 0.15F;
    }

}