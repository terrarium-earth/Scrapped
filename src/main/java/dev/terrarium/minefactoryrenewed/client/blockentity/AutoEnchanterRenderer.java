package dev.terrarium.minefactoryrenewed.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment.AutoEnchanterBlockEntity;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class AutoEnchanterRenderer implements BlockEntityRenderer<AutoEnchanterBlockEntity> {

    public static final Material BOOK_LOCATION = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("entity/enchanting_table_book"));
    private final BookModel bookModel;

    public AutoEnchanterRenderer(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void render(AutoEnchanterBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        //Rendering stolen from enchantment table
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.75D, 0.5D);
        float f = (float) blockEntity.time + partialTicks;
        poseStack.translate(0.0D, (double) (0.1F + Mth.sin(f * 0.1F) * 0.01F), 0.0D);

        float f1;
        for (f1 = blockEntity.rot - blockEntity.oRot; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {
        }

        while (f1 < -(float) Math.PI) {
            f1 += ((float) Math.PI * 2F);
        }

        float f2 = blockEntity.oRot + f1 * partialTicks;
        poseStack.mulPose(Vector3f.YP.rotation(-f2));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(80.0F));
        float f3 = Mth.lerp(partialTicks, blockEntity.oFlip, blockEntity.flip);
        float f4 = Mth.frac(f3 + 0.25F) * 1.6F - 0.3F;
        float f5 = Mth.frac(f3 + 0.75F) * 1.6F - 0.3F;
        float f6 = Mth.lerp(partialTicks, blockEntity.oOpen, blockEntity.open);
        this.bookModel.setupAnim(f, Mth.clamp(f4, 0.0F, 1.0F), Mth.clamp(f5, 0.0F, 1.0F), f6);
        VertexConsumer vertexconsumer = BOOK_LOCATION.buffer(bufferSource, RenderType::entitySolid);
        this.bookModel.render(poseStack, vertexconsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
