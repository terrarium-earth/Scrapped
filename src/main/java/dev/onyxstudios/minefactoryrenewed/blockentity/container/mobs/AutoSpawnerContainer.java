package dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.AutoSpawnerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class AutoSpawnerContainer extends MachineContainer {

    public AutoSpawnerContainer(int id, Inventory inventory, AutoSpawnerBlockEntity blockEntity) {
        super(ModBlockEntities.AUTO_SPAWNER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 8, 21));
        addPlayerSlots(inventory, false);
    }
}
