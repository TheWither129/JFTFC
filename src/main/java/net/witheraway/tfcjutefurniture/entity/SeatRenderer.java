package net.witheraway.tfcjutefurniture.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SeatRenderer extends EntityRenderer<SeatEntity> {
    public SeatRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(SeatEntity seatEntity) {
        return null;
    }
}
