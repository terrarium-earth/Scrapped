package dev.onyxstudios.minefactoryrenewed.blockentity.container.power;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class SteamTurbineContainer extends MachineContainer {

    public SteamTurbineContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.STEAM_TURBINE_CONTAINER.get(), id, inventory, blockEntity, false);
        addPlayerSlots(inventory, false);
    }
}
