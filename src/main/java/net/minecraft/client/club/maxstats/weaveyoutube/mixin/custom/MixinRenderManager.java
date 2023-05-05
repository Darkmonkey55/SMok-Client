package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Chams;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
@Mixin(RenderManager.class)
public abstract class MixinRenderManager {

    @Inject(method = "doRenderEntity", at = @At("HEAD"))
    public void doRenderEntity(Entity target, double x, double y, double z, float entityYaw, float partialTicks, boolean hideDebugBox, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof EntityPlayer) {
            if (!Utils.inInv()) {
                if (Smok.inst.ratManager.getBigRatByClass(Chams.class).isToggled()) {
                    GL11.glEnable(32823);
                    GL11.glPolygonOffset(0F, -1100000.0F);
                } else {
                    GL11.glDisable(32823);
                    GL11.glPolygonOffset(0F, 1100000.0F);
                }
            }
        }
    }

}