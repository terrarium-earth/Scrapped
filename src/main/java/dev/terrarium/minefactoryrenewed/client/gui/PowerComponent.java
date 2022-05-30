package dev.terrarium.minefactoryrenewed.client.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import java.util.*;
import java.util.function.BiConsumer;

import static net.minecraft.world.inventory.InventoryMenu.BLOCK_ATLAS;

public class PowerComponent extends ConfigComponent {

    private static final ResourceLocation COMPONENTS = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/machine_components.png");
    private static final Random RANDOM = new Random();
    private int color = 0xFFFFFF;

    private static final List<BlockSide> SIDES = new ArrayList<>();

    static {
        SIDES.add(new BlockSide(Direction.UP, 20, 2));
        SIDES.add(new BlockSide(Direction.DOWN, 20, 38));
        SIDES.add(new BlockSide(Direction.NORTH, 20, 20));
        SIDES.add(new BlockSide(Direction.SOUTH, 38, 38));
        SIDES.add(new BlockSide(Direction.EAST, 2, 20));
        SIDES.add(new BlockSide(Direction.WEST, 38, 20));
    }

    private final Map<Direction, Set<BakedQuad>> sideQuads = new EnumMap<>(Direction.class);
    private final BiConsumer<PowerComponent, Direction> sideConsumer;
    private final BlockState state;

    public PowerComponent(int x, int y, BlockState state, BiConsumer<PowerComponent, Direction> sideConsumer) {
        this(x, y, state, EmptyModelData.INSTANCE, sideConsumer);
    }

    public PowerComponent(int x, int y, BlockState state, IModelData modelData, BiConsumer<PowerComponent, Direction> sideConsumer) {
        super(x, y);
        this.sideConsumer = sideConsumer;
        this.state = state;
        loadModelQuads(modelData);
    }

    public void loadModelQuads(IModelData data) {
        sideQuads.clear();
        BakedModel blockModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
        for (BakedQuad quad : blockModel.getQuads(state, null, RANDOM, data)) {
            sideQuads.computeIfAbsent(quad.getDirection(), direction -> new LinkedHashSet<>()).add(quad);
        }

        for (Direction direction : Direction.values()) {
            for (BakedQuad quad : blockModel.getQuads(state, direction, RANDOM, data)) {
                sideQuads.computeIfAbsent(quad.getDirection(), computeDir -> new LinkedHashSet<>()).add(quad);
            }
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, COMPONENTS);
        RenderSystem.setShaderColor(red, green, blue, 1.0F);
        blit(poseStack, getX(), getY(), 0, 108, 56, 56);

        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(BLOCK_ATLAS));
        Lighting.setupForFlatItems();

        poseStack.pushPose();
        poseStack.translate(getX(), getY(), 100.0F);

        for (BlockSide side : SIDES) {
            Set<BakedQuad> quads = sideQuads.get(side.direction);

            poseStack.pushPose();
            poseStack.translate(side.x, side.y, 0);
            poseStack.translate(8, 8, 8);

            switch (side.direction) {
                case UP -> poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
                case DOWN -> poseStack.mulPose(Vector3f.XP.rotationDegrees(-90));
                case EAST -> poseStack.mulPose(Vector3f.YP.rotationDegrees(-90));
                case WEST -> poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                case NORTH -> poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
            }

            poseStack.translate(-8, -8, -8);
            poseStack.scale(16, 16, 16);
            for (BakedQuad quad : quads) {
                vertexConsumer.putBulkData(poseStack.last(), quad, 1.0f, 1.0f, 1.0f, 1.0f, 0xF000F0, OverlayTexture.NO_OVERLAY);
            }
            poseStack.popPose();
        }
        poseStack.popPose();

        bufferSource.endLastBatch();
        Lighting.setupFor3DItems();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (BlockSide side : SIDES) {
                boolean hovered = side.isHovered(getX(), getY(), (int) mouseX, (int) mouseY);

                if (hovered) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    sideConsumer.accept(this, side.direction);
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void setColor(int color) {
        this.color = color;
    }

    private record BlockSide(Direction direction, int x, int y) {
        private static final int SIZE = 16;

        public boolean isHovered(int startX, int startY, int mouseX, int mouseY) {
            return mouseX >= x + startX && mouseY >= y + startY && mouseX < x + startX + SIZE && mouseY < y + startY + SIZE;
        }
    }
}
