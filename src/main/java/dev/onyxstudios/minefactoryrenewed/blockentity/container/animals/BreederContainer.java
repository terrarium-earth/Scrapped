package dev.onyxstudios.minefactoryrenewed.blockentity.container.animals;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.animals.BreederBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
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
