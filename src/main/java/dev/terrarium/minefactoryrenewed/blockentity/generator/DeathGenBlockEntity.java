package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.data.generator.GeneratorItemManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DeathGenBlockEntity extends BurnableGenBlockEntity {

    public DeathGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEATH_GENERATOR.get(), pos, state, 100000, 40, 100);
    }

    @Override
    public void burnItem(ItemStack stack) {
        setEnergyGen(GeneratorItemManager.getDeath().getEnergyGen(stack));
        setBurnTime(GeneratorItemManager.getDeath().getBurnTime(stack));
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GeneratorItemManager.getDeath().isValid(stack);
    }
}
