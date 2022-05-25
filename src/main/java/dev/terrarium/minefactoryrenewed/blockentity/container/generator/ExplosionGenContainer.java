package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.ExplosionGenBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class ExplosionGenContainer extends GeneratorContainer<ExplosionGenBlockEntity> {

    public ExplosionGenContainer(int id, Inventory inventory, ExplosionGenBlockEntity generator) {
        super(ModBlockEntities.EXPLOSION_GENERATOR_CONTAINER.get(), id, inventory, generator);
        this.addSlot(new SlotItemHandler(generator.getInventory(), 0, 80, 35));
    }
}
