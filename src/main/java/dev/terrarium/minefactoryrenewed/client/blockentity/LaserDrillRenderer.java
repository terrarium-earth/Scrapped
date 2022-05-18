package dev.terrarium.minefactoryrenewed.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.blockentity.machine.processing.LaserDrillBlockEntity;
import dev.terrarium.minefactoryrenewed.client.RenderHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

public class LaserDrillRenderer implements BlockEntityRenderer<LaserDrillBlockEntity> {

    @Override
    public void render(LaserDrillBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        if (blockEntity.isValidBedrock()) {
            RenderHelper.renderBeam(
                    poseStack,
                    bufferSource,
                    blockEntity.getBlockPos(),
                    blockEntity.getBedrockPos(),
                    0x8ae4ff,
                    0xF000F0,
                    0.25f
            );
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LaserDrillBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }
}
