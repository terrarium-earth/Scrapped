package dev.onyxstudios.minefactoryrenewed.block.rubber;

import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RubberTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean largeHive) {
        return ModBlocks.RUBBER_TREE_FEATURE.getHolder().get();
    }
}
