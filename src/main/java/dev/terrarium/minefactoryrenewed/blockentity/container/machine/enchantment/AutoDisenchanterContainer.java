package dev.terrarium.minefactoryrenewed.blockentity.container.machine.enchantment;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.OutputSlot;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class AutoDisenchanterContainer extends MachineContainer {

    public AutoDisenchanterContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.AUTO_DISENCHANTER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 8, 21));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 80, 21));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 2, 80, 57));
        addPlayerSlots(inventory, false);
    }
}
