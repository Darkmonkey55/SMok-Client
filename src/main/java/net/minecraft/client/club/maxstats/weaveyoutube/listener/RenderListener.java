package net.minecraft.client.club.maxstats.weaveyoutube.listener;

import club.maxstats.weave.loader.api.event.SubscribeEvent;
import net.minecraft.client.club.maxstats.weaveyoutube.event.RenderLivingEvent;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.*;
import net.minecraft.client.me.sleepyfish.smok.utils.render.GlUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Esp;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant.Aura;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Nametags;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

// Class from SMok Client by SleepyFish
public class RenderListener {

    public static final int npc_ver = 2;
    public boolean render = true;

    @SubscribeEvent
    public void onMyEvent(RenderLivingEvent e) {
        if (Smok.inst.ratManager != null) {
            if (e.entity != Smok.inst.mc.thePlayer && e.entity != Utils.Npc.getNpc()) {
                if (Smok.inst.ratManager.getBigRatByClass(Esp.class).isToggled())
                    this.renderESP(e.entity, e.partialTicks);

                if (Smok.inst.ratManager.getBigRatByClass(Nametags.class) != null)
                    if (Smok.inst.ratManager.getBigRatByClass(Nametags.class).isToggled())
                        this.renderNametag(e.entity, e.partialTicks);
            }

            if (Aura.esp.isToggled() && Smok.inst.ratManager.getBigRatByClass(Aura.class).isToggled())
                if (TargetUtils.getTarget() != null && !Utils.inGui())
                    if (Utils.Combat.inRange(TargetUtils.getTarget(), Aura.attackRange.getValue()))
                        RenderUtils.drawTargetCapsule(TargetUtils.getTarget(), 0.8D, e.partialTicks);
        }
    }

    private void renderNametag(Entity entity, float ticks) {
        if (entity.getUniqueID().version() != npc_ver && this.render && entity instanceof EntityPlayer)
            renderNametagFunc((EntityLivingBase) entity, ticks);
    }

    private void renderESP(Entity entity, float ticks) {
        if (entity.getUniqueID().version() != npc_ver && this.render && entity instanceof EntityPlayer)
            renderESPFunc((EntityLivingBase) entity, ticks);
    }

    private void renderESPFunc(EntityLivingBase entity, float ticks) {
        if (!entity.isInvisibleToPlayer(Smok.inst.mc.thePlayer) && !BotUtils.isBot(entity)) {
            GL11.glPushMatrix();

            Color goober = ColorUtils.getClientColor(1);
            int hex = goober.getRGB();
            float red = ((hex >> 16) & 0xff) / 255F;
            float green = ((hex >> 8) & 0xff) / 255F;
            float blue = (hex & 0xff) / 255F;

            AxisAlignedBB aabb = entity.getEntityBoundingBox();
            double entityWidth = aabb.maxX - aabb.minX;
            double entityHeight = aabb.maxY - aabb.minY;

            double centerX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
            double centerY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
            double centerZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

            float size = (float) Math.max(entityWidth, entityHeight) + 0.5F;
            float sizeFromCenter = size / 2.0F;

            if (Esp.mode.getMode() != Esp.modes.Vape && Esp.mode.getMode() != Esp.modes.Capsule) {
                GL11.glTranslated(centerX, centerY + (entityHeight / 2), centerZ);
                GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0F, 1F, 0F);
                GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, 1F, 0F, 0F);
            }

            if (Esp.mode.getMode() == Esp.modes.Weave) {
                GlUtils.enableSeeThru();

                GL11.glColor4f(red, green, blue, 255f);

                GL11.glBegin(GL11.GL_LINE_LOOP);
                GL11.glVertex2f(sizeFromCenter - 0.5F, sizeFromCenter);
                GL11.glVertex2f(sizeFromCenter - 0.5F, -sizeFromCenter);
                GL11.glVertex2f(-sizeFromCenter + 0.5F, -sizeFromCenter);
                GL11.glVertex2f(-sizeFromCenter + 0.5F, sizeFromCenter);
                GL11.glEnd();

                if (Esp.showHealth.isToggled()) {
                    float healthSize = (entity.getHealth() / entity.getMaxHealth()) * size;
                    GlStateManager.color(1.0F - healthSize, healthSize, 0.0F);
                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex2f(size * 0.65F - 0.5F, -sizeFromCenter + healthSize);
                    GL11.glVertex2f(size * 0.65F - 0.5F, -sizeFromCenter);
                    GL11.glVertex2f(size * 0.6F - 0.5F, -sizeFromCenter);
                    GL11.glVertex2f(size * 0.6F - 0.5F, -sizeFromCenter + healthSize);
                    GL11.glEnd();
                }

                GlUtils.disableSeeThru();
            }

            if (Esp.mode.getMode() == Esp.modes.Smok)
                RenderUtils.drawImage(FileUtils.path + "/modules/esp_1.png", -1, -1, 2, 3, 255F);

            if (Esp.mode.getMode() == Esp.modes.FischKock)
                RenderUtils.drawImage(FileUtils.path + "/modules/esp_2.png", -1, -1, 2, 3, 255F);

            if (Esp.mode.getMode() == Esp.modes.Kioshii)
                RenderUtils.drawImage(FileUtils.path + "/modules/esp_3.png", -1, -1, 2, 3, 255F);

            if (Esp.mode.getMode() == Esp.modes.Capsule) {
                GlUtils.enableSeeThru();
                RenderUtils.drawTargetCapsule(entity, 0.6F, ticks);
                GlUtils.disableSeeThru();
            }

            if (Esp.mode.getMode() == Esp.modes.Vape) {
                GlUtils.enableSeeThru();

                double expand = 0.07D;
                AxisAlignedBB axis = entity.getEntityBoundingBox().expand(expand, expand, expand);

                AxisAlignedBB box = new AxisAlignedBB(axis.minX - entity.posX + centerX, axis.minY - entity.posY + centerY,
                        axis.minZ - entity.posZ + centerZ, axis.maxX - entity.posX + centerX, axis.maxY - entity.posY
                        + centerY, axis.maxZ - entity.posZ + centerZ);

                GL11.glColor4f(red, green, blue, 255f);
                RenderGlobal.drawSelectionBoundingBox(box);
                ColorUtils.clearColor();

                if (Esp.showHealth.isToggled()) {
                    GL11.glTranslated(centerX, centerY + (entityHeight / 2), centerZ);
                    GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0F, 1F, 0F);

                    float healthSize = (entity.getHealth() / entity.getMaxHealth()) * size;
                    GlStateManager.color(1.0F - healthSize, healthSize, 0.0F);
                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex2f(size * 0.65F - 0.5F, -sizeFromCenter + healthSize);
                    GL11.glVertex2f(size * 0.65F - 0.5F, -sizeFromCenter);
                    GL11.glVertex2f(size * 0.6F - 0.5F, -sizeFromCenter);
                    GL11.glVertex2f(size * 0.6F - 0.5F, -sizeFromCenter + healthSize);
                    GL11.glEnd();
                }

                GlUtils.disableSeeThru();
            }

            GL11.glPopMatrix();
        }
    }

    private void renderNametagFunc(EntityLivingBase entity, float ticks) {
        if (!entity.isInvisibleToPlayer(Smok.inst.mc.thePlayer) && !BotUtils.isBot(entity)) {
            GL11.glPushMatrix();

            Color smok = ColorUtils.getClientColor(1);
            int hex = smok.getRGB();
            float red = ((hex >> 16) & 0xff) / 255F;
            float green = ((hex >> 8) & 0xff) / 255F;
            float blue = (hex & 0xff) / 255F;

            AxisAlignedBB aabb = entity.getEntityBoundingBox();
            double entityWidth = aabb.maxX - aabb.minX;
            double entityHeight = aabb.maxY - aabb.minY;

            double centerX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
            double centerY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
            double centerZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

            float size = (float) Math.max(entityWidth, entityHeight) + 0.5F;
            float sizeFromCenter = size / 2.0F;

            GL11.glTranslated(centerX, centerY + (entityHeight / 2), centerZ);
            GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0F, 1F, 0F);

            GlUtils.enableSeeThru();
            GL11.glColor4f(red, green, blue, 255f);

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex2f(sizeFromCenter - 0.5F, sizeFromCenter);
            GL11.glVertex2f(sizeFromCenter - 0.5F, -sizeFromCenter);
            GL11.glEnd();

            GlUtils.disableSeeThru();
            ColorUtils.clearColor();
            GL11.glPopMatrix();
        }
    }

}