package dev.terrarium.minefactoryrenewed.blockentity.container.power;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class EthanolGeneratorContainer extends MachineContainer {

    public EthanolGeneratorContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.ETHANOL_GENERATOR_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
