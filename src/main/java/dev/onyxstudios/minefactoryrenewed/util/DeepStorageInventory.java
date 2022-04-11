package dev.onyxstudios.minefactoryrenewed.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class DeepStorageInventory extends ItemStackHandler {

    public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public static final int STORAGE = 2;

    private ItemStack itemStack = ItemStack.EMPTY;

    public DeepStorageInventory() {
        super(3);
    }

    //Have to copy (de-)/serialization because ItemStack gets count as a byte in nbt
    @Override
    public CompoundTag serializeNBT() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                itemTag.putInt("DeepStorageCount", stacks.get(i).getCount());
                stacks.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        nbt.put("ItemStack", itemStack.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setSize(nbt.contains("Size", Tag.TAG_INT) ? nbt.getInt("Size") : stacks.size());
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, ItemStack.of(itemTags));
                stacks.get(slot).setCount(itemTags.getInt("DeepStorageCount"));
            }
        }
        this.itemStack = ItemStack.of(nbt.getCompound("ItemStack"));
        onLoad();
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if ((!itemStack.isEmpty() && itemStack.getItem() == stack.getItem()) || itemStack.isEmpty()) {
            return super.insertItem(slot, stack, simulate);
        }

        return stack;
    }

    @Override
    public int getStackLimit(int slot, @NotNull ItemStack stack) {
        if (slot == STORAGE) {
            return Integer.MAX_VALUE;
        }

        return super.getStackLimit(slot, stack);
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
