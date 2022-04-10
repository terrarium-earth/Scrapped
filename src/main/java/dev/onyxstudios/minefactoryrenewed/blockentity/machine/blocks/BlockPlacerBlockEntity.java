package dev.onyxstudios.minefactoryrenewed.blockentity.machine.blocks;

import dev.onyxstudios.minefactoryrenewed.api.machine.MachineArea;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.blocks.BlockPlacerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockPlacerBlockEntity extends MachineBlockEntity implements MenuProvider {

    public BlockPlacerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLOCK_PLACER.get(), pos, state);
        this.createInventory(1, false);
        this.createEnergy(16000, 20);
        this.setMaxWorkTime(2);
        this.setMaxIdleTime(20);

        this.createMachineArea(pos, Direction.NORTH, 0);
        this.getMachineArea().setOneBlock(true);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        ItemStack stack = getInventory().getStackInSlot(0);

        if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
            Direction direction = getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            BlockPos pos = getBlockPos().relative(direction);
            if (level.isEmptyBlock(pos)) {
                level.setBlockAndUpdate(pos, blockItem.getBlock().defaultBlockState());
                getInventory().extractItem(0, 1, false);
                useEnergy();
                return true;
            }
        }

        return false;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.block_placer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BlockPlacerContainer(id, inventory, this);
    }
}
