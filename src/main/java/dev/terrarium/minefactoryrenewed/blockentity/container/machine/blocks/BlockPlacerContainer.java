package dev.terrarium.minefactoryrenewed.blockentity.container.machine.blocks;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class BlockPlacerContainer extends MachineContainer {

    public BlockPlacerContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.BLOCK_PLACER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 8, 21));
        addPlayerSlots(inventory, false);
    }
}
