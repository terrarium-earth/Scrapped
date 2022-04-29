package dev.onyxstudios.minefactoryrenewed.block.rubber;

import dev.onyxstudios.minefactoryrenewed.blockentity.RubberSignBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RubberStandingSignBlock extends StandingSignBlock {

    public RubberStandingSignBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModBlocks.RUBBER_TYPE);
    }

    @NotNull
    @Override
    public List<ItemStack> getDrops(@NotNull BlockState state, LootContext.@NotNull Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        if (drops.isEmpty()) {
            drops.add(new ItemStack(this));
        }

        return drops;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RubberSignBlockEntity(pos, state);
    }
}
