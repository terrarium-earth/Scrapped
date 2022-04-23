package dev.onyxstudios.minefactoryrenewed.blockentity.container.power;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class EthanolGeneratorContainer extends MachineContainer {

    public EthanolGeneratorContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.ETHANOL_GENERATOR_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
