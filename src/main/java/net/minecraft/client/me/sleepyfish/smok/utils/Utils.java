package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Mouse;

// Class from SMok Client by SleepyFish
public class Utils {

    public static boolean isMoving() {
        return Smok.inst.mc.thePlayer.moveForward != 0 || Smok.inst.mc.thePlayer.moveStrafing != 0;
    }

    public static boolean isMovingForward() {
        return Smok.inst.mc.thePlayer.moveForward > 0;
    }

    public static boolean isMovingBackwards() {
        return Smok.inst.mc.thePlayer.moveForward < 0;
    }

    public static boolean canLegitWork() {
        return Smok.inst.mc.thePlayer != null && !Smok.inst.mc.thePlayer.isDead && !Smok.inst.mc.thePlayer.isSpectator() && Smok.inst.mc.theWorld != null && Smok.inst.mc.currentScreen == null;
    }

    public static ItemStack getCurrentItem() {
        return Smok.inst.mc.thePlayer.getCurrentEquippedItem() == null ? new ItemStack(Blocks.air) : Smok.inst.mc.thePlayer.getCurrentEquippedItem();
    }

    public static boolean holdingBlock() {
        if (getCurrentItem() == null)
            return false;
        else
            return getCurrentItem().getItem() instanceof ItemBlock && !getCurrentItem().getDisplayName().equalsIgnoreCase("tnt");
    }

    public static boolean holdingWeapon() {
        Item item = getCurrentItem().getItem();

        if (item == null)
            return false;
        else
            return item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemShears;
    }

    public static Block getBlock(double x, double y, double z) {
        return getBlock(new BlockPos(x, y, z));
    }

    public static Block getBlock(BlockPos blockPos) {
        return Smok.inst.mc.theWorld.getBlockState(blockPos).getBlock();
    }

    public static boolean overAir(double distance) {
        return Smok.inst.mc.theWorld.isAirBlock(new BlockPos(MathHelper.floor_double(Smok.inst.mc.thePlayer.posX), MathHelper.floor_double(Smok.inst.mc.thePlayer.posY - distance), MathHelper.floor_double(Smok.inst.mc.thePlayer.posZ)));
    }

    public static boolean overAirCustom(double distanceX, double distanceY, double distanceZ) {
        return Smok.inst.mc.theWorld.isAirBlock(new BlockPos(MathHelper.floor_double(Smok.inst.mc.thePlayer.posX - distanceX), MathHelper.floor_double(Smok.inst.mc.thePlayer.posY - distanceY), MathHelper.floor_double(Smok.inst.mc.thePlayer.posZ - distanceZ)));
    }

    public static boolean inInv() {
        return Smok.inst.mc.currentScreen instanceof GuiInventory;
    }

    public static boolean inGui() {
        return Smok.inst.mc.currentScreen != null;
    }

    public static void shiftClick(int slot) {
        Smok.inst.mc.playerController.windowClick(Smok.inst.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, Smok.inst.mc.thePlayer);
    }

    public static void clean(int slot) {
        Smok.inst.mc.playerController.windowClick(Smok.inst.mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, Smok.inst.mc.thePlayer);
        Smok.inst.mc.playerController.windowClick(Smok.inst.mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, Smok.inst.mc.thePlayer);
    }

    public static void swap(int from, int to) {
        if (from <= 8)
            from += 36;

        Smok.inst.mc.playerController.windowClick(Smok.inst.mc.thePlayer.inventoryContainer.windowId, from, to, 2, Smok.inst.mc.thePlayer);
    }

    public static class Npc {

        private static EntityOtherPlayerMP npc;

        public static EntityOtherPlayerMP getNpc() {
            return npc;
        }

        public static void spawn() {
            if (Smok.inst.mc.thePlayer != null) {
                npc = new EntityOtherPlayerMP(Smok.inst.mc.theWorld, Smok.inst.mc.thePlayer.getGameProfile());
                npc.copyLocationAndAnglesFrom(Smok.inst.mc.thePlayer);
                npc.setRotationYawHead(Smok.inst.mc.thePlayer.rotationYawHead);
                npc.setSprinting(Smok.inst.mc.thePlayer.isSprinting());
                npc.setSneaking(Smok.inst.mc.thePlayer.isSneaking());
                npc.setInvisible(Smok.inst.mc.thePlayer.isInvisible());
                Smok.inst.mc.theWorld.addEntityToWorld(npc.getEntityId(), npc);
            }
        }

        public static void kill() {
            if (npc != null) {
                Smok.inst.mc.theWorld.removeEntityFromWorld(npc.getEntityId());
                npc = null;
            }
        }
    }

    public static class Combat {

        public static float fovToEntity(Entity target) {
            double x = target.posX - Smok.inst.mc.thePlayer.posX;
            double z = target.posZ - Smok.inst.mc.thePlayer.posZ;
            double yaw = Math.atan2(x, z) * 57.2957795D;
            return (float) (yaw * -1.0D);
        }

        public static double fovFromEntity(Entity target) {
            return ((double) (Smok.inst.mc.thePlayer.rotationYaw - fovToEntity(target)) % 360.0D + 540.0D) % 360.0D - 180.0D;
        }

        public static boolean isInFov(Entity target, float fov) {
            fov = (float) ((double) fov * 0.5D);
            double v = ((double) (Smok.inst.mc.thePlayer.rotationYaw - fovToEntity(target)) % 360.0D + 540.0D) % 360.0D - 180.0D;
            return v > 0.0D && v < (double) fov || (double) (-fov) < v && v < 0.0D;
        }

        public static boolean inRange(Entity target, double range) {
            if (target == null || target.isDead || target.isInvisibleToPlayer(Smok.inst.mc.thePlayer))
                return false;
            return Smok.inst.mc.thePlayer.getDistanceToEntity(target) < range;
        }

        public static float[] getBlockRotations(BlockPos blockPos, EnumFacing facing) {
            double x = blockPos.getX() + 0.5 - Smok.inst.mc.thePlayer.posX + facing.getFrontOffsetX() / 2D;
            double y = Smok.inst.mc.thePlayer.posY + Smok.inst.mc.thePlayer.getEyeHeight() - (blockPos.getY() + 0.5);
            double z = blockPos.getZ() + 0.5 - Smok.inst.mc.thePlayer.posZ + facing.getFrontOffsetZ() / 2D;
            double sqrt = MathHelper.sqrt_double(x * x + z * z);
            float yaw = (float) (Math.atan2(z, x) * 180 / MathUtils.PI) - 90;
            float pitch = (float) (Math.atan2(y, sqrt) * 180 / MathUtils.PI);
            if (yaw < 0) yaw += 360;
            return new float[]{yaw, pitch};
        }

        public static float[] getTargetRotations(Entity target) {
            if (target == null)
                return null;
            else {
                double diffX = target.posX - Smok.inst.mc.thePlayer.posX;
                double diffY;
                if (target instanceof EntityLivingBase) {
                    EntityLivingBase x = (EntityLivingBase) target;
                    diffY = x.posY + (double) x.getEyeHeight() * 0.9D - (Smok.inst.mc.thePlayer.posY + (double) Smok.inst.mc.thePlayer.getEyeHeight());
                } else
                    diffY = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2D - (Smok.inst.mc.thePlayer.posY + (double) Smok.inst.mc.thePlayer.getEyeHeight());

                double diffZ = target.posZ - Smok.inst.mc.thePlayer.posZ;
                float yaw = (float) (Math.atan2(diffZ, diffX) * 180D / MathUtils.PI) - 90F;
                float pitch = (float) (-(Math.atan2(diffY, MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ)) * 180D / MathUtils.PI));
                return new float[]{Smok.inst.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Smok.inst.mc.thePlayer.rotationYaw),
                        Smok.inst.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Smok.inst.mc.thePlayer.rotationPitch)};
            }
        }

        private static boolean blocking = false;

        public static void fakeBlock(Entity en, int m) {
            if (!Utils.holdingWeapon() || Utils.Combat.inRange(en, 8D))
                return;

            if (m == 1)
                if (Smok.inst.mc.thePlayer.ticksExisted % 4 == 0) {
                    Smok.inst.mc.playerController.interactWithEntitySendPacket(Smok.inst.mc.thePlayer, en);
                    Smok.inst.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Smok.inst.mc.thePlayer.getHeldItem()));
                }

            if (m == 2)
                Smok.inst.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Smok.inst.mc.thePlayer.getHeldItem()));

            if (m == 3) {
                if (!blocking && !Smok.inst.mc.thePlayer.isBlocking()) {
                    KeyBinding.setKeyBindState(Smok.inst.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    blocking = true;
                }

                if (blocking && !Mouse.isButtonDown(1) && (Smok.inst.mc.gameSettings.keyBindUseItem.isKeyDown() || Smok.inst.mc.thePlayer.isBlocking()))
                    if (Timer.hasTimeElapsed(125L, true)) {
                        KeyBinding.setKeyBindState(Smok.inst.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                        blocking = false;
                    }
            }
        }

    }

}