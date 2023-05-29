package net.minecraft.client.me.sleepyfish.smok.rats.impl.visual;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventRender2D;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SpaceSetting;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import java.awt.Color;

// Class from SMok Client by SleepyFish
public class Text_Gui extends Rat {

    public static int textHud_X = 10;
    public static int textHud_Y = 10;

    public static int textHud_X2 = 10;
    public static int textHud_Y2 = 10;

    BoolSetting visualModules;
    BoolSetting drawLogo;

    ModeSetting<Enum<?>> colorMode;
    ModeSetting<Enum<?>> brightness;

    public Text_Gui() {
        super(Var.arraylist_name, Category.Visual, Var.arraylist_desc);
    }

    @Override
    public void setup() {
        this.addSetting(visualModules = new BoolSetting(Var.arraylist_visualModules, true));
        this.addSetting(drawLogo = new BoolSetting(Var.arraylist_drawIcon, false));
        this.addSetting(new SpaceSetting());
        this.addSetting(colorMode = new ModeSetting<>(Var.arraylist_colorMode, colorModes.Fade));
        this.addSetting(brightness = new ModeSetting<>(Var.arraylist_brightness, brightnessModes.Normal));
    }

    @SmokEvent
    public void onRender(EventRender2D e) {
        int count = 0;
        int off = 0;

        if (ClientUtils.inClickGui()) {
            off = 10;

            RoundedUtils.drawRoundOutline(textHud_X - 5, textHud_Y - 3, 82, 12, 1F, 2.62F, ColorUtils.getBackgroundColor(5), ColorUtils.getBackgroundColor(5).darker());
            FontUtils.i20.drawString(":", textHud_X - 3, textHud_Y + 1.3, ColorUtils.getFontColor(1));
            FontUtils.r20.drawStringWithClientColor(Rat.Var.arraylist_name, textHud_X + 11, textHud_Y, true);
        }

        for (Rat rat : Smok.inst.ratManager.getBigRats()) {
            if (!visualModules.isEnabled())
                if (rat.getCategory() == Category.Visual)
                    continue;

            if (drawLogo.isEnabled()) {
                if (!ClientUtils.inClickGui()) {
                    off = 3;
                    RenderUtils.drawImage("logo.png", textHud_X - 20, textHud_Y - 20, 80, 30);
                }
            }

            Color color = Color.pink.darker();

            switch (colorMode.getMode().name()) {
                case "Random": {
                    color = ColorUtils.getClientColor(rat.hashCode() * 100);
                    break;
                }

                case "Static": {
                    color = ColorUtils.getClientColor(1);
                    break;
                }

                case "Fade": {
                    color = ColorUtils.getClientColor(count * 25);
                    break;
                }
            }

            switch (brightness.getMode().name()) {
                case "Darker": {
                    color = color.darker().darker();
                    break;
                }

                case "Dark": {
                    color = color.darker();
                    break;
                }

                case "Bright": {
                    color = color.brighter();
                    break;
                }

                case "Brighter": {
                    color = color.brighter().brighter();
                    break;
                }
            }

            if (rat.isEnabled()) {
                FontUtils.rBold18.drawStringWithShadow(rat.getName(), textHud_X, textHud_Y + off + (count * FontUtils.rBold18.getHeight() + 8F), color);
                count++;
            }
        }

        ColorUtils.clearColor();
    }

    public static void movementOnTop(int x, int y) {
        if (Smok.inst.guiManager.getClickGui().isTextHudMoving() && ClientUtils.inClickGui()) {
            textHud_X = x - textHud_X2;
            textHud_Y = y - textHud_Y2;
        }
    }

    public enum colorModes {
        Static, Random, Fade;
    }

    public enum brightnessModes {
        Darker, Dark, Normal, Bright, Brighter
    }

}