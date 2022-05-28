package dev.terrarium.minefactoryrenewed.blockentity.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.processing.LaserChargerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LaserChargerBlockEntity extends MachineBlockEntity implements MenuProvider {

    private BlockPos targetPos;

    public LaserChargerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LASER_CHARGER.get(), pos, state);
        this.createEnergy(100000, 5000);
        this.setMaxIdleTime(1);
        this.setMaxWorkTime(2);

        Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
        updateTarget(direction);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (targetPos != null)
            tag.put("targetPos", NbtUtils.writeBlockPos(targetPos));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("targetPos"))
            targetPos = NbtUtils.readBlockPos(tag.getCompound("targetPos"));
    }

    @Override
    public boolean run() {
        if (level == null || targetPos == null) return false;
        if (level.getBlockEntity(targetPos) instanceof LaserDrillBlockEntity laserDrill) {
            laserDrill.getEnergy().receiveEnergy(getEnergyCost(), false);
            useEnergy();
            return true;
        }

        return false;
    }

    public void updateTarget(Direction direction) {
        targetPos = getBlockPos().relative(direction, 2);
        setChanged();
    }

    public boolean isTargetValid() {
        return level != null && targetPos != null && level.getBlockEntity(targetPos) instanceof LaserDrillBlockEntity;
    }

    public BlockPos getTargetPos() {
        return targetPos;
    }

    @Override
    public boolean hasCustomRenderer() {
        return true;
    }

    @Override
    public AABB getRenderBoundingBox() {
        if (getTargetPos() != null) {
            return new AABB(getBlockPos(), targetPos).inflate(1);
        }

        return super.getRenderBoundingBox();
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new LaserChargerContainer(id, inventory, this);
    }
}
