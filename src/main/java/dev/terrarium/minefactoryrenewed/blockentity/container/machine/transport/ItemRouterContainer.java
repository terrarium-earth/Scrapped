package dev.terrarium.minefactoryrenewed.blockentity.container.machine.transport;

import dev.terrarium.minefactoryrenewed.blockentity.transport.ItemRouterBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ItemRouterContainer extends AbstractContainerMenu {

    public ItemRouterContainer(int id, Inventory inventory, ItemRouterBlockEntity itemRouter) {
        super(ModBlockEntities.ITEM_ROUTER_CONTAINER.get(), id);

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 9; ++j) {
                int index = i * 9 + j;
                this.addSlot(new SlotItemHandler(itemRouter.getInventory(), index, 8 + j * 18, 21 + i * 18));
            }
        }

        //Player Slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 119 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 177));
        }
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
