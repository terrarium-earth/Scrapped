package dev.terrarium.minefactoryrenewed.blockentity.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.processing.LavaFabContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class LavaFabBlockEntity extends MachineBlockEntity implements MenuProvider {

    public LavaFabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LAVA_FABRICATOR.get(), pos, state);
        this.createEnergy(32000, 200);
        this.createFluid(8000, new FluidStack(Fluids.LAVA, 1000));
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
        if (canRun() && getTank().getSpace() > 0) {
            getTank().fill(new FluidStack(Fluids.LAVA, 1), IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
        }

        transferFluid(100, getTank());
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.lava_fabricator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new LavaFabContainer(id, inventory, this);
    }
}
