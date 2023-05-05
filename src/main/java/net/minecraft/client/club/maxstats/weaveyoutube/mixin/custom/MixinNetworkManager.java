package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventSendPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Class from SMok Client by SleepyFish
@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onProcessPacket(Packet<?> packet, CallbackInfo ci) {
        EventSendPacket event = new EventSendPacket(packet);
        event.call();

        if (event.isCancelled())
            ci.cancel();
    }

    /*
    @Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
    private void channelRead0(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo ci) {
        EventReceivingPacket event = new EventReceivingPacket(packet);
        event.call();

        if (event.isCancelled())
            ci.cancel();
    }
     */


}