package net.minecraft.client.me.sleepyfish.smok.utils.render;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.FileUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class RenderUtils {

    public static void drawAuthor(int x, int y) {
        RoundedUtils.drawRound(x / 2F - ((float) FontUtils.r20.getStringWidth(Rat.Var.client_author) / 2F + 2F), y - 68F, (float) FontUtils.r20.getStringWidth(Rat.Var.client_author) + 4F, 20, 2, ColorUtils.getBackgroundColor(5).darker());
        RoundedUtils.drawGradientRoundLR(x / 2F - ((float) FontUtils.r20.getStringWidth(Rat.Var.client_author) / 2F), y - 64F, (float) FontUtils.r20.getStringWidth(Rat.Var.client_author), 1, 2, ColorUtils.getClientColor(1), ColorUtils.getClientColor(2000));

        FontUtils.r20.drawStringWithShadow(Rat.Var.client_author, x / 2F - (FontUtils.r20.getStringWidth(Rat.Var.client_author) / 2F), y - 58F, ColorUtils.getClientColor(1));
    }

    public static void drawVersion(int x, int y) {
        RoundedUtils.drawRound(x - 62, y - 33, 63, 27, 2, ColorUtils.getBackgroundColor(5).darker());
        RoundedUtils.drawGradientRoundLR(x - 60, y - 30, 61, 1, 2, ColorUtils.getClientColor(1), ColorUtils.getClientColor(2000));

        FontUtils.r20.drawStringWithShadow("Server: v" + Smok.inst.getServerVersion(), x - 60F, y - 25F, ColorUtils.getClientColor(1));
        FontUtils.r20.drawStringWithShadow("Client: v" + Smok.inst.getClientVersion(), x - 60F, y - 15F, ColorUtils.getClientColor(1).darker());
    }

    public static void drawRect(float x, float y, float width, float height, Color c) {
        float alpha = (c.getRGB() >> 24 & 0xFF) / 255F;
        float red =   (c.getRGB() >> 16 & 0xFF) / 255F;
        float green = (c.getRGB() >> 8 & 0xFF) / 255F;
        float blue =  (c.getRGB() & 0xFF) / 255F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d(x + width, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void drawImage(String image, int x, int y, int width, int height) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.enableBlend();
        GL11.glDepthMask(false);
        ColorUtils.clearColor();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        ResourceLocation res = new ResourceLocation(FileUtils.path + "/" + image);
        Smok.inst.mc.getTextureManager().bindTexture(res);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        ColorUtils.clearColor();
        GL11.glDepthMask(true);
        GlStateManager.disableBlend();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void drawScaledCustomSizeModalRect(double x, double y, float u, float v, int uWidth, int vHeight, double width, double height, float tileWidth, float tileHeight) {
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wR = tess.getWorldRenderer();
        wR.begin(7, DefaultVertexFormats.POSITION_TEX);
        wR.pos(x, (y + height), 0D).tex((u / tileWidth), ((v + (float) vHeight) / tileHeight)).endVertex();
        wR.pos((x + width), (y + height), 0D).tex(((u + (float) uWidth) / tileWidth), ((v + (float) vHeight) / tileHeight)).endVertex();
        wR.pos((x + width), y, 0D).tex(((u + (float) uWidth) / tileWidth), (v / tileHeight)).endVertex();
        wR.pos(x, y, 0D).tex((u / tileWidth), (v / tileHeight)).endVertex();
        tess.draw();
    }

    public static void drawTargetCapsule(Entity entity, double rad, float ticks) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        //GL11.glEnable(2832);
        GL11.glEnable(3042);

        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
        GL11.glDepthMask(false);

        GlStateManager.alphaFunc(GL11.GL_GREATER, 0F);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableCull();
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks - Smok.inst.mc.getRenderManager().viewerPosX;
        final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks - Smok.inst.mc.getRenderManager().viewerPosY + Math.sin(System.currentTimeMillis() / 2E+2) + 1);
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks - Smok.inst.mc.getRenderManager().viewerPosZ;
        Color C = ColorUtils.getClientColor(1).brighter();

        for (float i = 0; i < MathUtils.PI * 2; i += MathUtils.PI * 2 / 18.F) { // - MathUtils.PI * 2 / 64.F
            final double vecX = x + rad * Math.cos(i);
            final double vecZ = z + rad * Math.sin(i);

            GlStateManager.color(C.getRed() / 254F, C.getGreen() / 254F, C.getBlue() / 254F, 0);
            GL11.glVertex3d(vecX, y - Math.cos(System.currentTimeMillis() / 2E+2) / 2F, vecZ);
            GlStateManager.color(C.getRed() / 254F, C.getGreen() / 254F, C.getBlue() / 254F, 0.85F);
            GL11.glVertex3d(vecX, y, vecZ);

            GL11.glColor4f(255F, 255F, 255F, 20F);
        }

        GL11.glEnd();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GlStateManager.enableCull();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

        GL11.glDisable(2848);
        GL11.glDisable(2848);
        //GL11.glEnable(2832);
        GL11.glEnable(3553);

        GL11.glPopMatrix();
    }

    public static void drawRound(float x, float y, float x1, float y1, float radius, Color c) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GlStateManager.enableBlend();
        x *= 2D;
        y *= 2D;
        x1 *= 2D;
        y1 *= 2D;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GlStateManager.color(1F, 1F, 1F, 1F);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        ColorUtils.setColor(c);
        int i;

        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * MathUtils.PI / 180D) * radius * -1D, y + radius + Math.cos(i * MathUtils.PI / 180D) * radius * -1D);

        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * MathUtils.PI / 180D) * radius * -1D, y1 - radius + Math.cos(i * MathUtils.PI / 180D) * radius * -1D);

        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * MathUtils.PI / 180D) * radius, y1 - radius + Math.cos(i * MathUtils.PI / 180D) * radius);

        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * MathUtils.PI / 180D) * radius, y + radius + Math.cos(i * MathUtils.PI / 180D) * radius);

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glScaled(2D, 2D, 2D);
        GL11.glPopAttrib();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

    public static void drawBlock(BlockPos bp, int c) {
        double x = (double) bp.getX() - Smok.inst.mc.getRenderManager().viewerPosX;
        double y = (double) bp.getY() - Smok.inst.mc.getRenderManager().viewerPosY;
        double z = (double) bp.getZ() - Smok.inst.mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2F);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        float a = (float) (c >> 24 & 255) / 255F;
        float r = (float) (c >> 16 & 255) / 255F;
        float g = (float) (c >> 8 & 255) / 255F;
        float b = (float) (c & 255) / 255F;
        GL11.glColor4d(r, g, b, a);
        drawAroundAxisbox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D), r, g, b);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawAroundAxisbox(AxisAlignedBB box, float r, float g, float b) {
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        float alpha = 0.25F;
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        tess.draw();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        tess.draw();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        tess.draw();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        tess.draw();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        tess.draw();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        tess.draw();
    }

    public static class ItemRenderer {
        public static void render(ItemStack itemStack, int x, int y) {
            if (itemStack == null)
                return;

            Smok.inst.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            Smok.inst.mc.getRenderItem().renderItemIntoGUI(itemStack, x, y);
        }
    }

}