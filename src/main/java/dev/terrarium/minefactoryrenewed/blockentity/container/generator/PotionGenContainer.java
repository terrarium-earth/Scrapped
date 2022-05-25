package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.PotionGenBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class PotionGenContainer extends GeneratorContainer<PotionGenBlockEntity> {

    public PotionGenContainer(int id, Inventory inventory, PotionGenBlockEntity generator) {
        super(ModBlockEntities.POTION_GENERATOR_CONTAINER.get(), id, inventory, generator);
        this.addSlot(new SlotItemHandler(generator.getInventory(), 0, 80, 35));
    }
}
