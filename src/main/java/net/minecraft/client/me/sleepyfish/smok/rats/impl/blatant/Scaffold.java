package net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.club.maxstats.weaveyoutube.event.EventSendPacketPost;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.*;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.util.Vec3;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.lwjgl.input.Keyboard;

// Class from SMok Client by SleepyFish
public class Scaffold extends Rat {

    SlideSetting delay;
    SlideSetting randomize;

    public static BoolSetting blockSprint;
    BoolSetting tower;
    BoolSetting changeItem;

    private BlockData blockData = null;
    private boolean allowSwing = false;

    private float rotYaw;
    private float rotPitch;

    private int lastItem;

    public Scaffold() {
        super(Var.scaffold_name, Category.Blatant, Var.scaffold_desc);
    }

    @Override
    public void setup() {
        this.addSetting(delay = new SlideSetting(Var.scaffold_delay, 90, 25, 300, 10));
        this.addSetting(randomize = new SlideSetting(Var.scaffold_randomize, 0.6, 0, 3, 0.2));
        this.addSetting(tower = new BoolSetting(Var.scaffold_tower, true));
        this.addSetting(changeItem = new BoolSetting(Var.scaffold_changeItem, true));
        this.addSetting(blockSprint = new BoolSetting(Var.scaffold_blockSprint, true));
    }

    @Override
    public void onEnableEvent() {
        Smok.inst.rotManager.setRotating(false);

        if (changeItem.isEnabled()) {
            if (!Utils.holdingBlock()) {
                lastItem = mc.thePlayer.inventory.currentItem;
                Utils.changeToBlock();
            }
        }
    }

    @Override
    public void onDisableEvent() {
        Smok.inst.rotManager.setRotating(false);

        if (changeItem.isEnabled())
            mc.thePlayer.inventory.currentItem = lastItem;

        if (blockSprint.isEnabled())
            if (!mc.thePlayer.isSprinting())
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Keyboard.isKeyDown(this.getBind()))
            return;

        if (mc.theWorld == null || mc.thePlayer.isDead || Smok.inst.ratManager.getBigRatByClass(Aura.class).isEnabled())
            this.toggle();

        if (Utils.canLegitWork()) {
            this.rotateYourBalls();

            if (this.blockBlockPlace())
                return;

            if (Utils.holdingBlock()) {
                Smok.inst.rotManager.setRotating(true);

                if (this.rangeCheck()) {
                    Smok.inst.rotManager.rayTrace(blockData.pos, this.rotYaw, this.rotPitch);
                }

                if (mc.thePlayer.onGround) {
                    if (Utils.isMoving() && Utils.holdingBlock())
                        if (Utils.overAir(1) && blockData.face.getName() != "up")
                            allowSwing = true;
                } else if (tower.isEnabled())
                    if (Utils.overAirCustom(1, 1, 1)) {
                        Smok.inst.rotManager.setRotations(this.rotYaw, MathUtils.randomFloat(85.2F, 89.5F));
                        Smok.inst.rotManager.setRotating(true);
                        allowSwing = true;
                    }
            } else
                Smok.inst.rotManager.setRotating(false);
        }
    }

    @SmokEvent
    public void onPacket(EventSendPacketPost e) {
        if (Keyboard.isKeyDown(this.getBind()))
            return;

        if (e.isCancelled())
            return;

        if (allowSwing) {
            if (Timer.hasTimeElapsed(delay.getValueToLong(), true)) {
                if (tower.isEnabled()) {
                    if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockData.pos, blockData.face, new Vec3(blockData.pos.getX(), blockData.pos.getY(), blockData.pos.getZ()))) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                        allowSwing = false;
                    }
                } else {
                    if (Utils.overAirCustom(1, 1, 1) && !mc.theWorld.isAirBlock(blockData.pos)) {
                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockData.pos, blockData.face, new Vec3(blockData.pos.getX(), blockData.pos.getY(), blockData.pos.getZ()))) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            allowSwing = false;
                        }
                    }
                }
            }
        }
    }

    private void rotateYourBalls() {
        blockData = getBlockData(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ));

        if (!Utils.holdingBlock() || mc.thePlayer.fallDistance > 0.2D)
            this.blockData = null;

        if (blockData == null) {
            Smok.inst.rotManager.setRotating(false);
            return;
        }

        float[] rots = Utils.Combat.getBlockRotations(blockData.pos, blockData.face);
        float sens = Smok.inst.rotManager.getSensitivity();

        rots[0] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationYaw, rots[0], 360);
        rots[1] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationPitch, rots[1], 90);

        rots[0] = Math.round(rots[0] / sens) * sens;
        rots[1] = Math.round(rots[1] / sens) * sens;

        float random = randomize.getValueToFloat();
        this.rotYaw = Math.round(MathUtils.randomFloat(rots[0], rots[0] + random));
        this.rotPitch = Math.round(MathUtils.randomFloat(rots[1], rots[1] + random));

        Smok.inst.rotManager.setRotations(this.rotYaw, this.rotPitch + blockData.face.getHorizontalIndex());
    }

    private boolean blockBlockPlace() {
        if (tower.isEnabled()) {
            if (!mc.theWorld.isAirBlock(new BlockPos(mc.thePlayer).add(-1, -1, 0)))
                return true;

        } else {

            BlockPos var1 = new BlockPos(mc.thePlayer).add(1, -1, 0);
            BlockPos var2 = new BlockPos(mc.thePlayer).add(0, -1, 1);

            if (!mc.theWorld.isAirBlock(var1) && !mc.theWorld.isAirBlock(var2) || !mc.thePlayer.onGround)
                return true;

        }

        if (!this.rangeCheck()) {
            Smok.inst.rotManager.raytracePos = null;
            blockData = null;
            return true;
        }

        return false;
    }

    private boolean rangeCheck() {
        BlockPos player = mc.thePlayer.getPosition();
        BlockPos block = blockData.pos;

        return !(((block.getX() - player.getX()) > 3) && ((block.getY() - player.getY()) > 3) && ((block.getZ() - player.getZ()) > 3));
    }

    private BlockData getBlockData(BlockPos pos) {
        if (Utils.getBlock(pos.add(0, -1, 0)) != Blocks.air)
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);

        else if (Utils.getBlock(pos.add(-1, 0, 0)) != Blocks.air)
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);

        else if (Utils.getBlock(pos.add(1, 0, 0)) != Blocks.air)
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);

        else if (Utils.getBlock(pos.add(0, 0, -1)) != Blocks.air)
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);

        else if (Utils.getBlock(pos.add(0, 0, 1)) != Blocks.air)
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);

        else
            return null;
    }

    public static class BlockData {
        public final BlockPos pos;
        public final EnumFacing face;

        BlockData(final BlockPos pos, final EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }

}