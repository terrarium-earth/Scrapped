package dev.onyxstudios.minefactoryrenewed.blockentity.container.enchantment;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.OutputSlot;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class AutoAnvilContainer extends MachineContainer {

    public AutoAnvilContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.AUTO_ANVIL_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 44, 21));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 44, 57));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 2, 80, 39));
        addPlayerSlots(inventory, false);
    }
}
