package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.LavaGenBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class LavaGenContainer extends GeneratorContainer<LavaGenBlockEntity> {

    public LavaGenContainer(int id, Inventory inventory, LavaGenBlockEntity generator) {
        super(ModBlockEntities.LAVA_GENERATOR_CONTAINER.get(), id, inventory, generator);
    }
}
