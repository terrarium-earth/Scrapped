package dev.terrarium.minefactoryrenewed.blockentity.machine.blocks;

import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BlockBreakerBlockEntity extends MachineBlockEntity {

    public BlockBreakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLOCK_BREAKER.get(), pos, state);
        this.createEnergy(32000, 960);
        this.setMaxWorkTime(2);
        this.setMaxIdleTime(20);

        this.createMachineArea(pos, Direction.NORTH, 0);
        this.getMachineArea().setOneBlock(true);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        Direction direction = getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        BlockPos pos = getBlockPos().relative(direction);
        if (!level.isEmptyBlock(pos) && level.getBlockEntity(pos) == null) {
            BlockState state = level.getBlockState(pos);

            if (Items.IRON_PICKAXE.isCorrectToolForDrops(state) || Items.IRON_AXE.isCorrectToolForDrops(state) ||
                    Items.IRON_SHOVEL.isCorrectToolForDrops(state) || Items.AIR.isCorrectToolForDrops(state)) {
                insertOrDropItems(List.of(new ItemStack(state.getBlock())));
                level.destroyBlock(pos, false);
                useEnergy();
                return true;
            }
        }

        return false;
    }
}
