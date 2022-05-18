package dev.terrarium.minefactoryrenewed.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.blockentity.machine.processing.LaserChargerBlockEntity;
import dev.terrarium.minefactoryrenewed.client.RenderHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

public class LaserChargerRenderer implements BlockEntityRenderer<LaserChargerBlockEntity> {

    @Override
    public void render(LaserChargerBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.isTargetValid()) {
            poseStack.pushPose();
            poseStack.translate(0, 0.1f, 0);
            RenderHelper.renderBeam(
                    poseStack,
                    bufferSource,
                    blockEntity.getBlockPos(),
                    blockEntity.getTargetPos(),
                    0xed3737,
                    0xF000F0,
                    0.25f
            );
            poseStack.popPose();
        }
    }
}
