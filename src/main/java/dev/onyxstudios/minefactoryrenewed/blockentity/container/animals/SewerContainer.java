package dev.onyxstudios.minefactoryrenewed.blockentity.container.animals;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.animals.SewerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class SewerContainer extends MachineContainer {

    public SewerContainer(int id, Inventory inventory, SewerBlockEntity blockEntity) {
        super(ModBlockEntities.SEWER_CONTAINER.get(), id, inventory, blockEntity);
    }
}
