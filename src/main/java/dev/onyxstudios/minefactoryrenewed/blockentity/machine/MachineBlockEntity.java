package dev.onyxstudios.minefactoryrenewed.blockentity.machine;

import dev.onyxstudios.minefactoryrenewed.api.energy.MFREnergyStorage;
import dev.onyxstudios.minefactoryrenewed.api.machine.MachineArea;
import dev.onyxstudios.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.onyxstudios.minefactoryrenewed.item.MachineUpgradeItem;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public abstract class MachineBlockEntity extends BaseBlockEntity {

    public static final DamageSource NO_DROPS = new DamageSource("nodrops").bypassArmor().setMagic();
    protected static final Predicate<Entity> ENTITY_PREDICATE = (entity) -> (entity.isAlive() &&
            entity instanceof LivingEntity &&
            !(entity instanceof Player) &&
            !((LivingEntity) entity).isBaby() &&
            entity.canChangeDimensions()
    );

    private MFREnergyStorage energy;
    private LazyOptional<MFREnergyStorage> energyHandler;

    private ItemStackHandler inventory;
    private LazyOptional<IItemHandler> inventoryHandler;

    private FluidTank tank;
    private LazyOptional<FluidTank> tankHandler;

    private MachineArea machineArea;
    private int energyCost = 0;

    private final boolean shouldTick;

    private int maxWorkTime;
    private int maxIdleTime;

    private boolean isIdle = false;
    private int workTime;
    private int idleTime;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, true);
    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, boolean tick) {
        super(type, pos, state);
        this.shouldTick = tick;
    }

    public abstract boolean run();

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (energy != null)
            tag.put("energy", energy.serializeNBT());

        if (inventory != null)
            tag.put("inventory", inventory.serializeNBT());

        if (tank != null)
            tag.put("fluid", tank.writeToNBT(new CompoundTag()));

        if (machineArea != null)
            tag.put("machineArea", machineArea.save());

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

        if (tag.contains("fluid"))
            tank.readFromNBT(tag.getCompound("fluid"));

        if (tag.contains("machineArea"))
            machineArea.load(tag.getCompound("machineArea"));

        isIdle = tag.getBoolean("isIdle");
        workTime = tag.getInt("workTime");
        idleTime = tag.getInt("idleTime");
        energyCost = tag.getInt("energyCost");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        //TODO Remove, only for testing with no generators...
        blockEntity.getEnergy().receiveEnergy(100, false);
        blockEntity.tickInternal();
    }

    public static void tickClient(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        tick(level, pos, state, blockEntity);
    }

    public static void livingDropsEvent(LivingDropsEvent event) {
        if (event.getSource() == NO_DROPS)
            event.setCanceled(true);
    }

    private void tickInternal() {
        if (!canRun())
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

                if (!level.isClientSide() && !run()) {
                    setIdle();
                }

                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            }

            setChanged();
        }
    }

    public void useEnergy() {
        if (energy != null) {
            energy.extractEnergy(energyCost, false);
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    public boolean canRun() {
        return (level != null && !level.hasNeighborSignal(getBlockPos()) && shouldTick) &&
                (energy == null || energy.getEnergyStored() >= energyCost);
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
        this.setChanged();
    }

    public void createMachineArea(BlockPos pos, Direction facing) {
        this.machineArea = new MachineArea(pos, facing, 1);
        this.machineArea.calculateArea();
        this.setChanged();
    }

    public void createEnergy(int capacity, int energyCost) {
        energy = new MFREnergyStorage(capacity);
        energyHandler = LazyOptional.of(() -> energy);
        this.energyCost = energyCost;
        this.setChanged();
    }

    public void createInventory() {
        this.createInventory(0, true);
    }

    public void createInventory(int slots) {
        this.createInventory(slots, true);
    }

    public void createInventory(int slots, boolean upgradeSlot) {
        inventory = new ItemStackHandler(slots + (upgradeSlot ? 1 : 0)) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                //Upgrade slot
                if (slot == 0 && upgradeSlot) {
                    MachineBlockEntity.this.updateRange();
                }

                MachineBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return slot == 0 && upgradeSlot ? stack.getItem() instanceof MachineUpgradeItem :
                        super.isItemValid(slot, stack);
            }
        };
        inventoryHandler = LazyOptional.of(() -> inventory);
        this.setChanged();
    }

    public void createFluid(int capacity, FluidStack filter) {
        tank = new FluidTank(capacity, filter::isFluidEqual);
        tankHandler = LazyOptional.of(() -> tank);
        this.setChanged();
    }

    public void createFluid(int capacity) {
        tank = new FluidTank(capacity);
        tankHandler = LazyOptional.of(() -> tank);
        this.setChanged();
    }

    protected void insertOrDropItems(List<ItemStack> drops) {
        LazyOptional<IItemHandler> optional = InventoryUtils.getNearbyInventory(level, getBlockPos());

        if (!optional.isPresent()) {
            drops.forEach(this::dropItem);
            return;
        }

        optional.ifPresent(inventory -> drops.forEach(stack -> {
            ItemStack remaining = InventoryUtils.tryInsertItem(level, inventory, stack);

            if (!remaining.isEmpty())
                dropItem(remaining);
        }));
    }

    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(level, getBlockPos().getX() + 0.5,
                getBlockPos().getY() + 1.25, getBlockPos().getZ() + 0.5, stack);
        level.addFreshEntity(itemEntity);
    }

    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
        this.setChanged();
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

    public FluidTank getTank() {
        return tank;
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

        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && tank != null)
            return tankHandler.cast();

        return super.getCapability(cap, side);
    }
}
