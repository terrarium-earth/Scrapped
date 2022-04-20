package dev.onyxstudios.minefactoryrenewed.client.gui.machine.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.animals.ChronotyperContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.animals.ChronotyperBlockEntity;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.MachineScreen;
import dev.onyxstudios.minefactoryrenewed.network.ChronotyperButtonMessage;
import dev.onyxstudios.minefactoryrenewed.network.ModPackets;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ChronotyperScreen extends MachineScreen<ChronotyperContainer> {

    private static final ResourceLocation CHRONOTYPER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/chronotyper_gui.png");

    public ChronotyperScreen(ChronotyperContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, CHRONOTYPER_GUI, false);
    }

    @Override
    protected void init() {
        super.init();
        ChronotyperBlockEntity chronotyper = (ChronotyperBlockEntity) menu.getBlockEntity();
        this.addRenderableWidget(new Button(
                getGuiLeft() + 8, getGuiTop() + 20, 132, 20,
                new TranslatableComponent("gui.button.chronotyper." +
                        (chronotyper.isMovingBabies() ? "moving_babies" : "moving_adults")),
                button -> {
                    boolean babies = !chronotyper.isMovingBabies();
                    chronotyper.setMovingBabies(babies);
                    ModPackets.INSTANCE.sendToServer(new ChronotyperButtonMessage(chronotyper.getBlockPos(), babies));
                    button.setMessage(new TranslatableComponent("gui.button.chronotyper." +
                            (chronotyper.isMovingBabies() ? "moving_babies" : "moving_adults")));
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
        MachineBlockEntity machine = menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
        renderIdle(poseStack, machine.getIdleTime(), machine.getMaxIdleTime());
    }
}
