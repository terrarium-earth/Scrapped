package dev.terrarium.minefactoryrenewed.blockentity.machine.power;

import dev.terrarium.minefactoryrenewed.blockentity.container.power.EthanolGeneratorContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
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
import org.jetbrains.annotations.Nullable;

public class EthanolGeneratorBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int FLUID_COST = 5;
    private static final int ENERGY_GEN = 8;

    public EthanolGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ETHANOL_GENERATOR.get(), pos, state);
        this.createEnergy(100000, 0);
        this.createFluid(8000, new FluidStack(ModBlocks.ETHANOL.get(), 1000));
        this.setMaxIdleTime(1);
        this.setMaxWorkTime(1);
    }

    @Override
    public boolean run() {
        return true;
    }

    @Override
    protected void tick() {
        super.tick();
        if (canRun() && getTank().getFluidAmount() >= FLUID_COST && hasEnoughPower()) {
            getTank().drain(FLUID_COST, IFluidHandler.FluidAction.EXECUTE);
            getEnergy().receiveEnergy(ENERGY_GEN, false);
        }

        transferEnergy(ENERGY_GEN);
    }

    @Override
    public boolean hasEnoughPower() {
        return getEnergy().getMaxEnergyStored() - getEnergy().getEnergyStored() >= ENERGY_GEN;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.ethanol_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new EthanolGeneratorContainer(id, inventory, this);
    }
}
