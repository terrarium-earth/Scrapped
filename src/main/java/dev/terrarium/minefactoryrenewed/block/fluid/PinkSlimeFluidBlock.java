package dev.terrarium.minefactoryrenewed.block.fluid;

import dev.terrarium.minefactoryrenewed.entity.PinkSlimeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.Random;
import java.util.function.Supplier;

public class PinkSlimeFluidBlock extends BaseFluidBlock {

    public PinkSlimeFluidBlock(Supplier<FlowingFluid> fluidSupplier, Properties properties) {
        super(fluidSupplier, properties.randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        if (!level.isClientSide() && getFluidState(state).isSource()) {
            PinkSlimeEntity slime = new PinkSlimeEntity(level, 1);
            slime.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5f);
            level.addFreshEntity(slime);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }
}
