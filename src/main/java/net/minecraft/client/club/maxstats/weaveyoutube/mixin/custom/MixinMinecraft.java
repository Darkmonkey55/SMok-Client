package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.club.maxstats.weaveyoutube.event.EventRenderTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.*;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.other.FPS_Boost;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.other.Hit_Delay;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.NotificationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
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

    @Shadow private static int debugFPS;

    @Shadow private Session session;

    @Shadow private int leftClickCounter;

    /**
     * @author sleepyfish
     * @reason change session
     */
    @Overwrite
    public Session getSession() {
        return this.session;
    }

    @Inject(method = "runTick", at = @At("TAIL"))
    public void runTick(CallbackInfo ci) {
        EventTick event = new EventTick();
        event.call();

        if (Smok.inst.ratManager.getBigRatByClass(FPS_Boost.class).isEnabled())
            debugFPS = MathUtils.randomInt(debugFPS+60, debugFPS+210);

        if (Smok.inst.ratManager.getBigRatByClass(Hit_Delay.class).isEnabled())
            if (MouseUtils.isButtonDown(MouseUtils.MOUSE_LEFT))
                leftClickCounter = 0;
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