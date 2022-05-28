package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CulinaryGenBlockEntity extends BurnableGenBlockEntity {

    private static final int BASE_ENERGY_GEN = 4;

    public CulinaryGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CULINARY_GENERATOR.get(), pos, state, 100000, 4, 100);
    }

    @Override
    public void burnItem(ItemStack stack) {
        FoodProperties food = stack.getFoodProperties(null);
        if (food != null) {
            setEnergyGen(food.getNutrition() * BASE_ENERGY_GEN);
            setBurnTime((int) (food.getSaturationModifier() / food.getNutrition() * (45 * 20)));
        }
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.isEdible();
    }
}
