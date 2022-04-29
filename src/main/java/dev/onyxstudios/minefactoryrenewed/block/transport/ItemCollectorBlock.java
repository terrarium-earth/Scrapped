package dev.onyxstudios.minefactoryrenewed.block.transport;

import dev.onyxstudios.minefactoryrenewed.api.machine.IWrenchableMachine;
import dev.onyxstudios.minefactoryrenewed.blockentity.transport.ItemCollectorBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCollectorBlock extends BaseEntityBlock implements IWrenchableMachine {

    public ItemCollectorBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).requiresCorrectToolForDrops());
    }

    @Override
    public void onWrenched(Level level, Player player, BlockPos pos) {
        level.destroyBlock(pos, false, player);
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof ItemCollectorBlockEntity collector) {
            InventoryUtils.dropInventoryItems(level, pos, collector.getInventory());
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @NotNull
    @Override
    public List<ItemStack> getDrops(@NotNull BlockState state, LootContext.@NotNull Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        if (drops.isEmpty()) {
            drops.add(new ItemStack(this));
        }

        return drops;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide() ? createTickerHelper(blockEntityType, ModBlockEntities.ITEM_COLLECTOR.get(), ItemCollectorBlockEntity::tick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemCollectorBlockEntity(pos, state);
    }
}
