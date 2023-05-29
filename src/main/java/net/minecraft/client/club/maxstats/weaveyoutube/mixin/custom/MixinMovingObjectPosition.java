package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MovingObjectPosition.class)
public class MixinMovingObjectPosition {

    @Shadow private BlockPos blockPos;

    @Inject(method = "getBlockPos", at = @At("HEAD"), cancellable = true)
    public void getBlockPos(CallbackInfoReturnable<BlockPos> cir) {
        if (Smok.inst.rotManager.isRotating()) {
            if (Smok.inst.rotManager.allowRaytrace) {
                if (Smok.inst.rotManager.raytracePos != null) {
                    cir.setReturnValue(Smok.inst.rotManager.raytracePos);
                }
            }
        }
    }

}