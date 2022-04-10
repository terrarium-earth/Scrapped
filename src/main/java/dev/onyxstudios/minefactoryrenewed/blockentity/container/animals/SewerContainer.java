package dev.onyxstudios.minefactoryrenewed.blockentity.container.animals;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.animals.SewerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SewerContainer extends MachineContainer {

    public SewerContainer(int id, Inventory inventory, SewerBlockEntity blockEntity) {
        super(ModBlockEntities.SEWER_CONTAINER.get(), id, inventory, blockEntity);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return InventoryUtils.handleShiftClick(this, player, index);
    }
}
