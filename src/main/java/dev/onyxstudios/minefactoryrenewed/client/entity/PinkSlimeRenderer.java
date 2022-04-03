package dev.onyxstudios.minefactoryrenewed.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.entity.PinkSlimeEntity;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PinkSlimeRenderer extends MobRenderer<PinkSlimeEntity, SlimeModel<PinkSlimeEntity>> {

    private static final ResourceLocation SLIME_LOCATION = new ResourceLocation(MinefactoryRenewed.MODID, "textures/entity/pink_slime.png");

    public PinkSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(ModelLayers.SLIME)), 0.25f);
        this.addLayer(new SlimeOuterLayer<>(this, context.getModelSet()));
    }

    @Override
    public void render(PinkSlimeEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        this.shadowRadius = 0.25f * entity.getSize();
        super.render(entity, yaw, partialTicks, poseStack, buffer, light);
    }

    @Override
    protected void scale(PinkSlimeEntity livingEntity, PoseStack poseStack, float partialTickTime) {
        float f = 0.999F;
        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0D, 0.001F, 0.0D);
        float f1 = (float) livingEntity.getSize();
        float f2 = Mth.lerp(partialTickTime, livingEntity.oSquish, livingEntity.squish) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    @Override
    public ResourceLocation getTextureLocation(PinkSlimeEntity pEntity) {
        return SLIME_LOCATION;
    }
}
