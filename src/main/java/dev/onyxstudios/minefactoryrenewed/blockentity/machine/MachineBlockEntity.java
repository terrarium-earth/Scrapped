package dev.onyxstudios.minefactoryrenewed.blockentity.machine;

import dev.onyxstudios.minefactoryrenewed.api.energy.MFREnergyStorage;
import dev.onyxstudios.minefactoryrenewed.blockentity.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MachineBlockEntity extends BaseBlockEntity {

    private final MFREnergyStorage energy;
    private final LazyOptional<MFREnergyStorage> energyHandler;

    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> inventoryHandler;

    private final int maxWorkTime;
    private final int maxIdleTime;

    private boolean isIdle = false;
    private int workTime;
    private int idleTime;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int workTime, int idleTime, int capacity) {
        super(type, pos, state);
        energy = new MFREnergyStorage(capacity);
        energyHandler = LazyOptional.of(() -> energy);

        inventory = null;
        inventoryHandler = LazyOptional.empty();

        this.maxWorkTime = workTime;
        this.maxIdleTime = idleTime;
    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int workTime, int idleTime, int capacity, int slots) {
        super(type, pos, state);
        energy = new MFREnergyStorage(capacity);
        energyHandler = LazyOptional.of(() -> energy);
        inventory = new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                MachineBlockEntity.this.setChanged();
            }
        };
        inventoryHandler = LazyOptional.of(() -> inventory);

        this.maxWorkTime = workTime;
        this.maxIdleTime = idleTime;
    }

    public abstract boolean run();

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("energy", energy.serializeNBT());
        if (inventory != null)
            tag.put("inventory", inventory.serializeNBT());

        tag.putBoolean("isIdle", isIdle);
        tag.putInt("workTime", workTime);
        tag.putInt("idleTime", idleTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("energy"))
            energy.deserializeNBT(tag.getCompound("energy"));

        if (tag.contains("inventory"))
            inventory.deserializeNBT(tag.getCompound("inventory"));

        isIdle = tag.getBoolean("isIdle");
        workTime = tag.getInt("workTime");
        idleTime = tag.getInt("idleTime");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        blockEntity.tickInternal();
    }

    private void tickInternal() {
        if (isIdle) {
            idleTime++;

            if (idleTime >= maxIdleTime) {
                idleTime = 0;
                isIdle = false;
            }
            
            setChanged();
        } else {
            workTime++;
            if (workTime >= maxWorkTime) {
                workTime = 0;

                if (!run())
                    setIdle();
            }

            setChanged();
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY)
            return energyHandler.cast();

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != null)
            return inventoryHandler.cast();

        return super.getCapability(cap, side);
    }

    public void setIdle() {
        if (level == null) return;

        isIdle = true;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public MFREnergyStorage getEnergy() {
        return energy;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getMaxWorkTime() {
        return maxWorkTime;
    }
}
