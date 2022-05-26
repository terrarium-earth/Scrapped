package dev.terrarium.minefactoryrenewed.client.gui.machine.power;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.EthanolGeneratorContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.EthanolGeneratorBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.generator.GeneratorScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EthanolGeneratorScreen extends GeneratorScreen<EthanolGeneratorBlockEntity, EthanolGeneratorContainer> {

    private static final ResourceLocation ETHANOL_GENERATOR_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/ethanol_generator_gui.png");

    public EthanolGeneratorScreen(EthanolGeneratorContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, ETHANOL_GENERATOR_GUI);
    }
}
