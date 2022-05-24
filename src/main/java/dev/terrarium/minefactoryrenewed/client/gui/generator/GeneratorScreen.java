package dev.terrarium.minefactoryrenewed.client.gui.generator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.GeneratorContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.GeneratorBlockEntity;
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
import org.jetbrains.annotations.NotNull;

public abstract class GeneratorScreen<T extends GeneratorBlockEntity, C extends GeneratorContainer<T>> extends AbstractContainerScreen<C> {

    private final ResourceLocation guiLocation;
    protected int powerBarX = 162;
    protected int powerBarY = 69;
    protected int powerBarU = 181;
    protected int powerBarV = 4;

    protected int fluidBarX = 147;
    protected int fluidBarY = 69;
    protected int fluidBarWidth = 12;
    protected int fluidBarHeight = 48;

    protected int barWidth = 6;
    protected int barHeight = 48;

    public GeneratorScreen(C menu, Inventory inventory, Component title, ResourceLocation guiLocation) {
        super(menu, inventory, title);
        this.guiLocation = guiLocation;
    }

    public void renderGuiLast(PoseStack poseStack, int x, int y) {
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);

        GeneratorBlockEntity generator = menu.getGenerator();
        generator.getCapability(CapabilityEnergy.ENERGY).ifPresent(storage ->
                renderBarTooltip(
                        poseStack,
                        storage.getEnergyStored(),
                        storage.getMaxEnergyStored(),
                        powerBarX, powerBarY,
                        barWidth, barHeight,
                        mouseX, mouseY,
                        "FE"
                )
        );

        if (generator.getTank() != null) {
            renderBarTooltip(
                    poseStack,
                    generator.getTank().getFluidAmount(),
                    generator.getTank().getCapacity(),
                    fluidBarX, fluidBarY,
                    fluidBarWidth, fluidBarHeight,
                    mouseX, mouseY,
                    "MB"
            );
        }
    }

    @Override
    protected void renderBg(@NotNull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.guiLocation);
        blit(poseStack, getGuiLeft(), getGuiTop(), 0, 0, getXSize(), getYSize());

        renderEnergy(poseStack, menu.getGenerator());
        renderFluid(poseStack, menu.getGenerator());

        RenderSystem.setShaderTexture(0, this.guiLocation);
        renderGuiLast(poseStack, mouseX, mouseY);
    }

    private void renderEnergy(PoseStack poseStack, GeneratorBlockEntity blockEntity) {
        LazyOptional<IEnergyStorage> energy = blockEntity.getCapability(CapabilityEnergy.ENERGY);
        energy.ifPresent(storage ->
                renderBar(poseStack, storage.getEnergyStored(), storage.getMaxEnergyStored(), powerBarX, powerBarY, powerBarU, powerBarV));
    }

    private void renderFluid(PoseStack poseStack, GeneratorBlockEntity blockEntity) {
        FluidTank tank = blockEntity.getTank();
        if (tank != null && tank.getFluidAmount() > 0) {
            renderFluid(poseStack, tank);
        }
    }

    public void renderBar(PoseStack poseStack, int amount, int maxAmount, int x, int y, int u, int v) {
        int i = amount > 0 ? amount * barHeight / maxAmount : 0;
        blit(poseStack, getGuiLeft() + x, getGuiTop() + y - i, u, (v + barHeight) - i, barWidth, i);
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
