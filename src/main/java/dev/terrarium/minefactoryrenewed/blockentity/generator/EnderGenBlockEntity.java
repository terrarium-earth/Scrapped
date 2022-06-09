package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.data.generator.GeneratorItemManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class EnderGenBlockEntity extends BurnableGenBlockEntity {

    public EnderGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ENDER_GENERATOR.get(), pos, state, 100000, 40, 100);
    }

    @Override
    public void burnItem(ItemStack stack) {
        setEnergyGen(GeneratorItemManager.getEnder().getEnergyGen(stack));
        setBurnTime(GeneratorItemManager.getEnder().getBurnTime(stack));
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GeneratorItemManager.getEnder().isValid(stack);
    }
}
