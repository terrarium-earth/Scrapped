package dev.terrarium.minefactoryrenewed.blockentity.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.processing.EthanolReactorContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import dev.terrarium.minefactoryrenewed.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class EthanolReactorBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int[] SCALE = new int[]{0, 5, 25, 70, 150, 275, 455, 700, 1020, 1425};
    private static final int MAX_BURN_TIME = 8000;

    private int efficiency = 0;
    private int burnTime;

    public EthanolReactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ETHANOL_REACTOR.get(), pos, state);
        this.createInventory(9, false);
        this.createInventory(new ItemStackHandler(9) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                EthanolReactorBlockEntity.this.calculateEfficiency();
                EthanolReactorBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return !stack.isEmpty() && stack.is(ModTags.ETHANOL_SOURCE);
            }
        });
        this.createFluid(8000, new FluidStack(ModBlocks.ETHANOL.get(), 1000));
        this.setMaxIdleTime(1);
        this.setMaxWorkTime(1);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("efficiency", efficiency);
        tag.putInt("burnTime", burnTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        efficiency = tag.getInt("efficiency");
        burnTime = tag.getInt("burnTime");
    }

    @Override
    public boolean run() {
        return true;
    }

    @Override
    protected void tick() {
        super.tick();

        if (canRun() && getTank().getSpace() > 0) {
            if (MAX_BURN_TIME - burnTime >= efficiency) {
                burnTime += efficiency;
                for (int i = 0; i < getInventory().getSlots(); i++) {
                    ItemStack stack = getInventory().getStackInSlot(i);
                    if (!stack.isEmpty())
                        getInventory().extractItem(i, 1, false);
                }
            }

            if (burnTime > 0) {
                burnTime--;
                getTank().fill(new FluidStack(ModBlocks.ETHANOL.get(), 1), IFluidHandler.FluidAction.EXECUTE);
            }
        }

        transferFluid(10, getTank());
    }

    private void calculateEfficiency() {
        Set<Item> slots = new HashSet<>();
        int validSlots = 0;
        for (int i = 0; i < getInventory().getSlots(); i++) {
            ItemStack stack = getInventory().getStackInSlot(i);
            if (stack.isEmpty() || slots.contains(stack.getItem())) continue;

            slots.add(stack.getItem());
            validSlots++;
        }

        this.efficiency = SCALE[validSlots];
    }

    public int getEfficiency() {
        return efficiency;
    }

    public int getMaxEfficiency() {
        return SCALE[SCALE.length - 1];
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.ethanol_reactor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new EthanolReactorContainer(id, inventory, this);
    }
}
