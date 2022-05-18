package dev.terrarium.minefactoryrenewed.blockentity.container.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.animals.VeterinaryBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class VeterinaryContainer extends MachineContainer {

    public VeterinaryContainer(int id, Inventory inventory, VeterinaryBlockEntity blockEntity) {
        super(ModBlockEntities.VETERINARY_CONTAINER.get(), id, inventory, blockEntity, false);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new SlotItemHandler(blockEntity.getInventory(),
                        i * 3 + j, 8 + j * 18, 15 + i * 18));
            }
        }

        addPlayerSlots(inventory, false);
    }
}
