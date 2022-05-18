package dev.terrarium.minefactoryrenewed.blockentity.container.farming;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.farming.FruitPickerBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class FruitPickerContainer extends MachineContainer {

    public FruitPickerContainer(int id, Inventory inventory, FruitPickerBlockEntity fruitPicker) {
        super(ModBlockEntities.FRUIT_PICKER_CONTAINER.get(), id, inventory, fruitPicker);
    }
}
