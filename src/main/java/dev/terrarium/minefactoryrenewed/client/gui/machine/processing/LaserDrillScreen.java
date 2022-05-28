package dev.terrarium.minefactoryrenewed.client.gui.machine.processing;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.processing.LaserDrillContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.processing.LaserDrillBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LaserDrillScreen extends MachineScreen<LaserDrillContainer> {

    private static final ResourceLocation LASER_DRILL_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/laser_drill_gui.png");

    public LaserDrillScreen(LaserDrillContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, LASER_DRILL_GUI, false);
        this.powerBarX = workBarX;
        this.powerBarY = workBarY;
        this.workBarX = idleBarX;
        this.workBarY = idleBarY;
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        LaserDrillBlockEntity machine = (LaserDrillBlockEntity) menu.getBlockEntity();
        renderBarTooltip(poseStack, machine.getWorkTime(), machine.getMaxWorkTime(), workBarX, workBarY, x, y, "Wk");
        renderBarTooltip(
                poseStack,
                machine.getEnergy().getEnergyStored(),
                machine.getEnergy().getMaxEnergyStored(),
                powerBarX, powerBarY,
                x, y,
                "FE"
        );
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        LaserDrillBlockEntity machine = (LaserDrillBlockEntity) menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
        renderBar(
                poseStack,
                machine.getEnergy().getEnergyStored(),
                machine.getEnergy().getMaxEnergyStored(),
                powerBarX, powerBarY,
                powerBarU, powerBarV
        );
    }
}
