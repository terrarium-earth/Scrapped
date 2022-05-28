package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.BurnableGenBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class BurnableGenContainer extends GeneratorContainer<BurnableGenBlockEntity> {

    public BurnableGenContainer(int id, Inventory inventory, BurnableGenBlockEntity generator) {
        super(ModBlockEntities.BURNABLE_GENERATOR_CONTAINER.get(), id, inventory, generator);
        this.addSlot(new SlotItemHandler(generator.getInventory(), 0, 80, 35));
    }
}
