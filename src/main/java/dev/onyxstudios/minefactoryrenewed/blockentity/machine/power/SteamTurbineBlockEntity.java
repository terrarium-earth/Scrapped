package dev.onyxstudios.minefactoryrenewed.blockentity.machine.power;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.power.SteamTurbineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
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
import org.jetbrains.annotations.Nullable;

public class SteamTurbineBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int FLUID_COST = 80;
    private static final int ENERGY_GEN = 160;

    public SteamTurbineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEAM_TURBINE.get(), pos, state);
        this.createEnergy(100000, 0);
        this.createFluid(8000, new FluidStack(ModBlocks.STEAM.get(), 1000));
        this.setMaxIdleTime(2);
        this.setMaxWorkTime(1);
    }

    @Override
    public boolean run() {
        if (level == null || getTank().getFluidAmount() < FLUID_COST) return false;

        getTank().drain(FLUID_COST, IFluidHandler.FluidAction.EXECUTE);
        getEnergy().receiveEnergy(ENERGY_GEN, false);
        return true;
    }

    @Override
    protected void tick() {
        super.tick();
        transferEnergy(ENERGY_GEN);
    }

    @Override
    public boolean hasEnoughPower() {
        return getEnergy().getMaxEnergyStored() - getEnergy().getEnergyStored() >= ENERGY_GEN;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.steam_turbine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SteamTurbineContainer(id, inventory, this);
    }
}
