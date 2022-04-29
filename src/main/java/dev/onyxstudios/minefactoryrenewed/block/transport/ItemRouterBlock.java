package dev.onyxstudios.minefactoryrenewed.block.transport;

import dev.onyxstudios.minefactoryrenewed.api.machine.IWrenchableMachine;
import dev.onyxstudios.minefactoryrenewed.blockentity.transport.ItemRouterBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemRouterBlock extends BaseEntityBlock implements IWrenchableMachine {

    public ItemRouterBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).requiresCorrectToolForDrops());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof MenuProvider menuProvider) {
            NetworkHooks.openGui((ServerPlayer) player, menuProvider, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void onWrenched(Level level, Player player, BlockPos pos) {
        level.destroyBlock(pos, false, player);
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof ItemRouterBlockEntity itemRouter) {
            InventoryUtils.dropInventoryItems(level, pos, itemRouter.getInventory());
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
        return !level.isClientSide() ? createTickerHelper(blockEntityType, ModBlockEntities.ITEM_ROUTER.get(), ItemRouterBlockEntity::tick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemRouterBlockEntity(pos, state);
    }
}
