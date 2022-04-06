package dev.onyxstudios.minefactoryrenewed.block.machine.mobs;

import dev.onyxstudios.minefactoryrenewed.block.machine.RotatableMachineBlock;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.MobCounterBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class MobCounterBlock extends RotatableMachineBlock {

    public MobCounterBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f));
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return getDirectSignal(state, level, pos, direction);
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof MobCounterBlockEntity mobCounter) {
            return mobCounter.getMobCount();
        }

        return super.getDirectSignal(state, level, pos, direction);
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, getBlockEntityType(), MobCounterBlockEntity::customTick);
    }

    @Override
    public BlockEntityType<? extends MobCounterBlockEntity> getBlockEntityType() {
        return ModBlockEntities.MOB_COUNTER_BLOCK_ENTITY.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MobCounterBlockEntity(pos, state);
    }
}
