package net.minecraft.client.me.sleepyfish.smok.rats.impl.visual;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventRender2D;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.TargetUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Animation;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Direction;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.impl.EaseBackIn;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.simple.SimpleAnimation;
import net.minecraft.client.me.sleepyfish.smok.utils.render.GlUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.StencilUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

// Class from SMok Client by SleepyFish
public class Target_Hud extends Rat {

    private ScaledResolution sr;

    private String name;
    private float health;
    private boolean loaded;
    private Animation introAnimation;
    private ResourceLocation targetSkin;
    private ItemStack itemStack;
    private final SimpleAnimation healthAnimation = new SimpleAnimation(0.0F);
    private final SimpleAnimation redAnimation = new SimpleAnimation(0.0F);
    private final SimpleAnimation damageAnimation = new SimpleAnimation(0.0F);

    public Target_Hud() {
        super(Var.target_hud_name, Category.Visual, Var.target_hud_desc);
    }

    @Override
    public void onEnableEvent() {
        sr = new ScaledResolution(mc);
    }

    @SmokEvent
    public void onRender(EventRender2D e) {
        int scaleOffset;
        float offset;

        if (ClientUtils.inClickGui())
            TargetUtils.setTarget(mc.thePlayer);

        if (TargetUtils.getTarget() != null) {
            if (!loaded) {
                introAnimation = new EaseBackIn(450, 1, 2);
                loaded = true;
            }

            int healthInit = (int) (((EntityPlayer) TargetUtils.getTarget()).getHealth() > 20 ? 20 : ((EntityPlayer) TargetUtils.getTarget()).getHealth());

            introAnimation.setDirection(Direction.FORWARDS);
            name = TargetUtils.getTarget().getName();
            itemStack = ((EntityPlayer) TargetUtils.getTarget()).getCurrentEquippedItem();
            health = healthInit;
            targetSkin = ((AbstractClientPlayer) TargetUtils.getTarget()).getLocationSkin();
            scaleOffset = (int) (((EntityPlayer) TargetUtils.getTarget()).hurtTime * 0.35f);
            offset = -(((AbstractClientPlayer) TargetUtils.getTarget()).hurtTime * 23);
        } else {
            if (loaded)
                introAnimation.setDirection(Direction.BACKWARDS);

            scaleOffset = 0;
            offset = 0;
        }

        damageAnimation.setAnimation(scaleOffset, 100);
        redAnimation.setAnimation(offset, 100);

        if (loaded && name != null && targetSkin != null) {
            float getX = sr.getScaledWidth() / 2F + 85F;
            float getY = sr.getScaledHeight() / 2F + 45F;

            GlUtils.startScale(getX, getY, 140, 48, (float) introAnimation.getValue());
            RoundedUtils.drawRound(getX, getY, 140, 48, 3, ColorUtils.getBackgroundColor(5, 200).darker());
            //RoundedUtils.drawRoundOutline(getX, getY, 140, 50, 3, 3, new Color(0.0F, 0.0F, 0.0F, 0.35F), ColorUtils.getBackgroundColor(5, 240).darker());
            mc.getTextureManager().bindTexture(targetSkin);

            StencilUtils.initStencilToWrite();
            RenderUtils.drawRound(getX + 5 + damageAnimation.getValue(), getY + 5 + damageAnimation.getValue(), getX + 5 + 30 - damageAnimation.getValue(), getY + 5 + 30 - damageAnimation.getValue(), 4 * 2, ColorUtils.getFontColor(1));
            StencilUtils.readStencilBuffer(1);
            ColorUtils.setColor(new Color(255, (int) (255 + redAnimation.getValue()), (int) (255 + redAnimation.getValue())).getRGB());
            RenderUtils.drawScaledCustomSizeModalRect(getX + 5 + damageAnimation.getValue(), getY + 5 + damageAnimation.getValue(), 3, 3, 3, 3, 30 - (damageAnimation.getValue() * 2), 30 - (damageAnimation.getValue() * 2), 24, 24.5F);
            StencilUtils.uninitStencilBuffer();

            FontUtils.r20.drawString((int) health + " Health", getX + 42.5F, getY + 18, ColorUtils.getFontColor(1).getRGB());

            StencilUtils.initStencilToWrite();
            RenderUtils.drawRect(getX, getY, 120, 40, ColorUtils.getFontColor(1).getRGB());
            StencilUtils.readStencilBuffer(1);
            FontUtils.rBold22.drawString(name, getX + 40, getY + 5, ColorUtils.getFontColor(1).getRGB());
            StencilUtils.uninitStencilBuffer();

            healthAnimation.setAnimation(health * 6.5F, 12);
            RoundedUtils.drawGradientRoundLR(getX + 5, getY + 40, healthAnimation.getValue(), 3, 1, ColorUtils.getClientColor(1), ColorUtils.getClientColor(130));

            RenderUtils.ItemRenderer.render(itemStack, (int) (getX + 122), (int) (getY + 20));
            GlUtils.fixDepth();
            GlUtils.stopScale();
        }
    }

    /*
    @SmokEvent
    public void onRender(EventRender2D e) {
        double oldHealth = 0;

        EntityPlayer target = null;
        if (mc.objectMouseOver.entityHit != null) {
            target = (EntityPlayer) mc.objectMouseOver.entityHit;
            oldHealth = target.getHealth();
        } else if (target != null)
            if (Timer.hasTimeElapsed(2000L, true) && target.getHealth() == oldHealth)
                target = null;

        if (target != null) {
            int offsetX = 0;
            int offsetY = 0;
            int width = 60;
            int height = 30;

            FontUtils.r16.drawString(target.getName(), sr.getScaledWidth() / 2F + 5 + offsetX, sr.getScaledHeight() / 2F + 5 + offsetY, ColorUtils.getClientColor(69).getRGB());
            RenderUtils.drawRound(sr.getScaledWidth() / 2F + offsetX, sr.getScaledHeight() / 2F + offsetY, sr.getScaledWidth() / 2F + width + offsetX, sr.getScaledHeight() / 2F + height + offsetY, 2, ColorUtils.getClientColor(1, 170));
        }
    }
     */

}