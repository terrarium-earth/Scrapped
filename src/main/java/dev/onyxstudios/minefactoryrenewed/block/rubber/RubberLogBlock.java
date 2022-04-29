package dev.onyxstudios.minefactoryrenewed.block.rubber;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class RubberLogBlock extends RotatedPillarBlock {

    private final Supplier<Block> strippedVariant;

    public RubberLogBlock(Supplier<Block> strippedVariant) {
        super(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD));
        this.strippedVariant = strippedVariant;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (state.getBlock() == this && !simulate && ToolActions.AXE_STRIP.equals(toolAction)) {
            BlockState result = strippedVariant.get().defaultBlockState();
            if (result.getBlock() instanceof RotatedPillarBlock) {
                result.setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }

            return result;
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
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
}
