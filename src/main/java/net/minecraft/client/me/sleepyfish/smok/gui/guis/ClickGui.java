package net.minecraft.client.me.sleepyfish.smok.gui.guis;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.gui.comp.IComp;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Gui;
import net.minecraft.client.me.sleepyfish.smok.utils.MouseUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.GlUtils;
import net.minecraft.client.me.sleepyfish.smok.gui.comp.impl.SliderComp;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Text_Gui;
import net.minecraft.client.me.sleepyfish.smok.gui.comp.impl.CategoryComp;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Target_Hud;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Animation;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Direction;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.impl.EaseBackIn;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import java.util.Objects;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

// Class from SMok Client by SleepyFish
public class ClickGui extends GuiScreen {

    private final ArrayList<CategoryComp> categoryComps;
    private ScheduledFuture<?> future;

    private boolean textHudMoving;
    private boolean targetHudMoving;

    public boolean close;
    public Animation introAnimation;

    public ClickGui() {
        this.categoryComps = new ArrayList<>();
        this.textHudMoving = false;
        this.targetHudMoving = false;

        int widthOff = 175;

        Rat.Category[] values;
        int catAmount = (values = Rat.Category.values()).length;

        for (int cat = 0; cat < catAmount; cat++) {
            Rat.Category modCategory = values[cat];
            CategoryComp current = new CategoryComp(modCategory);
            current.setX(widthOff);
            current.setY(20);
            current.setOpened(true);
            categoryComps.add(current);
            widthOff += 92;
        }
    }

    @Override
    public void initGui() {
        ClientUtils.updateClientVersion();

        introAnimation = new EaseBackIn(450, 1, 0.5F);
        close = false;
    }

    @Override
    public void drawScreen(int x, int y, float p) {
        MouseUtils.mouseX = x;
        MouseUtils.mouseY = y;

        Gui.setColorMode();

        if (Smok.inst.ratManager.getBigRatByClass(Text_Gui.class).isEnabled()) {
            if (this.textHudMoving) {
                Text_Gui.movementOnTop(MouseUtils.mouseX, MouseUtils.mouseY);
            }
        }

        if (Smok.inst.ratManager.getBigRatByClass(Target_Hud.class).isEnabled()) {
            if (this.targetHudMoving) {
                Target_Hud.movementOnTop(MouseUtils.mouseX, MouseUtils.mouseY);
            }
        }

        if (close) {
            this.textHudMoving = false;
            this.targetHudMoving = false;

            introAnimation.setDirection(Direction.BACKWARDS);

            for (IComp comp : Objects.requireNonNull((categoryComps.iterator().hasNext()) ? categoryComps.iterator().next().getRats() : null)) {
                if (comp != null) {
                    if (comp instanceof SliderComp)
                        ((SliderComp) comp).sliding = false;
                }
            }

            if (introAnimation.isDone(Direction.BACKWARDS))
                mc.displayGuiScreen(null);
        }

        GlUtils.startScale(this.width / 2F - ((float) FontUtils.r20.getStringWidth(Rat.Var.client_author) / 2F + 2F), this.height - 68F, (float) FontUtils.r20.getStringWidth(Rat.Var.client_author) + 4F, 20, (float) introAnimation.getValue());
        RenderUtils.drawAuthor(this.width, this.height);
        GlUtils.stopScale();

        GlUtils.startScale(this.width - 62, this.height - 33, 63, 27, (float) introAnimation.getValue());
        RenderUtils.drawVersion(this.width, this.height);
        GlUtils.stopScale();

        for (CategoryComp cat : categoryComps) {
            GlUtils.startScale(cat.getX() + (cat.getWidth() / 2F), cat.getY() + (cat.getCategoryHeight() / 2F), (float) introAnimation.getValue());

            cat.drawCategory();
            cat.up(x, y);

            for (IComp c : cat.getRats())
                c.update(x, y);

            GlUtils.stopScale();
        }
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        Iterator<CategoryComp> iter = categoryComps.iterator();

        if (Smok.inst.ratManager.getBigRatByClass(Text_Gui.class).isEnabled()) {
            if (MouseUtils.isInside(x, y, Text_Gui.textHud_X - 10, Text_Gui.textHud_Y - 10, 85, 25) && b == MouseUtils.MOUSE_LEFT) {
                this.textHudMoving = true;

                Text_Gui.textHud_X2 = x - Text_Gui.textHud_X;
                Text_Gui.textHud_Y2 = y - Text_Gui.textHud_Y;
            }
        }

        if (Smok.inst.ratManager.getBigRatByClass(Target_Hud.class).isEnabled()) {
            if (MouseUtils.isInside(x, y, Target_Hud.targetHud_X, Target_Hud.targetHud_Y, 140, 48) && b == MouseUtils.MOUSE_LEFT) {
                this.targetHudMoving = true;

                Target_Hud.targetHud_X2 = x - Target_Hud.targetHud_X;
                Target_Hud.targetHud_Y2 = y - Target_Hud.targetHud_Y;
            }
        }

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
        if (button == MouseUtils.MOUSE_LEFT) {
            if (Smok.inst.ratManager.getBigRatByClass(Text_Gui.class).isEnabled()) {
                if (this.textHudMoving)
                    this.textHudMoving = false;
            }

            if (Smok.inst.ratManager.getBigRatByClass(Target_Hud.class).isEnabled()) {
                if (this.targetHudMoving)
                    this.targetHudMoving = false;
            }

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
        Iterator<CategoryComp> iter = categoryComps.iterator();

        if (k == 1) {
            close = true;
        } else {
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

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();

        float sc = 0 + Mouse.getEventDWheel() * 0.2F;
        for (CategoryComp category : getCategories())
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

    public boolean isTargetHudMoving() {
        return this.targetHudMoving;
    }

    public boolean isTextHudMoving() {
        return this.textHudMoving;
    }

    public ArrayList<CategoryComp> getCategories() {
        return categoryComps;
    }

}