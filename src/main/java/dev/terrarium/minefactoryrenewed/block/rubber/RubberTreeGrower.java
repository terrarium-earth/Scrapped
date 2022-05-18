package dev.terrarium.minefactoryrenewed.block.rubber;

import dev.terrarium.minefactoryrenewed.registry.ModWorldGen;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RubberTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull Random random, boolean largeHive) {
        return ModWorldGen.RUBBER_TREE_FEATURE.getHolder().orElseThrow();
    }
}
