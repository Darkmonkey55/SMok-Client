package net.minecraft.client.club.maxstats.weaveyoutube.mixin;

import net.minecraft.client.club.maxstats.weaveyoutube.event.RenderLivingEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.weavemc.loader.api.event.EventBus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Class from SMok Client by SleepyFish
@Mixin(RendererLivingEntity.class)
public class MixinRendererLivingEntity {

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        EventBus.callEvent(new RenderLivingEvent((RendererLivingEntity<EntityLivingBase>) (Object) this, entity, x, y, z, partialTicks));
    }

}