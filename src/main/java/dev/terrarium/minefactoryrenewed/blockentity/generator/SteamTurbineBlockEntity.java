package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.SteamTurbineContainer;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SteamTurbineBlockEntity extends GeneratorBlockEntity {

    private static final int FLUID_COST = 10;

    public SteamTurbineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEAM_TURBINE.get(), pos, state, 100000, 100, 240);
        this.createFluid(8000, new FluidStack(ModBlocks.STEAM.get(), 1000));
    }

    @Override
    protected void tick() {
        super.tick();
        if (this.getTank().getFluidAmount() >= FLUID_COST && canGenerate()) {
            this.getTank().drain(FLUID_COST, IFluidHandler.FluidAction.EXECUTE);
            this.generateEnergy();
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.steam_turbine");
    }

    @Override
    public Component getDisplayText() {
        return new TranslatableComponent("tooltip." + MinefactoryRenewed.MODID + ".generator." + (getTank().getFluidAmount() >= FLUID_COST && canGenerate() ? "generating" : "idle"), String.valueOf(getEnergyGen()));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new SteamTurbineContainer(id, inventory, this);
    }
}
