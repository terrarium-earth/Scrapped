package dev.terrarium.minefactoryrenewed.blockentity.machine.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.animals.SewerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SewerBlockEntity extends MachineBlockEntity implements MenuProvider {

    public SewerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SEWER.get(), pos, state);
        this.createFluid(4000, new FluidStack(ModBlocks.SEWAGE.get(), 1000));
        this.createInventory();
        this.setMaxWorkTime(30);
        this.setMaxIdleTime(2);

        this.createMachineArea(pos, Direction.UP, 0);
        this.getMachineArea().setVerticalRange(1);
        this.getMachineArea().setOriginOffset(0, 1, 0);
    }

    @Override
    public boolean run() {
        if (level == null || getTank().getSpace() < 20) return false;
        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ENTITY_PREDICATE);

        if (entities.size() > 0) {
            getTank().fill(new FluidStack(ModBlocks.SEWAGE.get(), entities.size() * 20), IFluidHandler.FluidAction.EXECUTE);
            return true;
        }

        return false;
    }

    @Override
    protected void tick() {
        super.tick();
        transferFluid(100, getTank());
    }

    @Override
    protected void updateRange() {
        super.updateRange();
        ItemStack stack = getInventory().getStackInSlot(0);
        this.getMachineArea().setVerticalRange(stack.isEmpty() ? 1 : 2);
        this.getMachineArea().setOriginOffset(0, stack.isEmpty() ? 1 : 0, 0);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.sewer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SewerContainer(id, inventory, this);
    }
}
