package dev.terrarium.minefactoryrenewed.blockentity.container.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class LaserDrillContainer extends MachineContainer {

    public LaserDrillContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.LASER_DRILL_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 8, 19));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 8, 37));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 2, 8, 55));
        addPlayerSlots(inventory, false);
    }
}
