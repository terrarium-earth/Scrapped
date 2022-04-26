package dev.onyxstudios.minefactoryrenewed.block.machine;

import dev.onyxstudios.minefactoryrenewed.api.machine.IWrenchableMachine;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class MachineBlock extends BaseEntityBlock implements IWrenchableMachine {

    public MachineBlock() {
        this(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).requiresCorrectToolForDrops());
    }

    public MachineBlock(Properties properties) {
        super(properties);
    }

    public abstract BlockEntityType<? extends MachineBlockEntity> getBlockEntityType();

    @Nullable
    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);

    @Override
    public void onWrenched(Level level, Player player, BlockPos pos) {
        level.destroyBlock(pos, false, player);
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean holdingWrench = player.getMainHandItem().getItem() == ModItems.WRENCH.get();
        if (holdingWrench)
            return super.use(state, level, pos, player, hand, hit);

        if (interactFluid(level, pos, player, hand)) {
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof MenuProvider menuProvider) {
            NetworkHooks.openGui((ServerPlayer) player, menuProvider, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }


    protected boolean interactFluid(Level level, BlockPos pos, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        LazyOptional<IFluidHandlerItem> handler = FluidUtil.getFluidHandler(stack);

        if (handler.isPresent() && level.getBlockEntity(pos) instanceof MachineBlockEntity machine && machine.getTank() != null) {
            if (FluidUtil.interactWithFluidHandler(player, hand, machine.getTank())) {
                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), Block.UPDATE_ALL);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof MachineBlockEntity machine) {
            InventoryUtils.dropInventoryItems(level, pos, machine.getInventory());
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (this.getRegistryName() != null) {
            Component text = new TranslatableComponent("tooltip.machine." + this.getRegistryName().getPath())
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY));
            tooltip.add(text);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ?
                createTickerHelper(blockEntityType, getBlockEntityType(), MachineBlockEntity::tickClient) :
                createTickerHelper(blockEntityType, getBlockEntityType(), MachineBlockEntity::tick);
    }
}
