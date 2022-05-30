package dev.terrarium.minefactoryrenewed.block.power;

import dev.terrarium.minefactoryrenewed.api.machine.IWrenchableMachine;
import dev.terrarium.minefactoryrenewed.blockentity.power.EnergyCellBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergyCellBlock extends BaseEntityBlock implements IWrenchableMachine {

    private final int capacity;
    private final int maxTransfer;

    public EnergyCellBlock(int capacity, int maxTransfer) {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).noOcclusion().requiresCorrectToolForDrops());
        this.capacity = capacity;
        this.maxTransfer = maxTransfer;
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (player.getMainHandItem().getItem() == ModItems.WRENCH.get())
            return super.use(state, level, pos, player, hand, hit);

        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof EnergyCellBlockEntity energyCell) {
            NetworkHooks.openGui((ServerPlayer) player, energyCell, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void onWrenched(Level level, Player player, BlockPos pos) {
        //TODO: Block block save energy to item
        //TODO: Block wrench save energy to item
        level.destroyBlock(pos, false, player);
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (this.getRegistryName() != null) {
            String tooltipText = I18n.get("tooltip.machine." + this.getRegistryName().getPath());
            String[] lines = tooltipText.split("<br>");
            for (String line : lines) {
                Component text = new TextComponent(line);
                tooltip.add(text);
            }
        }
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new EnergyCellBlockEntity(pos, state, this);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ENERGY_CELL.get(), EnergyCellBlockEntity::tick);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxTransfer() {
        return maxTransfer;
    }
}
