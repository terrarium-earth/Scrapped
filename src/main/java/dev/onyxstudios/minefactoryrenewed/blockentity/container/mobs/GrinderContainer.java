package dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.GrinderBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class GrinderContainer extends MachineContainer {

    public GrinderContainer(int id, Inventory inventory, GrinderBlockEntity blockEntity) {
        super(ModBlockEntities.GRINDER_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
