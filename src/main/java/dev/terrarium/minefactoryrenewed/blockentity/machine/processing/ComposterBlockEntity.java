package dev.terrarium.minefactoryrenewed.blockentity.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.processing.ComposterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

public class ComposterBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int FLUID_COST = 20;
    private static final int FLUID_PER_COMPOST = 2000;
    private int fluidConsumed;

    public ComposterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPOSTER.get(), pos, state);
        this.createEnergy(16000, 20);
        this.createFluid(8000, new FluidStack(ModBlocks.SEWAGE.get(), 1000));
        this.setMaxIdleTime(2);
        this.setMaxWorkTime(100);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("fluidConsumed", fluidConsumed);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidConsumed = tag.getInt("fluidConsumed");
    }

    @Override
    public boolean run() {
        if (level != null && fluidConsumed < FLUID_PER_COMPOST) return false;
        ItemStack result = new ItemStack(ModItems.INDUSTRIAL_FERTILIZER.get(), 1);
        insertOrDropItems(List.of(result));
        fluidConsumed = 0;
        return true;
    }

    @Override
    protected void tickWork() {
        super.tickWork();
        if (canRun() && getTank().getFluidAmount() >= FLUID_COST && fluidConsumed < FLUID_PER_COMPOST) {
            getTank().drain(FLUID_COST, IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
            fluidConsumed += FLUID_COST;
        }
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.composter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ComposterContainer(id, inventory, this);
    }
}
