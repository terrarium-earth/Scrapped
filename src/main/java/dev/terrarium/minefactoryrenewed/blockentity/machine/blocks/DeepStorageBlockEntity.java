package dev.terrarium.minefactoryrenewed.blockentity.machine.blocks;

import dev.terrarium.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.blocks.DeepStorageContainer;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.util.DeepStorageInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static dev.terrarium.minefactoryrenewed.util.DeepStorageInventory.*;

public class DeepStorageBlockEntity extends BaseBlockEntity implements MenuProvider {

    private final DeepStorageInventory inventory = new DeepStorageInventory() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            DeepStorageBlockEntity.this.setChanged();
        }
    };
    private final LazyOptional<ItemStackHandler> inventoryHandler = LazyOptional.of(() -> inventory);

    public DeepStorageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEEP_STORAGE.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("inventory"));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DeepStorageBlockEntity blockEntity) {
        blockEntity.tick();
    }

    private void tick() {
        if (level == null) return;
        ItemStack inputStack = inventory.getStackInSlot(INPUT);
        ItemStack outputStack = inventory.getStackInSlot(OUTPUT);
        ItemStack storageStack = inventory.getStackInSlot(STORAGE);

        boolean changed = false;
        if (!inputStack.isEmpty()) {
            if (storageStack.isEmpty() && (outputStack.isEmpty() || areEqual(outputStack, inputStack, true))) {
                storageStack = inputStack.copy();
                inputStack = ItemStack.EMPTY;
                inventory.setStackInSlot(STORAGE, storageStack);
                inventory.setStackInSlot(INPUT, inputStack);
                inventory.setItemStack(storageStack);
                changed = true;
            } else if (!storageStack.isEmpty() && areEqual(storageStack, inputStack, true)) {
                storageStack.grow(inputStack.getCount());
                inputStack = ItemStack.EMPTY;
                inventory.setStackInSlot(STORAGE, storageStack);
                inventory.setStackInSlot(INPUT, inputStack);
                changed = true;
            }
        }

        if (!storageStack.isEmpty()) {
            int itemSize = storageStack.getMaxStackSize();

            if (outputStack.isEmpty()) {
                int itemCount = Math.min(itemSize, storageStack.getCount());
                inventory.setStackInSlot(OUTPUT, new ItemStack(storageStack.getItem(), itemCount));
                inventory.getStackInSlot(STORAGE).shrink(itemCount);
                changed = true;
            } else if (outputStack.getCount() < itemSize) {
                inventory.getStackInSlot(OUTPUT).grow(1);
                inventory.getStackInSlot(STORAGE).shrink(1);
                changed = true;
            }
        }

        if (changed)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private boolean areEqual(ItemStack stack, ItemStack stack2, boolean matchNbt) {
        if (stack.isEmpty() || stack2.isEmpty()) return false;
        else if (stack.getItem() != stack2.getItem()) return false;
        else return !matchNbt || stack.areShareTagsEqual(stack2);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return inventoryHandler.cast();

        return super.getCapability(cap, side);
    }

    public DeepStorageInventory getInventory() {
        return inventory;
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new DeepStorageContainer(id, inventory, this);
    }
}
