package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.DisenchantmentGenBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class DisenchantmentGenContainer extends GeneratorContainer<DisenchantmentGenBlockEntity> {
    public DisenchantmentGenContainer(int id, Inventory inventory, DisenchantmentGenBlockEntity generator) {
        super(ModBlockEntities.DISENCHANTMENT_GENERATOR_CONTAINER.get(), id, inventory, generator);
        this.addSlot(new SlotItemHandler(generator.getInventory(), 0, 80, 35));
    }
}
