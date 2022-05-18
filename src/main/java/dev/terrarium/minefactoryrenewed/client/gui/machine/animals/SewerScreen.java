package dev.terrarium.minefactoryrenewed.client.gui.machine.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.animals.SewerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SewerScreen extends MachineScreen<SewerContainer> {

    private static final ResourceLocation SEWER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/sewer_gui.png");

    public SewerScreen(SewerContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, SEWER_GUI);
        this.workBarX = idleBarX;
        this.workBarY = idleBarY;
        this.fluidBarX = 147;
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        MachineBlockEntity machine = menu.getBlockEntity();
        renderBarTooltip(poseStack, machine.getWorkTime(), machine.getMaxWorkTime(), workBarX, workBarY, x, y, "Wk");
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        MachineBlockEntity machine = menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
    }
}
