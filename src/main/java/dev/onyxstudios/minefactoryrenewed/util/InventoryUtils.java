package dev.onyxstudios.minefactoryrenewed.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class InventoryUtils {

    //Cache direction array
    public static final Direction[] VALUES = Direction.values();

    public static void dropInventoryItems(Level level, BlockPos pos, ItemStackHandler inventory) {
        if (inventory == null) return;

        for (int i = 0; i < inventory.getSlots(); i++) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
        }
    }

    public static ItemStack tryInsertItem(Level level, IItemHandler inventory, ItemStack stack) {
        if (inventory == null) return stack;

        ItemStack result;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack invStack = inventory.getStackInSlot(i);
            if (invStack.isEmpty() || invStack.getItem() == stack.getItem()) {
                result = inventory.insertItem(i, stack, false);

                if (result.isEmpty())
                    return result;
            }
        }

        return stack;
    }

    public static LazyOptional<IItemHandler> getNearbyInventory(Level level, BlockPos pos) {
        for (Direction direction : VALUES) {
            BlockPos offsetPos = pos.relative(direction);
            BlockEntity blockEntity = level.getBlockEntity(offsetPos);

            if (blockEntity != null) {
                return blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
            }
        }

        return LazyOptional.empty();
    }

    public static ItemStack handleShiftClick(AbstractContainerMenu container, Player player, int slotIndex) {
        List<Slot> slots = container.slots;
        Slot sourceSlot = slots.get(slotIndex);
        ItemStack inputStack = sourceSlot.getItem();
        if (inputStack == ItemStack.EMPTY) {
            return ItemStack.EMPTY;
        }

        boolean sourceIsPlayer = sourceSlot.container == player.getInventory();
        ItemStack copy = inputStack.copy();

        if (sourceIsPlayer) {
            if (!mergeStack(player.getInventory(), false, sourceSlot, slots, false)) {
                return ItemStack.EMPTY;
            } else {
                return copy;
            }
        } else {
            boolean isMachineOutput = !sourceSlot.mayPickup(player);
            if (!mergeStack(player.getInventory(), true, sourceSlot, slots, !isMachineOutput)) {
                return ItemStack.EMPTY;
            } else {
                return copy;
            }
        }
    }

    private static boolean mergeStack(Inventory playerInv, boolean mergeIntoPlayer, Slot sourceSlot, List<Slot> slots, boolean reverse) {
        ItemStack sourceStack = sourceSlot.getItem();
        int originalSize = sourceStack.getCount();
        int len = slots.size();
        int idx;

        if (sourceStack.isStackable()) {
            idx = reverse ? len - 1 : 0;

            while (sourceStack.getCount() > 0 && (reverse ? idx >= 0 : idx < len)) {
                Slot targetSlot = slots.get(idx);
                if ((targetSlot.container == playerInv) == mergeIntoPlayer) {
                    ItemStack target = targetSlot.getItem();
                    if (ItemStack.isSame(sourceStack, target)) {
                        int targetMax = Math.min(targetSlot.getMaxStackSize(), target.getMaxStackSize());
                        int toTransfer = Math.min(sourceStack.getCount(), targetMax - target.getCount());
                        if (toTransfer > 0) {
                            target.grow(toTransfer);
                            sourceSlot.remove(toTransfer);
                            targetSlot.setChanged();
                        }
                    }
                }

                if (reverse) {
                    idx--;
                } else {
                    idx++;
                }
            }
            if (sourceStack.getCount() == 0) {
                sourceSlot.set(ItemStack.EMPTY);
                return true;
            }
        }

        idx = reverse ? len - 1 : 0;
        while (reverse ? idx >= 0 : idx < len) {
            Slot targetSlot = slots.get(idx);
            if ((targetSlot.container == playerInv) == mergeIntoPlayer
                    && !targetSlot.hasItem() && targetSlot.mayPlace(sourceStack)) {
                targetSlot.set(sourceStack);
                sourceSlot.set(ItemStack.EMPTY);
                return true;
            }

            if (reverse) {
                idx--;
            } else {
                idx++;
            }
        }

        if (sourceStack.getCount() != originalSize) {
            sourceSlot.setChanged();
            return true;
        }

        return false;
    }
}
