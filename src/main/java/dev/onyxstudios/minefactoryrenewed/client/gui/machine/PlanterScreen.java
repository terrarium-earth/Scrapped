package dev.onyxstudios.minefactoryrenewed.client.gui.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.PlanterContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PlanterScreen extends MachineScreen<PlanterContainer> {

    private static final ResourceLocation PLANTER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/planter_gui.png");

    public PlanterScreen(PlanterContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, PLANTER_GUI);
        this.imageHeight = 179;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY += 13;
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
