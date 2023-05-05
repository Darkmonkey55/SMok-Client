package net.minecraft.client.me.sleepyfish.smok.utils.render.notifications;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Animation;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Direction;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.impl.DecelerateAnimation;
import net.minecraft.client.gui.ScaledResolution;

// Class from SMok Client by SleepyFish
public class Notification {

    private final Timer timer = new Timer();
    private Animation animation;
    private Animation slideAnimation;

    private final Icon icon;
    private final String message;
    private final String title;

    private boolean loaded = false;

    private final double width;
    private final long delay;

    public Notification(String title, String msg, Icon icon, long delay) {
        this.title = title;
        this.message = msg;
        this.icon = icon;
        this.delay = delay;
        this.width = Math.max(FontUtils.r20.getStringWidth(msg), FontUtils.r20.getStringWidth(title) + 10) + 15;
    }

    public void show() {
        animation = new DecelerateAnimation(250, width + 13);
        slideAnimation = new DecelerateAnimation((int) delay, width + 12);
        timer.reset();
    }

    public boolean isShown() {
        if (Smok.inst.mc.thePlayer != null)
            return !animation.isDone(Direction.BACKWARDS);
        else
            return false;
    }

    public void render() {
        ScaledResolution sr = new ScaledResolution(Smok.inst.mc);

        if (Smok.inst.eveManager != null)
            if (!loaded) {
                loaded = true;
                Smok.inst.eveManager.register(this);
            }

        if (timer.delay(delay)) {
            animation.setDirection(Direction.BACKWARDS);
            slideAnimation.setDirection(Direction.BACKWARDS);
        }

        String icon = "?";
        if (this.icon == Icon.Bell)
            icon = "F";

        if (this.icon == Icon.Info)
            icon = "b";

        if (this.icon == Icon.Warning)
            icon = "A";

        if (this.icon == Icon.Check)
            icon = "I";

        if (this.icon == Icon.Refresh)
            icon = "y";

        if (this.icon == Icon.No)
            icon = "s";

        int heightOff = sr.getScaledHeight() - 40;
        if (ClientUtils.inClickGui())
            heightOff -= 30;

        RoundedUtils.drawGradientRoundLR((float) (sr.getScaledWidth() - animation.getValue()), heightOff, (float) (width + 13), 30, 2, ColorUtils.getBackgroundColor(5), ColorUtils.getBackgroundColor(5).darker());
        RoundedUtils.drawGradientRoundLR((float) (sr.getScaledWidth() - animation.getValue()), heightOff + 28, (float) (slideAnimation.getValue()), 2, 1, ColorUtils.getClientColor(1), ColorUtils.getClientColor(9696));

        FontUtils.i20.drawString(icon, (int) (sr.getScaledWidth() - animation.getValue() + 5), heightOff + 7, ColorUtils.getFontColor(2).getRGB());
        FontUtils.r20.drawString(title, sr.getScaledWidth() - animation.getValue() + 18, heightOff + 6, ColorUtils.getFontColor(2).getRGB());
        FontUtils.r20.drawString(message, sr.getScaledWidth() - animation.getValue() + 6, heightOff + 16, ColorUtils.getFontColor(2).getRGB());
    }

    /*
    @AmariEvent
    public void r(EventRenderShadow e) {
        ScaledResolution sr = new ScaledResolution(Amari.instance.mc);
        if (animation != null)
            RoundedUtils.drawRound((float) (sr.getScaledWidth() - animation.getValue()), heightOff, (float) (width + 5), 29, 6, new Color(0, 0, 0));
    }
     */

    public enum Icon {
        Info, Bell, Warning, Check, Refresh, No;
    }

    public static class Timer {

        private long lastMs;

        public Timer() {
            this.lastMs = 0L;
        }

        public boolean delay(long nextDelay) {
            return System.currentTimeMillis() - lastMs >= nextDelay;
        }

        public void reset() {
            this.lastMs = System.currentTimeMillis();
        }

    }

}