package dev.terrarium.minefactoryrenewed.blockentity.container.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.animals.SewerBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class SewerContainer extends MachineContainer {

    public SewerContainer(int id, Inventory inventory, SewerBlockEntity blockEntity) {
        super(ModBlockEntities.SEWER_CONTAINER.get(), id, inventory, blockEntity);
    }
}
