package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.blockentity.container.generator.BurnableGenContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BurnableGenBlockEntity extends GeneratorBlockEntity {

    private int burnTime;
    private int maxBurnTime;

    public BurnableGenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int energyCapacity, int energyGen, int maxTransfer) {
        super(type, pos, state, energyCapacity, energyGen, maxTransfer);
        this.createInventory(new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BurnableGenBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return BurnableGenBlockEntity.this.isItemValid(stack);
            }
        });
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("burnTime", burnTime);
        tag.putInt("maxBurnTime", maxBurnTime);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        burnTime = tag.getInt("burnTime");
        maxBurnTime = tag.getInt("maxBurnTime");
    }

    @Override
    protected void tick() {
        if (burnTime > 0) {
            burnTime--;

            onBurn();

        } else {
            burnTime = 0;
            maxBurnTime = 0;

            ItemStack stack = getInventory().getStackInSlot(0);
            if (canGenerate() && !stack.isEmpty() && isItemValid(stack)) {
                burnItem(stack);
                if (stack.hasContainerItem() && stack.getCount() == 1) {
                    getInventory().setStackInSlot(0, stack.getContainerItem());
                } else {
                    stack.shrink(1);
                }
            }
        }
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
        this.maxBurnTime = burnTime;
    }

    @Override
    public Component getDisplayText() {
        return new TranslatableComponent("tooltip.generator." + (burnTime > 0 && canGenerate() ? "generating" : "idle"), String.valueOf(getEnergyGen()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new BurnableGenContainer(id, inventory, this);
    }

    /**
     * Runs every time an item is burned. Do not shrink the stack here
     * @param stack item that is burned only if it is valid and the machine can generate energy
     */
    public abstract void burnItem(ItemStack stack);

    /**
     * Is called everytime the generator ticks to produce energy
     */
    public void onBurn() {
        if (canGenerate()) generateEnergy();
    }

    public abstract boolean isItemValid(ItemStack stack);

}
