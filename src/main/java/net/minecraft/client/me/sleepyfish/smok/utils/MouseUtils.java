package net.minecraft.client.me.sleepyfish.smok.utils;

import org.lwjgl.input.Mouse;

// Class from SMok Client by SleepyFish
public class MouseUtils {

    public static int mouseX;
    public static int mouseY;

    public final static int MOUSE_MIDDLE = 2;
    public final static int MOUSE_RIGHT = 1;
    public final static int MOUSE_LEFT = 0;

    public enum Scroll {
        UP, DOWN;
    }

    public static Scroll scroll() {
        int mouse = Mouse.getDWheel();

        if (mouse > 0)
            return Scroll.UP;
        else if (mouse < 0)
            return Scroll.DOWN;
        else
            return null;
    }

    public static boolean isInside(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (mouseX > x && mouseX < (x + width)) && (mouseY > y && mouseY < (y + height));
    }

    public static boolean isButtonDown(int key) {
        return Mouse.isButtonDown(key);
    }

    public static void setMousePos(int x, int y) {
        Mouse.setCursorPosition(x, y);
    }

    public static int getX() {
        return Mouse.getX();
    }

    public static int getY() {
        return Mouse.getY();
    }

}