package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventSendPacketPost;
import net.minecraft.client.club.maxstats.weaveyoutube.event.EventSendPacketPre;
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
    private void onProcessPacketHead(Packet<?> packet, CallbackInfo ci) {
        EventSendPacketPre event = new EventSendPacketPre(packet);
        event.call();

        if (event.isCancelled())
            ci.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("TAIL"), cancellable = true)
    private void onProcessPacketTail(Packet<?> packet, CallbackInfo ci) {
        EventSendPacketPost event = new EventSendPacketPost(packet);
        event.call();

        if (event.isCancelled())
            ci.cancel();
    }

}