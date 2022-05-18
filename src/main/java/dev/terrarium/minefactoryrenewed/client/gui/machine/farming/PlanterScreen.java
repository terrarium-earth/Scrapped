package dev.terrarium.minefactoryrenewed.client.gui.machine.farming;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.farming.PlanterContainer;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PlanterScreen extends MachineScreen<PlanterContainer> {

    private static final ResourceLocation PLANTER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/planter_gui.png");

    public PlanterScreen(PlanterContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, PLANTER_GUI);
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        renderBarTooltip(poseStack, menu.getBlockEntity().getWorkTime(), menu.getBlockEntity().getMaxWorkTime(), workBarX, workBarY, x, y, "Wk");
        renderBarTooltip(poseStack, menu.getBlockEntity().getIdleTime(), menu.getBlockEntity().getMaxIdleTime(), idleBarX, idleBarY, x, y, "Ticks");
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        renderWork(poseStack, menu.getBlockEntity().getWorkTime(), menu.getBlockEntity().getMaxWorkTime());
        renderIdle(poseStack, menu.getBlockEntity().getIdleTime(), menu.getBlockEntity().getMaxIdleTime());
    }
}
