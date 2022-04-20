package dev.onyxstudios.minefactoryrenewed.blockentity.container.farming;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FarmerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class FarmerContainer extends MachineContainer {

    public FarmerContainer(int id, Inventory inventory, FarmerBlockEntity farmer) {
        super(ModBlockEntities.FARMER_CONTAINER.get(), id, inventory, farmer);
    }
}
