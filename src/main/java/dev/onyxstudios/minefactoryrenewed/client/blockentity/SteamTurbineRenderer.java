package dev.onyxstudios.minefactoryrenewed.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.power.SteamTurbineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.client.ModClient;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.power.SteamTurbineScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.data.EmptyModelData;

public class SteamTurbineRenderer implements BlockEntityRenderer<SteamTurbineBlockEntity> {

    @Override
    public void render(SteamTurbineBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        poseStack.pushPose();
        if (blockEntity.getTank().getFluidAmount() >= 80 && blockEntity.hasEnoughPower()) {
            long angle = (System.currentTimeMillis() / 2) % 360;
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(angle));
            poseStack.translate(-0.5, 0, -0.5);
        }
        ModelBlockRenderer renderer = Minecraft.getInstance().getBlockRenderer().getModelRenderer();
        Minecraft.getInstance().getTextureManager().bindForSetup(InventoryMenu.BLOCK_ATLAS);
        renderer.renderModel(poseStack.last(), bufferSource.getBuffer(RenderType.cutout()), null,
                ModClient.BLADES_MODEL, 1, 1, 1, packedLight, packedOverlay, EmptyModelData.INSTANCE);
        poseStack.popPose();
    }
}
