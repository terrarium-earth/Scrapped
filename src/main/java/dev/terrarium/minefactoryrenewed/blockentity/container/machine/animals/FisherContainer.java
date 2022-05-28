package dev.terrarium.minefactoryrenewed.blockentity.container.machine.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.animals.FisherBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class FisherContainer extends MachineContainer {

    public FisherContainer(int id, Inventory inventory, FisherBlockEntity blockEntity) {
        super(ModBlockEntities.FISHER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 8, 21));
        addPlayerSlots(inventory, false);
    }
}
