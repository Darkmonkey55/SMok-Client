package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant.Scaffold;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Class from SMok Client by SleepyFish
@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    @Shadow public abstract boolean isSneaking();

    @Shadow protected abstract boolean isCurrentViewEntity();

    @Shadow @Final public NetHandlerPlayClient sendQueue;

    @Shadow private boolean serverSneakState;

    @Shadow private boolean serverSprintState;

    @Shadow private double lastReportedPosX;

    @Shadow private double lastReportedPosY;

    @Shadow private double lastReportedPosZ;

    @Shadow private float lastReportedYaw;

    @Shadow private float lastReportedPitch;

    @Shadow private int positionUpdateTicks;

    @Shadow public abstract void setSprinting(boolean b);

    private float yaw, pitch;

    public MixinEntityPlayerSP(World world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    /**
     * @author sleepy
     * @reason server rotations
     */
    @Overwrite
    public void onUpdateWalkingPlayer() {
        this.yaw = Smok.inst.mc.thePlayer.rotationYaw;
        this.pitch = Smok.inst.mc.thePlayer.rotationPitch;

        if (Smok.inst.rotManager.isRotating()) {
            this.yaw = Smok.inst.rotManager.getYaw();
            this.pitch = Smok.inst.rotManager.getPitch();

            Smok.inst.mc.thePlayer.rotationYawHead = this.yaw;
            Smok.inst.mc.thePlayer.renderYawOffset = this.yaw;
        }

        boolean var1 = this.isSprinting();

        if (Smok.inst.ratManager.getBigRatByClass(Scaffold.class).isToggled() && Scaffold.blockSprint.isToggled()) {
            KeyBinding.setKeyBindState(Smok.inst.mc.gameSettings.keyBindSprint.getKeyCode(), false);
            this.setSprinting(false);

            this.serverSprintState = false;
            var1 = false;
        }

        if (var1 != this.serverSprintState) {
            if (var1)
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
            else
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));

            this.serverSprintState = var1;
        }

        boolean var2 = this.isSneaking();
        if (var2 != this.serverSneakState) {
            if (var2)
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
            else
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));

            this.serverSneakState = var2;
        }

        if (this.isCurrentViewEntity()) {
            double var3 = this.posX - this.lastReportedPosX;
            double var5 = this.getEntityBoundingBox().minY - this.lastReportedPosY;
            double var7 = this.posZ - this.lastReportedPosZ;
            double var9 = this.yaw - this.lastReportedYaw;
            double var11 = this.pitch - this.lastReportedPitch;

            boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4 || this.positionUpdateTicks >= 20;
            boolean var14 = var9 != 0.0 || var11 != 0.0;

            if (this.ridingEntity == null) {
                if (var13 && var14)
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.yaw, this.pitch, this.onGround));
                else if (var13)
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround));
                else if (var14)
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.yaw, this.pitch, this.onGround));
                else
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
            } else {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999, this.motionZ, this.yaw, this.pitch, this.onGround));
                var13 = false;
            }

            ++this.positionUpdateTicks;

            if (var13) {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = this.getEntityBoundingBox().minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = 0;
            }

            if (var14) {
                this.lastReportedYaw = this.yaw;
                this.lastReportedPitch = this.pitch;
            }
        }
    }

}