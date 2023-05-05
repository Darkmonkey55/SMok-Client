package net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.club.maxstats.weaveyoutube.event.EventSendPacket;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
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

// Class from SMok Client by SleepyFish
public class Scaffold extends Rat {

    SlideSetting delay;
    SlideSetting randomize;

    public static BoolSetting blockSprint;
    BoolSetting tower;
    BoolSetting changeItem;

    private BlockData blockData = null;
    private boolean allowSwing = false;

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

        if (changeItem.isToggled())
            if (!Utils.holdingBlock()) {
                lastItem = mc.thePlayer.inventory.currentItem;

                for (int i = 0; i < 9; i++) {
                    ItemStack s = mc.thePlayer.inventory.getStackInSlot(i);
                    if (s != null && s.getItem() instanceof ItemBlock) {
                        boolean b = s.getItem() instanceof ItemAnvilBlock;
                        String n = s.getDisplayName().toLowerCase();

                        if (b || n.equals("sand") || n.equals("red sand") || n.equals("anvil") || n.endsWith("slab") ||
                                n.startsWith("lilly") || n.startsWith("sapling") || n.startsWith("chest") || n.contains("web"))
                            return;

                        mc.thePlayer.inventory.currentItem = i;
                    }
                }
            }
    }

    @Override
    public void onDisableEvent() {
        Smok.inst.rotManager.setRotating(false);

        if (changeItem.isToggled())
            mc.thePlayer.inventory.currentItem = lastItem;

        if (blockSprint.isToggled())
            if (!mc.thePlayer.isSprinting())
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Smok.inst.ratManager.getBigRatByClass(Aura.class).isToggled())
            return;

        if (mc.theWorld == null || mc.thePlayer.isDead)
            this.toggle();

        if (Utils.canLegitWork()) {
            for (double posY = mc.thePlayer.posY - 1; posY > 0D; --posY) {
                BlockData smok = getBlockData(new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ));
                if (smok != null) {
                    double difY;

                    difY = mc.thePlayer.posY - posY;
                    if (difY <= 1) {
                        blockData = smok;
                        break;
                    }
                }
            }

            if (blockData == null)
                return;

            float[] rots = Utils.Combat.getBlockRotations(blockData.pos, blockData.face);

            float random = randomize.getValueToFloat();
            float rotYaw = Math.round(MathUtils.randomFloat(rots[0], rots[0] + random));
            float rotPitch = Math.round(MathUtils.randomFloat(rots[1], rots[1] + random));
            Smok.inst.rotManager.setRotations(rotYaw, rotPitch + blockData.face.getHorizontalIndex());

            if (!tower.isToggled()) {
                BlockPos var1 = new BlockPos(mc.thePlayer).add(1, -1, 0);
                BlockPos var2 = new BlockPos(mc.thePlayer).add(0, -1, 1);

                if (!mc.theWorld.isAirBlock(var1) && !mc.theWorld.isAirBlock(var2) || !mc.thePlayer.onGround)
                    return;

            } else if (!mc.theWorld.isAirBlock(new BlockPos(mc.thePlayer).add(0, -1, 0)))
                return;

            if (Utils.holdingBlock()) {
                Smok.inst.rotManager.setRotating(true);

                if (mc.thePlayer.onGround) {
                    if (Utils.isMoving() && Utils.holdingBlock())
                        if (Utils.overAir(1) && blockData.face.getName() != "up")
                            allowSwing = true;
                } else if (tower.isToggled())
                    if (Utils.overAirCustom(1, 1, 1)) {
                        Smok.inst.rotManager.setRotations(rotYaw, MathUtils.randomFloat(85.2F, 89.5F));
                        Smok.inst.rotManager.setRotating(true);
                        allowSwing = true;
                    }
            } else
                Smok.inst.rotManager.setRotating(false);
        }

    }

    @SmokEvent
    public void onPacket(EventSendPacket e) {
        if (allowSwing)
            if (Timer.hasTimeElapsed(delay.getValueToLong(), true)) {
                if (tower.isToggled()) {
                    if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockData.pos, blockData.face, new Vec3(blockData.pos.getX(), blockData.pos.getY(), blockData.pos.getZ()))) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                        allowSwing = false;
                    }
                } else {
                    if (Utils.overAirCustom(1, 1, 1) && !mc.theWorld.isAirBlock(blockData.pos))
                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockData.pos, blockData.face, new Vec3(blockData.pos.getX(), blockData.pos.getY(), blockData.pos.getZ()))) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            allowSwing = false;
                        }
                }
            }
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

    private class BlockData {
        final BlockPos pos;
        final EnumFacing face;

        BlockData(final BlockPos pos, final EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }

}