package dev.terrarium.minefactoryrenewed.blockentity.container.mobs;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.mobs.GrinderBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class GrinderContainer extends MachineContainer {

    public GrinderContainer(int id, Inventory inventory, GrinderBlockEntity blockEntity) {
        super(ModBlockEntities.GRINDER_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
