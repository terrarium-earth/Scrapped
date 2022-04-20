package dev.onyxstudios.minefactoryrenewed.blockentity.container.farming;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FruitPickerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class FruitPickerContainer extends MachineContainer {

    public FruitPickerContainer(int id, Inventory inventory, FruitPickerBlockEntity fruitPicker) {
        super(ModBlockEntities.FRUIT_PICKER_CONTAINER.get(), id, inventory, fruitPicker);
    }
}
