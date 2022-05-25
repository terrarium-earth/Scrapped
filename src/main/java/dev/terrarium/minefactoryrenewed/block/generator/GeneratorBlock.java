package dev.terrarium.minefactoryrenewed.block.generator;

import dev.terrarium.minefactoryrenewed.api.machine.IWrenchableMachine;
import dev.terrarium.minefactoryrenewed.blockentity.generator.GeneratorBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class GeneratorBlock extends BaseEntityBlock implements IWrenchableMachine {

    public GeneratorBlock() {
        this(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).requiresCorrectToolForDrops());
    }

    public GeneratorBlock(Properties properties) {
        super(properties);
    }

    public abstract BlockEntityType<? extends GeneratorBlockEntity> getBlockEntityType();

    @Nullable
    @Override
    public abstract BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state);

    @Override
    public void onWrenched(Level level, Player player, BlockPos pos) {
        level.destroyBlock(pos, false, player);
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (player.getMainHandItem().getItem() == ModItems.WRENCH.get())
            return super.use(state, level, pos, player, hand, hit);

        if (interactFluid(level, pos, player, hand)) {
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof GeneratorBlockEntity generator && generator.hasMenu()) {
            NetworkHooks.openGui((ServerPlayer) player, generator, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    protected boolean interactFluid(Level level, BlockPos pos, Player player, InteractionHand hand) {
        LazyOptional<IFluidHandlerItem> handler = FluidUtil.getFluidHandler(player.getItemInHand(hand));
        if (handler.isPresent() && level.getBlockEntity(pos) instanceof GeneratorBlockEntity generator && generator.getTank() != null) {
            if (FluidUtil.interactWithFluidHandler(player, hand, generator.getTank())) {
                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), Block.UPDATE_ALL);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof GeneratorBlockEntity generator) {
            InventoryUtils.dropInventoryItems(level, pos, generator.getInventory());
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
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if(Screen.hasShiftDown()) {
            if (this.getRegistryName() != null) {
                String tooltipText = I18n.get("tooltip.generator." + this.getRegistryName().getPath());
                String[] lines = tooltipText.split("<br>");
                for (String line : lines) {
                    Component text = new TextComponent(line);
                    tooltip.add(text);
                }
            }
        } else {
            tooltip.add(new TranslatableComponent("tooltip.item.shift_info"));
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ?
                createTickerHelper(blockEntityType, getBlockEntityType(), GeneratorBlockEntity::tickClient) :
                createTickerHelper(blockEntityType, getBlockEntityType(), GeneratorBlockEntity::tick);
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
