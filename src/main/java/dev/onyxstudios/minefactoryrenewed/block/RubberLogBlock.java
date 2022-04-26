package dev.onyxstudios.minefactoryrenewed.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class RubberLogBlock extends RotatedPillarBlock {

    private final Supplier<Block> strippedVariant;

    public RubberLogBlock(Supplier<Block> strippedVariant) {
        super(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD));
        this.strippedVariant = strippedVariant;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, Level level, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
        if (state.getBlock() == this && ToolActions.AXE_STRIP.equals(toolAction)) {
            return strippedVariant.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
        }

        return super.getToolModifiedState(state, level, pos, player, stack, toolAction);
    }
}
