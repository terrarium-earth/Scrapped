package dev.onyxstudios.minefactoryrenewed.block.machine.processing;

import dev.onyxstudios.minefactoryrenewed.block.machine.MachineBlock;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.processing.SludgeBoilerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SludgeBoilerBlock extends MachineBlock {

    public SludgeBoilerBlock() {
        super();
    }

    @Override
    public BlockEntityType<? extends MachineBlockEntity> getBlockEntityType() {
        return ModBlockEntities.SLUDGE_BOILER.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SludgeBoilerBlockEntity(pos, state);
    }
}
