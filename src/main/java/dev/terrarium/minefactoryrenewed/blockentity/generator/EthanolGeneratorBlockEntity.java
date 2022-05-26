package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.blockentity.container.generator.EthanolGeneratorContainer;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EthanolGeneratorBlockEntity extends GeneratorBlockEntity {

    private static final int FLUID_COST = 5;

    public EthanolGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ETHANOL_GENERATOR.get(), pos, state, 100000, 120, 240);
        this.createFluid(8000, new FluidStack(ModBlocks.ETHANOL.get(), 1000));
    }

    @Override
    protected void tick() {
        super.tick();
        if (getTank().getFluidAmount() >= FLUID_COST && canGenerate()) {
            getTank().drain(FLUID_COST, IFluidHandler.FluidAction.EXECUTE);
            generateEnergy();
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.ethanol_generator");
    }

    @Override
    public Component getDisplayText() {
        return getTank().getFluidAmount() >= FLUID_COST && canGenerate() ?
                new TranslatableComponent("tooltip.generator.generating", String.valueOf(getEnergyGen())) :
                new TranslatableComponent("tooltip.generator.idle");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new EthanolGeneratorContainer(id, inventory, this);
    }
}
