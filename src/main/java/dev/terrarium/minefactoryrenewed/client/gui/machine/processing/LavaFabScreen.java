package dev.terrarium.minefactoryrenewed.client.gui.machine.processing;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.processing.LavaFabContainer;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LavaFabScreen extends MachineScreen<LavaFabContainer> {

    private static final ResourceLocation LAVA_FAB_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/lava_fabricator_gui.png");

    public LavaFabScreen(LavaFabContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, LAVA_FAB_GUI, false);
        this.powerBarX = idleBarX;
        this.fluidBarX = 147;
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
    }
}
