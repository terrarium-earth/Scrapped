package dev.onyxstudios.minefactoryrenewed.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.block.machine.RotatableMachineBlock;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RenderHelper {

    private static final ResourceLocation OUTLINE = new ResourceLocation(MinefactoryRenewed.MODID, "textures/block/outline.png");

    public static void outlineArea(MachineBlockEntity machine, PoseStack stack, MultiBufferSource bufferSource, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        Level level = minecraft.level;
        if (player == null || level == null) return;

        if (player.getMainHandItem().is(ModItems.WRENCH.get()) || player.getOffhandItem().is(ModItems.WRENCH.get())) {
            VertexConsumer buffer = bufferSource.getBuffer(ModRenderTypes.RAINBOW.apply(OUTLINE));
            ModClient.TIME.set(partialTicks + player.tickCount);
            BlockState state = machine.getBlockState();
            BlockPos pos = machine.getBlockPos();

            BlockPos firstCorner = machine.getMachineArea().getFirstCorner().subtract(pos);
            BlockPos secondCorner = machine.getMachineArea().getSecondCorner().subtract(pos);

            float minX = firstCorner.getX();
            float minY = firstCorner.getY() + 0.01f;
            float minZ = firstCorner.getZ();

            float maxX = secondCorner.getX();
            float maxY = secondCorner.getY() - 0.01f;
            float maxZ = secondCorner.getZ();

            if (state.getBlock() instanceof RotatableMachineBlock) {
                switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                    case NORTH -> {
                        minX += 0.01;
                        maxX -= 0.01;
                        minZ += 0.99;
                        maxZ += 1.01;
                    }
                    case SOUTH -> {
                        minZ += 0.01;
                        maxZ -= 0.01;
                        minX += 0.99;
                        maxX += 1.01;
                    }
                    case WEST -> {
                        minZ += 0.99;
                        maxZ += 1.01;
                        minX += 0.99;
                        maxX += 1.01;
                    }
                    case EAST -> {
                        minX += 0.01;
                        maxX -= 0.01;
                        minZ += 0.01;
                        maxZ -= 0.01;
                    }
                    default -> {
                    }
                }
            }

            stack.pushPose();
            vertex(stack, buffer, minX, minY, minZ, 0, 0, 0xFFFFFF, 1);
            vertex(stack, buffer, minX, maxY, minZ, 0, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, maxY, minZ, 1, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, minY, minZ, 1, 0, 0xFFFFFF, 1);

            vertex(stack, buffer, minX, minY, maxZ, 0, 0, 0xFFFFFF, 1);
            vertex(stack, buffer, minX, maxY, maxZ, 0, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, maxY, maxZ, 1, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, minY, maxZ, 1, 0, 0xFFFFFF, 1);

            vertex(stack, buffer, minX, minY, minZ, 0, 0, 0xFFFFFF, 1);
            vertex(stack, buffer, minX, maxY, minZ, 0, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, minX, maxY, maxZ, 1, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, minX, minY, maxZ, 1, 0, 0xFFFFFF, 1);

            vertex(stack, buffer, maxX, minY, minZ, 0, 0, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, maxY, minZ, 0, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, maxY, maxZ, 1, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, minY, maxZ, 1, 0, 0xFFFFFF, 1);

            vertex(stack, buffer, minX, minY, minZ, 0, 0, 0xFFFFFF, 1);
            vertex(stack, buffer, minX, minY, maxZ, 0, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, minY, maxZ, 1, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, minY, minZ, 1, 0, 0xFFFFFF, 1);

            vertex(stack, buffer, minX, maxY, minZ, 0, 0, 0xFFFFFF, 1);
            vertex(stack, buffer, minX, maxY, maxZ, 0, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, maxY, maxZ, 1, 1, 0xFFFFFF, 1);
            vertex(stack, buffer, maxX, maxY, minZ, 1, 0, 0xFFFFFF, 1);
            stack.popPose();
        }
    }

    private static void vertex(PoseStack stack, VertexConsumer buffer,
                               float x, float y, float z,
                               float u, float v, int color, int light) {
        int red = color >> 16 & 0xFF;
        int green = color >> 8 & 0xFF;
        int blue = color & 0xFF;
        int alpha = color >> 24 & 0xFF;

        buffer.vertex(stack.last().pose(), x, y, z)
                .color(red, green, blue, alpha)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(stack.last().normal(), 1, 1, 1)
                .endVertex();
    }
}
