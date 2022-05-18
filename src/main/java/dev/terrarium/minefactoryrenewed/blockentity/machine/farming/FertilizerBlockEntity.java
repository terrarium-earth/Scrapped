package dev.terrarium.minefactoryrenewed.blockentity.machine.farming;

import dev.terrarium.minefactoryrenewed.blockentity.container.farming.FertilizerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FertilizerBlockEntity extends MachineBlockEntity implements MenuProvider {

    public FertilizerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FERTILIZER.get(), pos, state);
        this.createInventory(9);
        this.createEnergy(32000, 960);
        this.setMaxWorkTime(2);
        this.setMaxIdleTime(20);

        this.createMachineArea(pos, Direction.NORTH);
    }

    @Override
    public boolean run() {
        BlockPos workPos = getMachineArea().getNextBlock();
        if (level == null || level.isEmptyBlock(workPos))
            return false;

        BlockState state = level.getBlockState(workPos);
        if (state.getBlock() instanceof BonemealableBlock bonemealableBlock) {
            int slot = getFertilizerSlot();

            if (slot >= 0 && bonemealableBlock.isValidBonemealTarget(level, workPos, state, false)) {
                bonemealableBlock.performBonemeal((ServerLevel) level, level.getRandom(), workPos, state);
                getInventory().extractItem(slot, 1, false);
                useEnergy();
                return true;
            }
        }

        return false;
    }

    private int getFertilizerSlot() {
        for (int i = 1; i < getInventory().getSlots(); i++) {
            ItemStack stack = getInventory().getStackInSlot(i);

            if (!stack.isEmpty() && stack.is(ModItems.INDUSTRIAL_FERTILIZER.get()))
                return i;
        }

        return -1;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.fertilizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FertilizerContainer(id, inventory, this);
    }
}
