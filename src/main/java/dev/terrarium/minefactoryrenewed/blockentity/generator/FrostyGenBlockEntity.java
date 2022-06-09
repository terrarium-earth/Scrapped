package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.data.generator.GeneratorItemManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class FrostyGenBlockEntity extends BurnableGenBlockEntity {

    public FrostyGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FROSTY_GENERATOR.get(), pos, state, 100000, 40, 100);
    }

    @Override
    public void burnItem(ItemStack stack) {
        setEnergyGen(GeneratorItemManager.getFrosty().getEnergyGen(stack));
        setBurnTime(GeneratorItemManager.getFrosty().getBurnTime(stack));
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GeneratorItemManager.getFrosty().isValid(stack);
    }
}
