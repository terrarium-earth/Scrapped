package dev.terrarium.minefactoryrenewed.blockentity.container.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.animals.BreederBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class BreederContainer extends MachineContainer {

    public BreederContainer(int id, Inventory inventory, BreederBlockEntity blockEntity) {
        super(ModBlockEntities.BREEDER_CONTAINER.get(), id, inventory, blockEntity, false);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new SlotItemHandler(blockEntity.getInventory(),
                        i * 3 + j, 8 + j * 18, 15 + i * 18));
            }
        }

        addPlayerSlots(inventory, false);
    }
}
