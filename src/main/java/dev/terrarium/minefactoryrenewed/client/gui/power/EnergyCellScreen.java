package dev.terrarium.minefactoryrenewed.client.gui.power;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.power.EnergyCellContainer;
import dev.terrarium.minefactoryrenewed.blockentity.power.EnergyCellBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.PowerComponent;
import dev.terrarium.minefactoryrenewed.client.gui.SideConfigWidget;
import dev.terrarium.minefactoryrenewed.network.EnergyCellPowerMessage;
import dev.terrarium.minefactoryrenewed.network.EnergyCellSideMessage;
import dev.terrarium.minefactoryrenewed.network.ModPackets;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

public class EnergyCellScreen extends AbstractContainerScreen<EnergyCellContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/energy_cell_gui.png");
    private static final ResourceLocation GEAR = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/icons/gear.png");
    protected int powerBarX = 85;
    protected int powerBarY = 69;
    protected int powerBarU = 181;
    protected int powerBarV = 4;

    protected int barWidth = 6;
    protected int barHeight = 48;

    private SideConfigWidget configWidget;

    public EnergyCellScreen(EnergyCellContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        EnergyCellBlockEntity energyCell = menu.getEnergyCell();
        this.addRenderableWidget(new Button(
                getGuiLeft() + 45, getGuiTop() + 50, 20, 20,
                new TextComponent("+"),
                button -> {
                    boolean sneaking = Screen.hasShiftDown();
                    energyCell.addMaxReceive(sneaking ? 1000 : 100);

                    ModPackets.INSTANCE.sendToServer(new EnergyCellPowerMessage(
                            energyCell.getBlockPos(),
                            energyCell.getMaxReceive(),
                            energyCell.getMaxExtract()
                    ));
                }
        ));

        this.addRenderableWidget(new Button(
                getGuiLeft() + 23, getGuiTop() + 50, 20, 20,
                new TextComponent("-"),
                button -> {
                    boolean sneaking = Screen.hasShiftDown();
                    energyCell.addMaxReceive(sneaking ? -1000 : -100);

                    ModPackets.INSTANCE.sendToServer(new EnergyCellPowerMessage(
                            energyCell.getBlockPos(),
                            energyCell.getMaxReceive(),
                            energyCell.getMaxExtract()
                    ));
                }
        ));

        this.addRenderableWidget(new Button(
                getGuiLeft() + 113, getGuiTop() + 50, 20, 20,
                new TextComponent("+"),
                button -> {
                    boolean sneaking = Screen.hasShiftDown();
                    energyCell.addMaxExtract(sneaking ? 1000 : 100);

                    ModPackets.INSTANCE.sendToServer(new EnergyCellPowerMessage(
                            energyCell.getBlockPos(),
                            energyCell.getMaxReceive(),
                            energyCell.getMaxExtract()
                    ));
                }
        ));

        this.addRenderableWidget(new Button(
                getGuiLeft() + 135, getGuiTop() + 50, 20, 20,
                new TextComponent("-"),
                button -> {
                    boolean sneaking = Screen.hasShiftDown();
                    energyCell.addMaxExtract(sneaking ? -1000 : -100);

                    ModPackets.INSTANCE.sendToServer(new EnergyCellPowerMessage(
                            energyCell.getBlockPos(),
                            energyCell.getMaxReceive(),
                            energyCell.getMaxExtract()
                    ));
                }
        ));

        PowerComponent powerComponent = new PowerComponent(getGuiLeft() + 193, getGuiTop() + 24, energyCell.getBlockState(), energyCell.getModelData(), (component, direction) -> {
            ModPackets.INSTANCE.sendToServer(new EnergyCellSideMessage(
                    energyCell.getBlockPos(),
                    direction
            ));
            energyCell.cycleConfig(direction);
            component.loadModelQuads(energyCell.getModelData());
        });
        powerComponent.setColor(0x1f5461);
        this.configWidget = new SideConfigWidget(getGuiLeft() + 176, getGuiTop() + 4, GEAR, new TextComponent("Configuration"));
        this.configWidget.setBackgroundColor(0x1f5461);
        this.configWidget.setTextColor(0xE6B30B);
        this.configWidget.addComponent(powerComponent);

        this.addRenderableWidget(configWidget);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);

        EnergyCellBlockEntity energyCell = menu.getEnergyCell();
        energyCell.getCapability(CapabilityEnergy.ENERGY).ifPresent(storage ->
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
    }

    @Override
    protected void renderBg(@NotNull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        blit(poseStack, getGuiLeft(), getGuiTop(), 0, 0, getXSize(), getYSize());

        renderEnergy(poseStack, menu.getEnergyCell());

        EnergyCellBlockEntity energyCell = menu.getEnergyCell();
        drawCenteredText(poseStack, "Max Input:", getGuiLeft() + 43, getGuiTop() + 30);
        drawCenteredText(poseStack, energyCell.getMaxReceive() + " FE/t", getGuiLeft() + 43, getGuiTop() + 40);

        drawCenteredText(poseStack, "Max Output:", getGuiLeft() + 133, getGuiTop() + 30);
        drawCenteredText(poseStack, energyCell.getMaxExtract() + " FE/t", getGuiLeft() + 133, getGuiTop() + 40);
    }

    public void drawCenteredText(PoseStack stack, String text, int centerX, int y) {
        float x = centerX - (font.width(text) / 2.0f);
        font.draw(stack, text, x, y, 0x3f3f3f);
    }

    public void renderBarTooltip(PoseStack poseStack, int amount, int maxAmount,
                                 int x, int y, int width, int height, int mouseX, int mouseY, String tag) {
        if (this.isHovering(x - 1, y - height, width, height, mouseX, mouseY)) {
            Component text = new TextComponent(amount + " / " + maxAmount + " " + tag);
            renderTooltip(poseStack, text, mouseX, mouseY);
        }
    }

    private void renderEnergy(PoseStack poseStack, EnergyCellBlockEntity blockEntity) {
        LazyOptional<IEnergyStorage> energy = blockEntity.getCapability(CapabilityEnergy.ENERGY);
        energy.ifPresent(storage ->
                renderBar(poseStack, storage.getEnergyStored(), storage.getMaxEnergyStored(), powerBarX, powerBarY, powerBarU, powerBarV));
    }

    public void renderBar(PoseStack poseStack, int amount, int maxAmount, int x, int y, int u, int v) {
        int i = amount > 0 ? amount * barHeight / maxAmount : 0;
        blit(poseStack, getGuiLeft() + x, getGuiTop() + y - i, u, (v + barHeight) - i, barWidth, i);
    }

    public SideConfigWidget getConfigWidget() {
        return configWidget;
    }
}
