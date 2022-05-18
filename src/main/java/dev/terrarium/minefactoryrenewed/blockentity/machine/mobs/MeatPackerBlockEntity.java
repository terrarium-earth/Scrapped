package dev.terrarium.minefactoryrenewed.blockentity.machine.mobs;

import dev.terrarium.minefactoryrenewed.blockentity.container.mobs.MeatPackerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MeatPackerBlockEntity extends MachineBlockEntity implements MenuProvider {

    public MeatPackerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MEAT_PACKER.get(), pos, state);
        this.createEnergy(16000, 100);
        this.createFluid(10000, new FluidStack(ModBlocks.MEAT.get(), 10000));
        this.setMaxWorkTime(40);
        this.setMaxIdleTime(10);
    }

    @Override
    public boolean run() {
        if (level == null) return false;

        if (canMakeMeat()) {
            boolean isNugget = getTank().getFluidAmount() < 200 && getTank().getFluidAmount() >= 50;

            ItemStack stack = new ItemStack(ModItems.RAW_MEAT_INGOT.get());
            if (isNugget)
                stack = new ItemStack(ModItems.RAW_MEAT_NUGGET.get());

            insertOrDropItems(List.of(stack));
            getTank().drain(isNugget ? 50 : 200, IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
            return true;
        }

        return false;
    }

    private boolean canMakeMeat() {
        return getTank().getFluidAmount() >= 200 || getTank().getFluidAmount() >= 50;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.meat_packer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MeatPackerContainer(id, inventory, this);
    }
}
