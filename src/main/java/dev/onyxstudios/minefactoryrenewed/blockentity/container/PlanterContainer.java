package dev.onyxstudios.minefactoryrenewed.blockentity.container;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.PlanterBlockEntity;
import dev.onyxstudios.minefactoryrenewed.item.MachineUpgradeItem;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PlanterContainer extends MachineContainer {

    public PlanterContainer(int id, Inventory inventory, PlanterBlockEntity planter) {
        super(ModBlockEntities.PLANTER_CONTAINER.get(), id, inventory, planter);
        this.addSlot(new RangeUpgradeSlot(planter.getInventory(), 0, 152, 75));

        //Input Slots
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.addSlot(new SlotItemHandler(planter.getInventory(), 1 + (i * 4 + j), 66 + j * 18, 12 + i * 18));
            }
        }

        //Filter Slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new FilterSlot(planter.getFilterInventory(), i * 3 + j, 8 + j * 18, 30 + i * 18));
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);

        ItemStack itemStack = slot.getItem().copy();
        if (slot.hasItem()) {
            if (itemStack.getItem() instanceof MachineUpgradeItem) {
                if (index == 36) {
                    if (!moveItemStackTo(slot.getItem(), 0, 35, true))
                        return ItemStack.EMPTY;
                } else {
                    if (!moveItemStackTo(slot.getItem(), 36, 37, true))
                        return ItemStack.EMPTY;
                }
            } else if (index >= 53 && index <= 61) {
                if (!moveItemStackTo(slot.getItem(), 3, 39, false))
                    return ItemStack.EMPTY;
            } else if (index >= 0 && index <= 35) {
                if (!moveItemStackTo(slot.getItem(), 37, 52, true))
                    return ItemStack.EMPTY;
            } else if (index >= 37 && index <= 52) {
                if (!moveItemStackTo(slot.getItem(), 0, 35, true))
                    return ItemStack.EMPTY;
            }

            if (itemStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            slot.onTake(player, itemStack);
        }

        return itemStack;
    }

    private static class FilterSlot extends SlotItemHandler {

        public FilterSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }
}
