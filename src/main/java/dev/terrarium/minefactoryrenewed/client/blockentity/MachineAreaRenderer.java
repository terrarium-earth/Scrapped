package dev.terrarium.minefactoryrenewed.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.client.RenderHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

public class MachineAreaRenderer implements BlockEntityRenderer<MachineBlockEntity> {

    @Override
    public void render(MachineBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        RenderHelper.outlineArea(blockEntity, poseStack, bufferSource, partialTick);
    }
}
