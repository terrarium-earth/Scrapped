package dev.onyxstudios.minefactoryrenewed.blockentity.container.processing;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class WeatherContainer extends MachineContainer {

    public WeatherContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.WEATHER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addPlayerSlots(inventory, false);
    }
}
