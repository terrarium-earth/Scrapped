package dev.onyxstudios.minefactoryrenewed.client.gui.machine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class MachineScreen<T extends MachineContainer> extends AbstractContainerScreen<T> {

    protected ResourceLocation guiLocation;
    protected int powerBarX = 144;
    protected int powerBarY = 69;
    protected int powerBarU = 181;
    protected int powerBarV = 4;

    protected int workBarX = 153;
    protected int workBarY = 69;
    protected int workBarU = 189;
    protected int workBarV = 4;

    protected int idleBarX = 162;
    protected int idleBarY = 69;
    protected int idleBarU = 197;
    protected int idleBarV = 4;

    protected int barWidth = 6;
    protected int barHeight = 48;

    public MachineScreen(T menu, Inventory inventory, Component title, ResourceLocation guiLocation) {
        super(menu, inventory, title);
        this.guiLocation = guiLocation;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);

        menu.getBlockEntity().getCapability(CapabilityEnergy.ENERGY).ifPresent(storage ->
                renderBarTooltip(poseStack, storage.getEnergyStored(), storage.getMaxEnergyStored(),
                        powerBarX, powerBarY, mouseX, mouseY, "FE")
        );
        renderGui(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.guiLocation);
        blit(poseStack, getGuiLeft(), getGuiTop(), 0, 0, getXSize(), getYSize());

        renderEnergy(poseStack, menu.getBlockEntity());
        renderGuiLast(poseStack, mouseX, mouseY);
    }

    public abstract void renderGui(PoseStack poseStack, int x, int y);

    public abstract void renderGuiLast(PoseStack poseStack, int x, int y);

    public void renderEnergy(PoseStack poseStack, BaseBlockEntity blockEntity) {
        LazyOptional<IEnergyStorage> energy = blockEntity.getCapability(CapabilityEnergy.ENERGY);
        energy.ifPresent(storage ->
                renderBar(poseStack, storage.getEnergyStored(), storage.getMaxEnergyStored(), powerBarX, powerBarY, powerBarU, powerBarV));
    }

    public void renderWork(PoseStack poseStack, int workAmount, int maxWorkAmount) {
        renderBar(poseStack, workAmount, maxWorkAmount, workBarX, workBarY, workBarU, workBarV);
    }

    public void renderIdle(PoseStack poseStack, int idleAmount, int maxIdleAmount) {
        renderBar(poseStack, idleAmount, maxIdleAmount, idleBarX, idleBarY, idleBarU, idleBarV);
    }

    public void renderBar(PoseStack poseStack, int amount, int maxAmount, int x, int y, int u, int v) {
        int i = barHeight;
        int j = 0;
        if (amount > 0)
            j = amount * i / maxAmount;

        blit(poseStack, getGuiLeft() + x, getGuiTop() + y - j, u, (v + barHeight) - j, barWidth, j);
    }

    public void renderBarTooltip(PoseStack poseStack, int amount, int maxAmount, int x, int y, int mouseX, int mouseY, String tag) {
        if (this.isHovering(x - 1, y - barHeight, barWidth, barHeight, mouseX, mouseY)) {
            Component text = new TextComponent(amount + " / " + maxAmount + " " + tag);
            renderTooltip(poseStack, text, mouseX, mouseY);
        }
    }
}
