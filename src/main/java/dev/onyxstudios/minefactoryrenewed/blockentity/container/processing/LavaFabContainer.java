package dev.onyxstudios.minefactoryrenewed.blockentity.container.processing;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class LavaFabContainer extends MachineContainer {

    public LavaFabContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.LAVA_FABRICATOR_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
