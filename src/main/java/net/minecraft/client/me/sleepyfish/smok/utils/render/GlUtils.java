package net.minecraft.client.me.sleepyfish.smok.utils.render;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class GlUtils {

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, (float) (limit * .01));
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != Smok.inst.mc.displayWidth || framebuffer.framebufferHeight != Smok.inst.mc.displayHeight) {
            if (framebuffer != null)
                framebuffer.deleteFramebuffer();

            return new Framebuffer(Smok.inst.mc.displayWidth, Smok.inst.mc.displayHeight, true);
        }

        return framebuffer;
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
    }


    public static void startScale(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.translate(-x, -y, 0);
    }

    public static void startScale(float x, float y, float width, float height, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((x + (x + width)) / 2, (y + (y + height)) / 2, 0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.translate(-(x + (x + width)) / 2, -(y + (y + height)) / 2, 0);
    }

    public static void stopScale() {
        GlStateManager.popMatrix();
    }

    public static void fixDepth() {
        GlStateManager.disableDepth();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void enableSeeThru() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.disableAlpha();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
    }

    public static void disableSeeThru() {
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

}