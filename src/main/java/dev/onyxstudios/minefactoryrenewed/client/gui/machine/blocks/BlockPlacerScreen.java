package dev.onyxstudios.minefactoryrenewed.client.gui.machine.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.blocks.BlockPlacerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BlockPlacerScreen extends MachineScreen<BlockPlacerContainer> {

    private static final ResourceLocation BLOCK_PLACER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/block_placer_gui.png");

    public BlockPlacerScreen(BlockPlacerContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, BLOCK_PLACER_GUI);
        this.imageHeight = 166;
        this.inventoryLabelY = imageHeight - 94;
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
