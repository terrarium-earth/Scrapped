package dev.onyxstudios.minefactoryrenewed.blockentity.container.animals;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.animals.RancherBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class RancherContainer extends MachineContainer {

    public RancherContainer(int id, Inventory inventory, RancherBlockEntity blockEntity) {
        super(ModBlockEntities.RANCHER_CONTAINER.get(), id, inventory, blockEntity, false);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new SlotItemHandler(blockEntity.getInventory(),
                        i * 3 + j, 8 + j * 18, 15 + i * 18));
            }
        }

        addPlayerSlots(inventory, false);
    }
}
