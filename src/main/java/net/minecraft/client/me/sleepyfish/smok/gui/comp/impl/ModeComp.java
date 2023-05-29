package net.minecraft.client.me.sleepyfish.smok.gui.comp.impl;

import net.minecraft.client.me.sleepyfish.smok.gui.comp.IComp;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FastEditUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class ModeComp implements IComp {

    private final ModuleComp module;
    private final ModeSetting<Enum<?>> mode;
    private int x, y, o;

    public ModeComp(ModeSetting<Enum<?>> desc, ModuleComp b, int o) {
        this.mode = desc;
        this.module = b;
        this.x = b.getCategory().getX() + b.getCategory().getWidth();
        this.y = b.getCategory().getY() + b.getOff();
        this.o = o;
    }

    public void setComponentStartAt(int n) {
        this.o = n;
    }

    public int getHeight() {
        return 0;
    }

    public int getY() {
        return y;
    }


    public void mouseReleased(int x, int y, int m) {
    }

    public void keyTyped(char chara, int key) {
    }

    public void draw() {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);

        int modeWidth = (int) (FontUtils.r24.getStringWidth(this.mode.getSettingName() + ": ") * 0.5);
        FontUtils.r24.drawString(this.mode.getSettingName() + ": ", (float) ((this.module.getCategory().getX() + 4) * 2), (float) ((this.module.getCategory().getY() + this.o + 4) * 2), ColorUtils.getFontColor(1).darker().darker());
        FontUtils.r24.drawString(String.valueOf(this.mode.getMode()), (float) ((this.module.getCategory().getX() + modeWidth + 4) * 2), (float) ((this.module.getCategory().getY() + this.o + 4) * 2), ColorUtils.getFontColor(1).darker());

        GL11.glPopMatrix();
    }

    public void update(int x, int y) {
        this.y = this.module.getCategory().getY() + this.o;
        this.x = this.module.getCategory().getX();
    }

    public void mouseDown(int x, int y, int b) {
        if (!ClientUtils.inClickGui())
            return;

        if (isHoveringOverMode(x, y)) {
            if (b == 1)
                this.mode.nextMode();

            if (b == 0)
                this.mode.prevMode();
        }
    }

    private boolean isHoveringOverMode(int x, int y) {
        if (!this.module.isExpanded() || !ClientUtils.inClickGui())
            return false;

        return x > this.x && x < this.x + this.module.getCategory().getWidth() && y > this.y && y < this.y + FastEditUtils.settingGap;
    }

}