package dev.terrarium.minefactoryrenewed.client.gui.machine.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.blocks.DeepStorageContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.blocks.DeepStorageBlockEntity;
import dev.terrarium.minefactoryrenewed.util.DeepStorageInventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DeepStorageScreen extends AbstractContainerScreen<DeepStorageContainer> {

    private static final ResourceLocation DEEP_STORAGE_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/dsu_gui.png");

    public DeepStorageScreen(DeepStorageContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
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
        RenderSystem.setShaderTexture(0, DEEP_STORAGE_GUI);
        blit(poseStack, getGuiLeft(), getGuiTop(), 0, 0, getXSize(), getYSize());

        //Draw Text
        DeepStorageBlockEntity deepStorage = menu.getBlockEntity();
        String text = (deepStorage.getInventory().getStackInSlot(DeepStorageInventory.STORAGE).getCount() +
                deepStorage.getInventory().getStackInSlot(DeepStorageInventory.OUTPUT).getCount()) + " Items";
        int width = font.width(text);

        font.draw(poseStack, text, (getGuiLeft() + 88) - (width / 2.0f), getGuiTop() + 40, 0xFFFFFF);
    }
}
