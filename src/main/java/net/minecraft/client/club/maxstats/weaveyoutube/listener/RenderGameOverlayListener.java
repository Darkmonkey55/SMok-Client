package net.minecraft.client.club.maxstats.weaveyoutube.listener;

import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.RenderGameOverlayEvent;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderGameOverlayListener {

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        GlStateManager.disableTexture2D();
        GlStateManager.color(1f, 0f, 0f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(0, 50);
        GL11.glVertex2f(50, 50);
        GL11.glVertex2f(50, 0);
        GL11.glEnd();

        GlStateManager.color(1f, 1f, 1f);
        GlStateManager.enableTexture2D();
    }

}