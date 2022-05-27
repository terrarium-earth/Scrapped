package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.data.generator.DisenchantmentManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisenchantmentGenBlockEntity extends BurnableGenBlockEntity {

    public DisenchantmentGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DISENCHANTMENT_GENERATOR.get(), pos, state, 100000, 80, 500);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new DisenchantmentGenContainer(id, inventory, this);
    }

    @Override
    public void burnItem(ItemStack stack) {
        setEnergyGen(DisenchantmentManager.getInstance().getEnergyGen(stack));
        setBurnTime(DisenchantmentManager.getInstance().getBurnTime(stack));
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return DisenchantmentManager.getInstance().isEnchantmentBurnable(stack);
    }
}
