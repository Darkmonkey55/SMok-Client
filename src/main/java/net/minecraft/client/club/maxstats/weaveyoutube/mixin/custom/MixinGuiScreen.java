package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.No_Background;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

// Class from SMok Client by SleepyFish
@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Shadow protected Minecraft mc;

    @Shadow public int width;

    @Shadow public int height;

    @Shadow public abstract void drawBackground(int tint);

    /**
     * @author sleepy
     * @reason no background
     */
    @Overwrite
    public void drawWorldBackground(int tint) {
        if (this.mc.theWorld != null) {
            int smok = (Smok.inst.ratManager.getBigRatByClass(No_Background.class).isEnabled()) ? 0 : -1072689136;
            Gui.drawRect(0, 0, this.width, this.height, smok);
        } else
            this.drawBackground(tint);
    }

}