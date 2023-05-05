package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventRender2D;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Class from SMok Client by SleepyFish
@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    @Inject(method = "renderTooltip", at = @At("HEAD"))
    public void renderOverlay(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        EventRender2D event = new EventRender2D(sr, partialTicks);
        event.call();
    }

}