package dev.terrarium.minefactoryrenewed.blockentity.container.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class EthanolReactorContainer extends MachineContainer {

    public EthanolReactorContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.ETHANOL_REACTOR_CONTAINER.get(), id, inventory, blockEntity, false);
        for (int i = 0; i < 9; i++) {
            this.addSlot(new SlotItemHandler(blockEntity.getInventory(), i, 8 + i * 18, 75));
        }

        addPlayerSlots(inventory, true);
    }
}
