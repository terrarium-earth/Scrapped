package dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.MeatPackerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class MeatPackerContainer extends MachineContainer {

    public MeatPackerContainer(int id, Inventory inventory, MeatPackerBlockEntity blockEntity) {
        super(ModBlockEntities.MEAT_PACKER_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
