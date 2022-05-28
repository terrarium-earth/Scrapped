package dev.terrarium.minefactoryrenewed.client.gui.machine.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.animals.RancherContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RancherScreen extends MachineScreen<RancherContainer> {

    private static final ResourceLocation RANCHER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/rancher_gui.png");

    public RancherScreen(RancherContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, RANCHER_GUI, false);
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        MachineBlockEntity machine = menu.getBlockEntity();
        renderBarTooltip(poseStack, machine.getWorkTime(), machine.getMaxWorkTime(), workBarX, workBarY, x, y, "Wk");
        renderBarTooltip(poseStack, machine.getIdleTime(), machine.getMaxIdleTime(), idleBarX, idleBarY, x, y, "Ticks");
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        MachineBlockEntity machine = menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
        renderIdle(poseStack, machine.getIdleTime(), machine.getMaxIdleTime());
    }
}
