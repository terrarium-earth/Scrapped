package dev.terrarium.minefactoryrenewed.blockentity.container.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class LaserChargerContainer extends MachineContainer {

    public LaserChargerContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.LASER_CHARGER_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
