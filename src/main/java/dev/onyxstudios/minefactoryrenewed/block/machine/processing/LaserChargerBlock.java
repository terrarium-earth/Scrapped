package dev.onyxstudios.minefactoryrenewed.block.machine.processing;

import dev.onyxstudios.minefactoryrenewed.block.machine.RotatableMachineBlock;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.processing.LaserChargerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LaserChargerBlock extends RotatableMachineBlock {

    public LaserChargerBlock() {
        super();
    }

    @Override
    public void onMachineRotated(Level level, BlockPos pos, Direction rotatedTo) {
        super.onMachineRotated(level, pos, rotatedTo);
        if (level.getBlockEntity(pos) instanceof LaserChargerBlockEntity laserCharger) {
            laserCharger.updateTarget(rotatedTo);
        }
    }

    @Override
    public BlockEntityType<? extends MachineBlockEntity> getBlockEntityType() {
        return ModBlockEntities.LASER_CHARGER.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LaserChargerBlockEntity(pos, state);
    }
}
