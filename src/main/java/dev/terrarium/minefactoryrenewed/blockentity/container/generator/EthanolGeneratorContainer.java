package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.EthanolGeneratorBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class EthanolGeneratorContainer extends GeneratorContainer<EthanolGeneratorBlockEntity> {

    public EthanolGeneratorContainer(int id, Inventory inventory, EthanolGeneratorBlockEntity blockEntity) {
        super(ModBlockEntities.ETHANOL_GENERATOR_CONTAINER.get(), id, inventory, blockEntity);
    }
}
