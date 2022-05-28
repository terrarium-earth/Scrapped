package dev.terrarium.minefactoryrenewed.blockentity.container.machine.blocks;

import dev.terrarium.minefactoryrenewed.blockentity.machine.blocks.DeepStorageBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class DeepStorageContainer extends AbstractContainerMenu {

    private final DeepStorageBlockEntity blockEntity;

    public DeepStorageContainer(int id, Inventory inventory, DeepStorageBlockEntity blockEntity) {
        super(ModBlockEntities.DEEP_STORAGE_CONTAINER.get(), id);
        this.blockEntity = blockEntity;
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 80, 16));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 80, 58));

        //Player Slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    public DeepStorageBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return InventoryUtils.handleShiftClick(this, player, index);
    }
}
