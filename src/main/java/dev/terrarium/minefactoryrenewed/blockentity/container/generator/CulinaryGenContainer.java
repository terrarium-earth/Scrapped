package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.CulinaryGenBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class CulinaryGenContainer extends GeneratorContainer<CulinaryGenBlockEntity> {

    public CulinaryGenContainer(int id, Inventory inventory, CulinaryGenBlockEntity generator) {
        super(ModBlockEntities.CULINARY_GENERATOR_CONTAINER.get(), id, inventory, generator);
        this.addSlot(new SlotItemHandler(generator.getInventory(), 0, 80, 35));
    }
}
