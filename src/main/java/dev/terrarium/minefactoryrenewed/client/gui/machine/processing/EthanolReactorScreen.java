package dev.terrarium.minefactoryrenewed.client.gui.machine.processing;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.processing.EthanolReactorContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.processing.EthanolReactorBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EthanolReactorScreen extends MachineScreen<EthanolReactorContainer> {

    private static final ResourceLocation ETHANOL_REACTOR_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/ethanol_reactor_gui.png");

    public EthanolReactorScreen(EthanolReactorContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, ETHANOL_REACTOR_GUI, false);
        this.fluidBarX = 147;
        this.imageHeight = 179;
        this.inventoryLabelY = imageHeight - 116;
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        EthanolReactorBlockEntity machine = (EthanolReactorBlockEntity) menu.getBlockEntity();
        renderBarTooltip(poseStack, machine.getEfficiency(), machine.getMaxEfficiency(), idleBarX, idleBarY, x, y, "Efficiency");
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        EthanolReactorBlockEntity machine = (EthanolReactorBlockEntity) menu.getBlockEntity();
        renderIdle(poseStack, machine.getEfficiency(), machine.getMaxEfficiency());
    }
}
