package dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Predicate;

public class MobCounterBlockEntity extends MachineBlockEntity {

    protected static final Predicate<Entity> ALL_ENTITY_PREDICATE = (entity) -> (entity.isAlive() &&
            entity instanceof LivingEntity &&
            entity.canChangeDimensions()
    );

    private int currentCount;

    public MobCounterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOB_COUNTER_BLOCK_ENTITY.get(), pos, state, false);
        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setUpgradeRadius(1);
        this.getMachineArea().setVerticalRange(2);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("currentEntityCount", currentCount);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        currentCount = tag.getInt("currentEntityCount");
    }

    public static void customTick(Level level, BlockPos pos, BlockState state, MobCounterBlockEntity blockEntity) {
        blockEntity.tick();
    }

    private void tick() {
        if (level == null) return;

        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ALL_ENTITY_PREDICATE);
        if (entities.size() != currentCount) {
            currentCount = entities.size();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            level.updateNeighborsAt(getBlockPos(), this.getBlockState().getBlock());
        }
    }

    @Override
    public boolean run() {
        return false;
    }

    public int getMobCount() {
        return currentCount;
    }
}
