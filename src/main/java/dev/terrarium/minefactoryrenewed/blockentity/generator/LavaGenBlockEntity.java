package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.blockentity.container.generator.LavaGenContainer;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LavaGenBlockEntity extends GeneratorBlockEntity {

    private int ticker = 0;

    public LavaGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LAVA_GENERATOR.get(), pos, state, 100000, 40, 100);
        this.createFluid(8000, new FluidStack(Fluids.LAVA, 1000));
    }


    @Override
    protected void tick() {
        super.tick();
        if(ticker > 10 && this.getTank().getCapacity() > 10) {
            this.getTank().drain(10, IFluidHandler.FluidAction.EXECUTE);
            this.generateEnergy();
            ticker = 0;
        }
        ticker++;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("blocks.minefactoryrenewed.lava_generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new LavaGenContainer(id, inventory, this);
    }
}
