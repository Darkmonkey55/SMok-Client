package net.minecraft.client.me.sleepyfish.smok.gui.guis;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.gui.comp.IComp;
import net.minecraft.client.me.sleepyfish.smok.utils.MouseUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FastEditUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.GlUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Text_Gui;
import net.minecraft.client.me.sleepyfish.smok.gui.comp.impl.CategoryComp;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Animation;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Direction;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.impl.EaseBackIn;
import net.minecraft.client.gui.GuiScreen;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class ClickGui extends GuiScreen {

    private final ArrayList<CategoryComp> categoryComps;
    private ScheduledFuture<?> future;

    public boolean close;
    public Animation introAnimation;

    public ClickGui() {
        this.categoryComps = new ArrayList<>();
        int widthOff = 175;

        Rat.Category[] values;
        int catAmount = (values = Rat.Category.values()).length;

        for (int cat = 0; cat < catAmount; ++cat) {
            Rat.Category modCategory = values[cat];
            CategoryComp current = new CategoryComp(modCategory);
            current.setX(widthOff);
            current.setY(20);
            current.setOpened(true);
            categoryComps.add(current);
            widthOff += 90;
        }
    }

    @Override
    public void initGui() {
        ClientUtils.updateClientVersion();

        introAnimation = new EaseBackIn(450, 1, 2);
        close = false;
    }

    @Override
    public void drawScreen(int x, int y, float p) {

        if (close) {
            introAnimation.setDirection(Direction.BACKWARDS);

            if (introAnimation.isDone(Direction.BACKWARDS))
                mc.displayGuiScreen(null);
        }

        RenderUtils.drawAuthor(this.width, this.height);
        RenderUtils.drawVersion(this.width, this.height);

        if (Smok.inst.ratManager.getBigRatByClass(Text_Gui.class).isToggled()) {
            RoundedUtils.drawRound(FastEditUtils.textHud_X - 5, FastEditUtils.textHud_Y - 3, 80, 13, 2, ColorUtils.getBackgroundColor(5).darker());
            FontUtils.i20.drawString(":", FastEditUtils.textHud_X - 1, FastEditUtils.textHud_Y + 1.3, ColorUtils.getFontColor(1).getRGB());
            FontUtils.r20.drawStringWithClientColor(Rat.Var.arraylist_name, FastEditUtils.textHud_X + 12, FastEditUtils.textHud_Y, true);
        }

        for (CategoryComp cat : categoryComps) {
            GlUtils.startScale(cat.getX() + (cat.getWidth() / 2F), cat.getY() + cat.getHeight() + 60F, (float) introAnimation.getValue());

            cat.drawCategory();
            cat.up(x, y);

            for (IComp c : cat.getRats())
                c.update(x, y);

            GL11.glPopMatrix();
        }
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        Iterator<CategoryComp> iter = categoryComps.iterator();

        // Version button
        if (MouseUtils.isInside(x, y, this.width - 62, this.height - 33, 63, 27) && b == MouseUtils.MOUSE_LEFT)
            ClientUtils.browse(Rat.Var.client_github);

        // Author button
        if (MouseUtils.isInside(x, y, this.width / 2F - ((float) FontUtils.r20.getStringWidth(Rat.Var.client_author) / 2F + 2F), this.height - 68F, (float) FontUtils.r20.getStringWidth(Rat.Var.client_author) + 4F, 20) && b == MouseUtils.MOUSE_LEFT)
            ClientUtils.browse(Rat.Var.client_discord);

        while (true) {
            CategoryComp cat;
            do {
                do {
                    if (!iter.hasNext())
                        return;

                    cat = iter.next();

                    if (cat.insideArea(x, y) && !cat.isHoveringOverCategoryCollapseIcon(x, y) && !cat.mousePressed(x, y) && b == 0) {
                        cat.mousePressed(true);
                        cat.xx = x - cat.getX();
                        cat.yy = y - cat.getY();
                    }

                    if (cat.insideArea(x, y) && b == 1)
                        cat.setOpened(!cat.isOpened());

                } while (!cat.isOpened());
            } while (cat.getRats().isEmpty());

            for (IComp c : cat.getRats())
                c.mouseDown(x, y, b);
        }
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        if (button == 0) {
            Iterator<CategoryComp> iter = categoryComps.iterator();

            CategoryComp cat;
            while (iter.hasNext()) {
                cat = iter.next();
                cat.mousePressed(false);
            }

            iter = categoryComps.iterator();

            while (true) {
                do {
                    do {
                        if (!iter.hasNext())
                            return;

                        cat = iter.next();
                    } while (!cat.isOpened());
                } while (cat.getRats().isEmpty());

                for (IComp c : cat.getRats())
                    c.mouseReleased(x, y, button);
            }
        }
    }

    @Override
    public void keyTyped(char t, int k) {
        if (k == 1)
            close = true;

        else {
            Iterator<CategoryComp> iter = categoryComps.iterator();

            while (true) {
                CategoryComp cat;
                do {
                    do {
                        if (!iter.hasNext())
                            return;

                        cat = iter.next();
                    } while (!cat.isOpened());
                } while (cat.getRats().isEmpty());

                for (IComp c : cat.getRats())
                    c.keyTyped(t, k);
            }
        }
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        float sc = 0 + Mouse.getEventDWheel() * 0.2F;
        for (CategoryComp category : SMkO())
            category.scroll(sc);
    }

    @Override
    public void onGuiClosed() {
        if (this.future != null) {
            this.future.cancel(true);
            this.future = null;
        }

        for (CategoryComp cat : categoryComps)
            cat.mousePressed(false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public ArrayList<CategoryComp> SMkO() {
        return categoryComps;
    }

}