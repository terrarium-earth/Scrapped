package dev.terrarium.minefactoryrenewed.blockentity.container.mobs;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.mobs.AutoSpawnerBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class AutoSpawnerContainer extends MachineContainer {

    public AutoSpawnerContainer(int id, Inventory inventory, AutoSpawnerBlockEntity blockEntity) {
        super(ModBlockEntities.AUTO_SPAWNER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 8, 21));
        addPlayerSlots(inventory, false);
    }
}
