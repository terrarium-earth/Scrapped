package dev.terrarium.minefactoryrenewed.blockentity.container.machine.enchantment;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.OutputSlot;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class AutoEnchanterContainer extends MachineContainer {

    public AutoEnchanterContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.AUTO_ENCHANTER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 80, 21));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 1, 80, 57));
        addPlayerSlots(inventory, false);
    }
}
