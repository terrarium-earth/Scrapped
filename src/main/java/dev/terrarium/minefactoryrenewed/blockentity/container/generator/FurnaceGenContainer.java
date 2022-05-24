package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.FurnaceGenBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class FurnaceGenContainer extends GeneratorContainer<FurnaceGenBlockEntity> {

    public FurnaceGenContainer(int id, Inventory inventory, FurnaceGenBlockEntity generator) {
        super(ModBlockEntities.FURNACE_GENERATOR_CONTAINER.get(), id, inventory, generator);
        this.addSlot(new SlotItemHandler(generator.getInventory(), 0, 80, 35));
    }
}
