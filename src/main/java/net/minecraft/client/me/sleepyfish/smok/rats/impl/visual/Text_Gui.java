package net.minecraft.client.me.sleepyfish.smok.rats.impl.visual;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventRender2D;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FastEditUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;

// Class from SMok Client by SleepyFish
public class Text_Gui extends Rat {

    BoolSetting randomColor;
    BoolSetting visualModules;

    public Text_Gui() {
        super(Var.arraylist_name, Category.Visual, Var.arraylist_desc);
    }

    @Override
    public void setup() {
        this.addSetting(randomColor = new BoolSetting(Var.arraylist_randomColor, false));
        this.addSetting(visualModules = new BoolSetting(Var.arraylist_visualModules, true));
    }

    @SmokEvent
    public void onRender(EventRender2D e) {
        int count = 0;
        int off = 0;

        if (ClientUtils.inClickGui())
            off = 10;

        for  (Rat rat : Smok.inst.ratManager.getBigRats()) {
            if (!visualModules.isToggled())
                if (rat.getCategory() == Category.Visual)
                    continue;

            int color = (randomColor.isToggled()) ? ColorUtils.getClientColor(rat.hashCode() * 100).getRGB() : ColorUtils.getClientColor(1).getRGB();

            if (rat.isToggled()) {
                FontUtils.rBold18.drawStringWithShadow(rat.getName(), FastEditUtils.textHud_X, FastEditUtils.textHud_Y + off + (count * FontUtils.rBold18.getHeight() + 8F), color);
                count++;
            }
        }

        ColorUtils.clearColor();
    }

}