package dev.terrarium.minefactoryrenewed.client.gui.generator;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.LavaGenContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.LavaGenBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LavaGenScreen extends GeneratorScreen<LavaGenBlockEntity, LavaGenContainer> {

    private static final ResourceLocation LAVA_GEN_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/lava_generator_gui.png");

    public LavaGenScreen(LavaGenContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, LAVA_GEN_GUI);
    }
}
