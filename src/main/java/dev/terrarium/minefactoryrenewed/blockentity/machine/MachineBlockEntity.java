package dev.terrarium.minefactoryrenewed.blockentity.machine;

import dev.terrarium.minefactoryrenewed.api.energy.MFREnergyStorage;
import dev.terrarium.minefactoryrenewed.api.machine.MachineArea;
import dev.terrarium.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.terrarium.minefactoryrenewed.item.MachineUpgradeItem;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
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
    public static final Predicate<Entity> ALL_ENTITY_PREDICATE = (entity) -> (entity.isAlive() &&
            entity instanceof LivingEntity &&
            entity.canChangeDimensions()
    );
    protected static final Predicate<Entity> ANIMAL_PREDICATE = (entity) -> (entity.isAlive() &&
            entity instanceof Animal &&
            entity.canChangeDimensions()
    );

    //Cache directions array
    protected static final Direction[] DIRECTIONS = Direction.values();

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
        //if (blockEntity.getEnergy() != null && !state.is(ModBlocks.LASER_DRILL.get()) &&
        //        !state.is(ModBlocks.STEAM_TURBINE.get()))
        //    blockEntity.getEnergy().receiveEnergy(100, false);
        blockEntity.tick();
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
        if (level == null || !canRun() || !hasEnoughPower())
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
            tickWork();
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

    //Tick normally
    protected void tick() {
    }

    //Tick while working
    protected void tickWork() {
    }

    public void useEnergy() {
        useEnergy(energyCost);
    }

    public void useEnergy(int cost) {
        if (level != null && energy != null) {
            energy.extractEnergy(cost, false);
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    public boolean canRun() {
        return level != null && !level.hasNeighborSignal(getBlockPos()) && shouldTick;
    }

    public boolean hasEnoughPower() {
        return hasEnoughPower(energyCost);
    }

    public boolean hasEnoughPower(int cost) {
        return (energy == null || energy.getEnergyStored() >= cost);
    }

    public void setIdle() {
        if (level == null) return;

        isIdle = true;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public void transferEnergy(int maxTransfer) {
        if (level == null) return;

        for (Direction direction : DIRECTIONS) {
            BlockPos offset = getBlockPos().relative(direction);
            BlockEntity blockEntity = level.getBlockEntity(offset);
            if (blockEntity == null) continue;

            blockEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(energyStorage -> {
                int maxExtract = getEnergy().extractEnergy(maxTransfer, true);
                int extracted = energyStorage.receiveEnergy(maxExtract, false);
                getEnergy().extractEnergy(extracted, false);
            });
        }
    }

    public void transferFluid(int maxTransfer, FluidTank tank) {
        if (level == null) return;

        for (Direction direction : DIRECTIONS) {
            BlockPos offset = getBlockPos().relative(direction);
            BlockEntity blockEntity = level.getBlockEntity(offset);
            if (blockEntity == null) continue;

            blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite()).ifPresent(fluidHandler -> {
                FluidStack maxExtract = tank.drain(maxTransfer, IFluidHandler.FluidAction.SIMULATE);
                int extracted = fluidHandler.fill(maxExtract, IFluidHandler.FluidAction.EXECUTE);
                tank.drain(extracted, IFluidHandler.FluidAction.EXECUTE);
            });
        }
    }

    protected void updateRange() {
        ItemStack stack = inventory.getStackInSlot(0);
        int radius = 0;
        if (!stack.isEmpty()) {
            radius = ((MachineUpgradeItem) stack.getItem()).getRadiusIncrease();
        }

        machineArea.setUpgradeRadius(radius);
        this.setChanged();
    }

    public void createMachineArea(BlockPos pos, Direction facing) {
        this.createMachineArea(pos, facing, 1);
    }

    public void createMachineArea(BlockPos pos, Direction facing, int radius) {
        MachineArea machineArea = new MachineArea(pos, facing, radius);
        machineArea.calculateArea();
        createMachineArea(machineArea);
    }

    public void createMachineArea(MachineArea machineArea) {
        this.machineArea = machineArea;
        this.setChanged();
    }

    public void createEnergy(int capacity, int energyCost) {
        energy = new MFREnergyStorage(capacity, Integer.MAX_VALUE, Integer.MAX_VALUE);
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
        ItemStackHandler itemStackHandler = new ItemStackHandler(slots + (upgradeSlot ? 1 : 0)) {
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
        this.createInventory(itemStackHandler);
    }

    public void createInventory(ItemStackHandler inventory) {
        this.inventory = inventory;
        this.inventoryHandler = LazyOptional.of(() -> inventory);
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

    public int getEnergyCost() {
        return energyCost;
    }

    public boolean hasCustomRenderer() {
        return false;
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
