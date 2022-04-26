package dev.onyxstudios.minefactoryrenewed.client.gui.transport;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.transport.ItemRouterContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ItemRouterScreen extends AbstractContainerScreen<ItemRouterContainer> {

    private static final ResourceLocation ITEM_ROUTER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/item_router_gui.png");

    public ItemRouterScreen(ItemRouterContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 201;
        this.inventoryLabelY = imageHeight - 94;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ITEM_ROUTER_GUI);
        blit(poseStack, getGuiLeft(), getGuiTop(), 0, 0, getXSize(), getYSize());
    }
}
