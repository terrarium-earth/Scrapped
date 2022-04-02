package dev.onyxstudios.minefactoryrenewed.blockentity.container.farming;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FruitPickerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FruitPickerContainer extends MachineContainer {

    public FruitPickerContainer(int id, Inventory inventory, FruitPickerBlockEntity fruitPicker) {
        super(ModBlockEntities.FRUIT_PICKER_CONTAINER.get(), id, inventory, fruitPicker);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return InventoryUtils.handleShiftClick(this, player, index);
    }
}
