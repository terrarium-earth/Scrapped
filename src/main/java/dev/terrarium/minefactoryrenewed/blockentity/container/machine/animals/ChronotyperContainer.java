package dev.terrarium.minefactoryrenewed.blockentity.container.machine.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.animals.ChronotyperBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class ChronotyperContainer extends MachineContainer {

    public ChronotyperContainer(int id, Inventory inventory, ChronotyperBlockEntity blockEntity) {
        super(ModBlockEntities.CHRONOTYPER_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
