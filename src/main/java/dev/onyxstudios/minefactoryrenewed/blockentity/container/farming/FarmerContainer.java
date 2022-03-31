package dev.onyxstudios.minefactoryrenewed.blockentity.container.farming;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FarmerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FarmerContainer extends MachineContainer {

    public FarmerContainer(int id, Inventory inventory, FarmerBlockEntity farmer) {
        super(ModBlockEntities.FARMER_CONTAINER.get(), id, inventory, farmer);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return InventoryUtils.handleShiftClick(this, player, index);
    }
}
