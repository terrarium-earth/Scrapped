package dev.onyxstudios.minefactoryrenewed.block.machine;

import dev.onyxstudios.minefactoryrenewed.api.machine.IRotatableMachine;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public abstract class RotatableMachineBlock extends MachineBlock implements IRotatableMachine {

    public RotatableMachineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH));
    }

    @Override
    public void onMachineRotated(Level level, BlockPos pos, Direction rotatedTo) {
        if (level.getBlockEntity(pos) instanceof MachineBlockEntity machine && machine.getMachineArea() != null) {
            machine.getMachineArea().setFacing(rotatedTo);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        onMachineRotated(level, pos, state.getValue(HorizontalDirectionalBlock.FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,
                context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING,
                rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }
}
