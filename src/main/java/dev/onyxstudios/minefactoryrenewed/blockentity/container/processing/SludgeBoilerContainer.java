package dev.onyxstudios.minefactoryrenewed.blockentity.container.processing;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class SludgeBoilerContainer extends MachineContainer {

    public SludgeBoilerContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.SLUDGE_BOILER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addPlayerSlots(inventory, false);
    }
}
