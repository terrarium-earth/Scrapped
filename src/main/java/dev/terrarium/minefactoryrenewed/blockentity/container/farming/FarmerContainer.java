package dev.terrarium.minefactoryrenewed.blockentity.container.farming;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.farming.FarmerBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class FarmerContainer extends MachineContainer {

    public FarmerContainer(int id, Inventory inventory, FarmerBlockEntity farmer) {
        super(ModBlockEntities.FARMER_CONTAINER.get(), id, inventory, farmer);
    }
}
