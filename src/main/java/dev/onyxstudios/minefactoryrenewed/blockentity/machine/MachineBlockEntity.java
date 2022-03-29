package dev.onyxstudios.minefactoryrenewed.blockentity.machine;

import dev.onyxstudios.minefactoryrenewed.api.energy.MFREnergyStorage;
import dev.onyxstudios.minefactoryrenewed.api.machine.MachineArea;
import dev.onyxstudios.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.onyxstudios.minefactoryrenewed.item.MachineUpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
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

    private MFREnergyStorage energy;
    private LazyOptional<MFREnergyStorage> energyHandler;

    private ItemStackHandler inventory;
    private LazyOptional<IItemHandler> inventoryHandler;

    private MachineArea machineArea;
    private int energyCost = 0;

    private int maxWorkTime;
    private int maxIdleTime;

    private boolean isIdle = false;
    private int workTime;
    private int idleTime;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract boolean run();

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (energy != null)
            tag.put("energy", energy.serializeNBT());

        if (inventory != null)
            tag.put("inventory", inventory.serializeNBT());

        tag.putBoolean("isIdle", isIdle);
        tag.putInt("workTime", workTime);
        tag.putInt("idleTime", idleTime);
        tag.putInt("energyCost", energyCost);
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
        energyCost = tag.getInt("energyCost");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        blockEntity.tickInternal();
    }

    private void tickInternal() {
        if (energy != null && energy.getEnergyStored() < energyCost)
            return;

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

                if (run() && energy != null) {
                    energy.extractEnergy(energyCost, false);
                } else {
                    setIdle();
                }
            }

            setChanged();
        }

        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public void setIdle() {
        if (level == null) return;

        isIdle = true;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private void updateRange() {
        ItemStack stack = inventory.getStackInSlot(0);
        int radius = 0;
        if (!stack.isEmpty()) {
            radius = ((MachineUpgradeItem) stack.getItem()).getRadiusIncrease();
        }

        machineArea.setUpgradeRadius(radius);
    }

    public void createMachineArea(BlockPos pos, Direction facing) {
        this.machineArea = new MachineArea(pos, facing, 1);
        this.machineArea.calculateArea();
    }

    public void createEnergy(int capacity, int energyCost) {
        energy = new MFREnergyStorage(capacity);
        energyHandler = LazyOptional.of(() -> energy);
        this.energyCost = energyCost;
        this.setChanged();
    }

    public void createInventory(int slots) {
        //Add one for upgrade slot which is always index = 0
        inventory = new ItemStackHandler(slots + 1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                //Upgrade slot
                if (slot == 0) {
                    MachineBlockEntity.this.updateRange();
                }

                MachineBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return slot == 0 ? stack.getItem() instanceof MachineUpgradeItem : super.isItemValid(slot, stack);
            }
        };
        inventoryHandler = LazyOptional.of(() -> inventory);
        this.setChanged();
    }

    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
        setChanged();
    }

    public void setMaxWorkTime(int maxWorkTime) {
        this.maxWorkTime = maxWorkTime;
        this.setChanged();
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
        this.setChanged();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public MFREnergyStorage getEnergy() {
        return energy;
    }

    public MachineArea getMachineArea() {
        return machineArea;
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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY && energy != null)
            return energyHandler.cast();

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != null)
            return inventoryHandler.cast();

        return super.getCapability(cap, side);
    }
}
