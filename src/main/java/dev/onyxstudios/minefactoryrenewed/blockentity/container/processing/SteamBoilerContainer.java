package dev.onyxstudios.minefactoryrenewed.blockentity.container.processing;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.FuelSlot;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class SteamBoilerContainer extends MachineContainer {

    public SteamBoilerContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.STEAM_BOILER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new FuelSlot(blockEntity.getInventory(), 0, 80, 35));
        addPlayerSlots(inventory, false);
    }
}
