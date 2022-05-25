package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.PinkGenBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class PinkGenContainer extends GeneratorContainer<PinkGenBlockEntity> {

    public PinkGenContainer(int id, Inventory inventory, PinkGenBlockEntity generator) {
        super(ModBlockEntities.PINK_GENERATOR_CONTAINER.get(), id, inventory, generator);
        this.addSlot(new SlotItemHandler(generator.getInventory(), 0, 80, 35));
    }
}
