package dev.terrarium.minefactoryrenewed.blockentity.power;

import dev.terrarium.minefactoryrenewed.api.energy.MFREnergyStorage;
import dev.terrarium.minefactoryrenewed.api.energy.StrictEnergyStorage;
import dev.terrarium.minefactoryrenewed.api.machine.MachineConfigType;
import dev.terrarium.minefactoryrenewed.block.power.EnergyCellBlock;
import dev.terrarium.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.container.power.EnergyCellContainer;
import dev.terrarium.minefactoryrenewed.client.model.data.ModelDataTypes;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EnergyCellBlockEntity extends BaseBlockEntity implements MenuProvider {

    private final Map<Direction, MachineConfigType> config = new HashMap<>();

    private final MFREnergyStorage energyStorage;
    private final LazyOptional<MFREnergyStorage> handler;

    private final StrictEnergyStorage inputStorage;
    private final LazyOptional<StrictEnergyStorage> inputHandler;

    private final StrictEnergyStorage outputStorage;
    private final LazyOptional<StrictEnergyStorage> outputHandler;

    private final StrictEnergyStorage displayStorage;
    private final LazyOptional<StrictEnergyStorage> displayHandler;

    private int maxReceive;
    private int maxExtract;

    public EnergyCellBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, (EnergyCellBlock) ModBlocks.BASIC_ENERGY_CELL.get());
    }

    public EnergyCellBlockEntity(BlockPos pos, BlockState state, EnergyCellBlock energyCell) {
        super(ModBlockEntities.ENERGY_CELL.get(), pos, state);
        this.energyStorage = new MFREnergyStorage(energyCell.getCapacity(), energyCell.getMaxTransfer(), energyCell.getMaxTransfer());
        this.handler = LazyOptional.of(() -> energyStorage);

        this.inputStorage = new StrictEnergyStorage(energyStorage, energyCell.getMaxTransfer(), 0);
        this.inputHandler = LazyOptional.of(() -> inputStorage);

        this.outputStorage = new StrictEnergyStorage(energyStorage, 0, energyCell.getMaxTransfer());
        this.outputHandler = LazyOptional.of(() -> outputStorage);

        this.displayStorage = new StrictEnergyStorage(energyStorage, 0, 0);
        this.displayHandler = LazyOptional.of(() -> displayStorage);

        this.maxExtract = energyCell.getMaxTransfer();
        this.maxReceive = energyCell.getMaxTransfer();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("maxReceive", maxReceive);
        tag.putInt("maxExtract", maxExtract);
        tag.put("energy", energyStorage.serializeNBT());

        CompoundTag machineConfig = new CompoundTag();
        for (Direction direction : InventoryUtils.VALUES) {
            MachineConfigType type = config.getOrDefault(direction, MachineConfigType.NONE);
            machineConfig.putInt(direction.getSerializedName(), type.ordinal());
        }

        tag.put("machineConfig", machineConfig);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        maxReceive = tag.getInt("maxReceive");
        maxExtract = tag.getInt("maxExtract");
        energyStorage.deserializeNBT(tag.getCompound("energy"));
        inputStorage.setMaxReceive(maxReceive);
        outputStorage.setMaxExtract(maxExtract);

        CompoundTag machineConfig = tag.getCompound("machineConfig");
        for (Direction direction : InventoryUtils.VALUES) {
            int ordinal = machineConfig.getInt(direction.getSerializedName());
            config.put(direction, MachineConfigType.values()[ordinal]);
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EnergyCellBlockEntity blockEntity) {
        blockEntity.tick();
    }

    public void tick() {
        transferEnergy();
    }

    public void transferEnergy() {
        if (level == null) return;

        for (Direction direction : InventoryUtils.VALUES) {
            if (!canExtract(direction)) continue;

            BlockPos offset = getBlockPos().relative(direction);
            BlockEntity blockEntity = level.getBlockEntity(offset);
            if (blockEntity == null) continue;

            blockEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(neighborEnergy -> {
                int maxExtract = outputStorage.extractEnergy(outputStorage.getMaxExtract(), true);
                int extracted = neighborEnergy.receiveEnergy(maxExtract, false);
                outputStorage.extractEnergy(extracted, false);
            });
        }
    }

    public void cycleConfig(Direction direction) {
        if (level == null) return;

        MachineConfigType configType = getSideConfig(direction);
        switch (configType) {
            case NONE -> config.put(direction, MachineConfigType.INPUT);
            case INPUT -> config.put(direction, MachineConfigType.EXTRACT);
            case EXTRACT -> config.put(direction, MachineConfigType.INPUT_EXTRACT);
            case INPUT_EXTRACT -> config.put(direction, MachineConfigType.NONE);
        }

        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        level.updateNeighborsAt(getBlockPos(), getBlockState().getBlock());
        requestModelDataUpdate();
    }

    public boolean canInput(Direction direction) {
        MachineConfigType configType = getSideConfig(direction);
        return configType == MachineConfigType.INPUT || configType == MachineConfigType.INPUT_EXTRACT;
    }

    public boolean canExtract(Direction direction) {
        MachineConfigType configType = getSideConfig(direction);
        return configType == MachineConfigType.EXTRACT || configType == MachineConfigType.INPUT_EXTRACT;
    }

    public MachineConfigType getSideConfig(Direction direction) {
        return config.getOrDefault(direction, MachineConfigType.NONE);
    }

    public void addMaxReceive(int amount) {
        setMaxReceive(maxReceive + amount);
    }

    public void addMaxExtract(int amount) {
        setMaxExtract(maxExtract + amount);
    }

    public void setMaxReceive(int maxReceive) {
        EnergyCellBlock energyCell = (EnergyCellBlock) this.getBlockState().getBlock();
        this.maxReceive = Math.max(Math.min(energyCell.getMaxTransfer(), maxReceive), 0);
        this.inputStorage.setMaxReceive(this.maxReceive);
    }

    public void setMaxExtract(int maxExtract) {
        EnergyCellBlock energyCell = (EnergyCellBlock) this.getBlockState().getBlock();
        this.maxExtract = Math.max(Math.min(energyCell.getMaxTransfer(), maxExtract), 0);
        this.outputStorage.setMaxExtract(this.maxExtract);
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new EnergyCellContainer(id, inventory, this);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            MachineConfigType configType = getSideConfig(side == null ? Direction.UP : side);
            return switch (configType) {
                case NONE -> displayHandler.cast();
                case INPUT -> inputHandler.cast();
                case EXTRACT -> outputHandler.cast();
                case INPUT_EXTRACT -> handler.cast();
            };
        }

        return super.getCapability(cap, side);
    }

    @NotNull
    @Override
    public IModelData getModelData() {
        MachineConfigType[] types = new MachineConfigType[InventoryUtils.VALUES.length];
        for (Direction direction : InventoryUtils.VALUES) {
            types[direction.ordinal()] = getSideConfig(direction);
        }

        return new ModelDataMap.Builder().withInitial(ModelDataTypes.MACHINE_CONFIG_PROPERTY, types).build();
    }
}
