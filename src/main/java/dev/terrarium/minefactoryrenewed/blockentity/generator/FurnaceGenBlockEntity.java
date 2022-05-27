package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public class FurnaceGenBlockEntity extends BurnableGenBlockEntity {

    public FurnaceGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FURNACE_GENERATOR.get(), pos, state, 100000, 40, 100);
    }

    @Override
    public void burnItem(ItemStack stack) {
        setBurnTime(ForgeHooks.getBurnTime(stack, null) / 2);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null) > 0;
    }
}
