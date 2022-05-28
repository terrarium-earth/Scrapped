package dev.terrarium.minefactoryrenewed.blockentity.container.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.FuelSlot;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class SteamBoilerContainer extends MachineContainer {

    public SteamBoilerContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.STEAM_BOILER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new FuelSlot(blockEntity.getInventory(), 0, 80, 35));
        addPlayerSlots(inventory, false);
    }
}
