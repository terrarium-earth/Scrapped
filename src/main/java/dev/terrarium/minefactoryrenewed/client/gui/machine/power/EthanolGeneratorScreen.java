package dev.terrarium.minefactoryrenewed.client.gui.machine.power;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.power.EthanolGeneratorContainer;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EthanolGeneratorScreen extends MachineScreen<EthanolGeneratorContainer> {

    private static final ResourceLocation ETHANOL_GENERATOR_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/ethanol_generator_gui.png");

    public EthanolGeneratorScreen(EthanolGeneratorContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, ETHANOL_GENERATOR_GUI, false);
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
