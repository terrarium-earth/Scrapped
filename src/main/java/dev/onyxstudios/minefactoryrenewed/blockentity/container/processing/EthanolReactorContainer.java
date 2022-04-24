package dev.onyxstudios.minefactoryrenewed.blockentity.container.processing;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
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
