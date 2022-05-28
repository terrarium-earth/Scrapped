package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.data.generator.PotionManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class PotionGenBlockEntity extends BurnableGenBlockEntity {

    public PotionGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POTION_GENERATOR.get(), pos, state, 100000, 40, 100);
    }

    @Override
    public void burnItem(ItemStack stack) {
        setEnergyGen(PotionManager.getInstance().getEnergyGen(stack));
        setBurnTime(PotionManager.getInstance().getBurnTime(stack));
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return PotionManager.getInstance().isPotionBurnable(stack);
    }
}
