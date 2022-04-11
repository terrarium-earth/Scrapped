package dev.onyxstudios.minefactoryrenewed.client.gui.machine.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.blocks.BlockSmasherContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.blocks.BlockSmasherBlockEntity;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.MachineScreen;
import dev.onyxstudios.minefactoryrenewed.network.ModPackets;
import dev.onyxstudios.minefactoryrenewed.network.SmasherButtonMessage;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BlockSmasherScreen extends MachineScreen<BlockSmasherContainer> {

    private static final ResourceLocation BLOCK_SMASHER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/block_smasher_gui.png");
    private Button addButton;
    private Button subtractButton;

    public BlockSmasherScreen(BlockSmasherContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, BLOCK_SMASHER_GUI);
        this.imageHeight = 166;
        this.inventoryLabelY = imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        BlockSmasherBlockEntity smasher = (BlockSmasherBlockEntity) menu.getBlockEntity();
        this.addButton = this.addRenderableWidget(new Button(
                getGuiLeft() + 8, getGuiTop() + 22, 20, 20,
                new TextComponent("+"),
                button -> {
                    smasher.addFortune();
                    ModPackets.INSTANCE.sendToServer(new SmasherButtonMessage(smasher.getBlockPos(), smasher.getFortune()));
                }
        ));

        this.subtractButton = this.addRenderableWidget(new Button(
                getGuiLeft() + 8, getGuiTop() + 44, 20, 20,
                new TextComponent("-"),
                button -> {
                    smasher.subtractFortune();
                    ModPackets.INSTANCE.sendToServer(new SmasherButtonMessage(smasher.getBlockPos(), smasher.getFortune()));
                }
        ));
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        MachineBlockEntity machine = menu.getBlockEntity();
        renderBarTooltip(poseStack, machine.getWorkTime(), machine.getMaxWorkTime(), workBarX, workBarY, x, y, "Wk");
        renderBarTooltip(poseStack, machine.getIdleTime(), machine.getMaxIdleTime(), idleBarX, idleBarY, x, y, "Ticks");
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        BlockSmasherBlockEntity machine = (BlockSmasherBlockEntity) menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
        renderIdle(poseStack, machine.getIdleTime(), machine.getMaxIdleTime());

        font.draw(poseStack, "Fortune: " + machine.getFortune(), getGuiLeft() + 56, getGuiTop() + 57, 0xFFFFFF);
    }
}
