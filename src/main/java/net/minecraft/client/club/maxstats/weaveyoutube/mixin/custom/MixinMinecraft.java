package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.club.maxstats.weaveyoutube.event.EventRenderTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.other.FPS_Boost;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.other.Hit_Delay;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.NotificationManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.input.Keyboard;

// Class from SMok Client by SleepyFish
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow private int rightClickDelayTimer;

    @Shadow private static int debugFPS;

    @Inject(method = "runTick", at = @At("TAIL"))
    public void runTick(CallbackInfo ci) {
        EventTick event = new EventTick();
        event.call();

        if (Smok.inst.ratManager.getBigRatByClass(FPS_Boost.class).isToggled())
            debugFPS = MathUtils.randomInt(debugFPS+60, debugFPS+210);

        if (Smok.inst.ratManager.getBigRatByClass(Hit_Delay.class).isToggled())
            rightClickDelayTimer = 0;

        int var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();

        if (Timer.hasTimeElapsed(105L, true))
            if (Keyboard.getEventKeyState() && !Keyboard.next())
                if (Utils.canLegitWork() && !Utils.inGui()) {
                    if (var1 == Smok.inst.getBind(1))
                        Smok.inst.mc.displayGuiScreen(Smok.inst.guiManager.getClickGui());

                    for (Rat m : Smok.inst.ratManager.getBigRats())
                        if (var1 == m.getKeycode())
                            m.toggle();
                }
    }

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/achievement/GuiAchievement;updateAchievementWindow()V", shift = At.Shift.AFTER))
    public void renderTick(CallbackInfo ci) {
        NotificationManager.render();

        EventRenderTick event = new EventRenderTick();
        event.call();
    }

    /**
     * @author sleepyfish
     * @reason fps booster (100% real) working code
     */
    @Overwrite
    public static int getDebugFPS() {
        return debugFPS;
    }

}