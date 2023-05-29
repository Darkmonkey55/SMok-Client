package net.minecraft.client.club.maxstats.weaveyoutube.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.weavemc.loader.api.event.Event;

// Class from SMok Client by SleepyFish
public class RenderLivingEvent extends Event {

    public final RendererLivingEntity<EntityLivingBase> renderer;
    public final EntityLivingBase entity;
    public final double x, y, z;
    public final float partialTicks;

    public RenderLivingEvent(RendererLivingEntity<EntityLivingBase> renderer, EntityLivingBase entity, double x, double y, double z, float partialTicks) {
        this.renderer = renderer;
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.partialTicks = partialTicks;
    }

}