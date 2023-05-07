package net.minecraft.client.club.maxstats.weaveyoutube.mixin.custom;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.TargetUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant.Aura;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.other.Animations;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.item.EnumAction;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

// Class from SMok Client by SleepyFish
@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Shadow @Final private Minecraft mc;

    @Shadow private float prevEquippedProgress;

    @Shadow private float equippedProgress;

    @Shadow protected abstract void rotateArroundXAndY(float v, float v1);

    @Shadow protected abstract void setLightMapFromPlayer(AbstractClientPlayer player);

    @Shadow protected abstract void rotateWithPlayerRotations(EntityPlayerSP player, float v);

    @Shadow private ItemStack itemToRender;

    @Shadow protected abstract void renderItemMap(AbstractClientPlayer player, float v, float v1, float v2);

    @Shadow protected abstract void transformFirstPersonItem(float v, float v1);

    @Shadow protected abstract void performDrinking(AbstractClientPlayer player, float v);

    @Shadow protected abstract void doBlockTransformations();

    @Shadow protected abstract void doBowTransformations(float v, AbstractClientPlayer player);

    @Shadow protected abstract void doItemUsedTransformations(float v);

    @Shadow public abstract void renderItem(EntityLivingBase player, ItemStack itemStack, ItemCameraTransforms.TransformType transformType);

    @Shadow protected abstract void renderPlayerArm(AbstractClientPlayer player, float v, float v1);

    /**
     * @author sleepyfish
     * @reason animations
     * @goofy made this shit looking code to imitate minecraft
     */
    @Overwrite
    public void renderItemInFirstPerson(float var1) {
        float var2 = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * var1);
        EntityPlayerSP var3 = this.mc.thePlayer;

        float var4 = var3.getSwingProgress(var1);
        float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var1;
        float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var1;
        this.rotateArroundXAndY(var5, var6);
        this.setLightMapFromPlayer(var3);
        this.rotateWithPlayerRotations(var3, var1);

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();

        if (this.itemToRender != null)
        {
            float var3dd = MathHelper.sin((float) (var4 * var4 * MathUtils.PI));
            float var4dd = MathHelper.sin((float) (MathHelper.sqrt_float(var4) * MathUtils.PI));

            if (this.itemToRender.getItem() == Items.filled_map)
            {
                this.renderItemMap(var3, var5, var2, var4);
            }
              else if (var3.getItemInUseCount() > 0)
            {
                EnumAction var7 = this.itemToRender.getItemUseAction();

                switch (var7) {
                    case NONE:
                        this.transformFirstPersonItem(var2, 0.0F);
                        break;

                    case EAT:
                    case DRINK:
                        this.performDrinking(var3, var1);
                        this.transformFirstPersonItem(var2, 0.0F);
                        break;

                    case BLOCK:
                        this.transformFirstPersonItem(var2, 0.0F);
                        this.doBlockTransformations();
                        break;

                    case BOW:
                        this.transformFirstPersonItem(var2, 0.0F);
                        this.doBowTransformations(var1, var3);
                }
            }
              else
            {
                if (Smok.inst.ratManager.getBigRatByClass(Animations.class).isToggled())
                {
                    if (Animations.mode.getMode() == Animations.modes.Swing)
                        this.transformFirstPersonItem(var2, var4);

                    if (Animations.mode.getMode() == Animations.modes.HighSwing)
                        this.transformFirstPersonItem(var2 - 0.2F, var4);

                    if (Animations.mode.getMode() == Animations.modes.Reversed)
                        this.transformFirstPersonItem(var2, var4 + 0.2F);

                    if (Animations.mode.getMode() == Animations.modes.Goober)
                        this.transformFirstPersonItem(var2 - 0.2F, var4 - 0.2F);

                    if (Animations.mode.getMode() == Animations.modes.Sided)
                    {
                        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
                        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
                        GlStateManager.rotate(var4, 0.0F, 0.0F, 1.0F);

                        GlStateManager.rotate(var3dd * -20.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(var4dd * -20.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.rotate(var4dd * -80.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(0.4F, 0.4F, 0.4F);
                    }

                    if (Animations.mode.getMode() == Animations.modes.Wonky)
                    {
                        GlStateManager.translate(var4, -0.52F, -0.71999997F);
                        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);

                        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(var3dd * -20.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(var4dd * -20.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.rotate(var4dd * -80.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(0.4F, 0.4F, 0.4F);
                    }

                    if (Animations.mode.getMode() == Animations.modes.Spin)
                    {
                        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
                        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
                        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate((1f - var3.swingProgress) * 360F, 1, 0, 0);

                        GlStateManager.rotate(var3dd * -20.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(var4dd * -20.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.rotate(var4dd * -80.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(0.4F, 0.4F, 0.4F);
                    }
                }
                 else
                {
                    this.doItemUsedTransformations(var4);
                    this.transformFirstPersonItem(var2, var4);
                }
            }

            // Client sided Auto block
            if (Smok.inst.ratManager.getBigRatByClass(Aura.class).isToggled())
            {
                if (TargetUtils.getTarget() == null)
                {
                    if (Aura.renderFakeBlock && Aura.renderFakeBlockTimer.delay(2269L))
                    {
                        Aura.renderFakeBlock = false;
                        Aura.renderFakeBlockTimer.reset();
                    }
                }

                if (Aura.renderFakeBlock)
                {
                    if (Utils.holdingWeapon())
                    {
                        this.transformFirstPersonItem(var2, 0.0F);
                        GlStateManager.translate(1F, 1F, 1F);
                        GlStateManager.scale(2.5F, 2.5F, 2.4F);
                        GlStateManager.rotate(10.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(-85.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
                    }
                }
            }

            if (Smok.inst.ratManager.getBigRatByClass(Animations.class).isToggled() && Animations.removeHand.isToggled())
            {
                this.renderItem(var3, null, ItemCameraTransforms.TransformType.FIRST_PERSON);
            }
              else
            {
                this.renderItem(var3, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
            }
        }
          else if (!var3.isInvisible())
        {
            if (Smok.inst.ratManager.getBigRatByClass(Animations.class).isToggled() && Animations.removeHand.isToggled())
            {
                this.renderPlayerArm(var3, -999, 0);
            }
             else
            {
                this.renderPlayerArm(var3, var2, var4);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }

}