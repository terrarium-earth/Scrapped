package dev.onyxstudios.minefactoryrenewed.client.gui.machine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.templates.FluidTank;

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

    protected int fluidBarX = 129;
    protected int fluidBarY = 69;
    protected int fluidBarWidth = 12;
    protected int fluidBarHeight = 48;

    protected int barWidth = 6;
    protected int barHeight = 48;

    public MachineScreen(T menu, Inventory inventory, Component title, ResourceLocation guiLocation) {
        this(menu, inventory, title, guiLocation, true);
    }

    public MachineScreen(T menu, Inventory inventory, Component title, ResourceLocation guiLocation, boolean extend) {
        super(menu, inventory, title);
        this.guiLocation = guiLocation;

        if (extend) {
            this.imageHeight = 179;
            this.inventoryLabelY = imageHeight - 94;
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);

        MachineBlockEntity machine = menu.getBlockEntity();
        machine.getCapability(CapabilityEnergy.ENERGY).ifPresent(storage ->
                renderBarTooltip(
                        poseStack,
                        storage.getEnergyStored(),
                        storage.getMaxEnergyStored(),
                        powerBarX, powerBarY,
                        mouseX, mouseY,
                        "FE"
                )
        );

        if (machine.getTank() != null) {
            renderBarTooltip(
                    poseStack,
                    machine.getTank().getFluidAmount(),
                    machine.getTank().getCapacity(),
                    fluidBarX, fluidBarY,
                    fluidBarWidth, fluidBarHeight,
                    mouseX, mouseY,
                    "MB"
            );
        }

        renderGui(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.guiLocation);
        blit(poseStack, getGuiLeft(), getGuiTop(), 0, 0, getXSize(), getYSize());

        renderEnergy(poseStack, menu.getBlockEntity());
        renderFluid(poseStack, menu.getBlockEntity());

        RenderSystem.setShaderTexture(0, this.guiLocation);
        renderGuiLast(poseStack, mouseX, mouseY);
    }

    public abstract void renderGui(PoseStack poseStack, int x, int y);

    public abstract void renderGuiLast(PoseStack poseStack, int x, int y);

    public void renderEnergy(PoseStack poseStack, MachineBlockEntity blockEntity) {
        LazyOptional<IEnergyStorage> energy = blockEntity.getCapability(CapabilityEnergy.ENERGY);
        energy.ifPresent(storage ->
                renderBar(poseStack, storage.getEnergyStored(), storage.getMaxEnergyStored(), powerBarX, powerBarY, powerBarU, powerBarV));
    }

    public void renderFluid(PoseStack poseStack, MachineBlockEntity blockEntity) {
        FluidTank tank = blockEntity.getTank();
        if (tank != null && tank.getFluidAmount() > 0) {
            renderFluid(poseStack, tank);
        }
    }

    public void renderWork(PoseStack poseStack, int workAmount, int maxWorkAmount) {
        renderBar(poseStack, workAmount, maxWorkAmount, workBarX, workBarY, workBarU, workBarV);
    }

    public void renderIdle(PoseStack poseStack, int idleAmount, int maxIdleAmount) {
        renderBar(poseStack, idleAmount, maxIdleAmount, idleBarX, idleBarY, idleBarU, idleBarV);
    }

    public void renderBar(PoseStack poseStack, int amount, int maxAmount, int x, int y, int u, int v) {
        int i = amount > 0 ? amount * barHeight / maxAmount : 0;
        blit(poseStack, getGuiLeft() + x, getGuiTop() + y - i, u, (v + barHeight) - i, barWidth, i);
    }

    public void renderBarTooltip(PoseStack poseStack, int amount, int maxAmount,
                                 int x, int y, int mouseX, int mouseY, String tag) {
        renderBarTooltip(poseStack, amount, maxAmount, x, y, barWidth, barHeight, mouseX, mouseY, tag);
    }

    public void renderBarTooltip(PoseStack poseStack, int amount, int maxAmount,
                                 int x, int y, int width, int height, int mouseX, int mouseY, String tag) {
        if (this.isHovering(x - 1, y - height, width, height, mouseX, mouseY)) {
            Component text = new TextComponent(amount + " / " + maxAmount + " " + tag);
            renderTooltip(poseStack, text, mouseX, mouseY);
        }
    }

    public void renderFluid(PoseStack poseStack, FluidTank tank) {
        renderFluid(poseStack, tank, fluidBarX, fluidBarY, fluidBarWidth, fluidBarHeight);
    }

    public void renderFluid(PoseStack poseStack, FluidTank tank, int barX, int barY, int width, int height) {
        int i = tank.getFluidAmount() * height / tank.getCapacity();
        TextureAtlasSprite sprite = getMinecraft().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(tank.getFluid().getFluid().getAttributes().getStillTexture());
        int color = tank.getFluid().getFluid().getAttributes().getColor();
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        float alpha = (color >> 24 & 0xFF) / 255.0f;

        int x = getGuiLeft() + barX;
        int y = getGuiTop() + barY - i;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(red, green, blue, alpha);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        blit(poseStack, x, y, 0, width, i, sprite);

        //Reset texture back to gui texture
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.guiLocation);
    }
}
