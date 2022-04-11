package dev.onyxstudios.minefactoryrenewed.block.machine.blocks;

import dev.onyxstudios.minefactoryrenewed.api.machine.IWrenchableMachine;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.blocks.DeepStorageBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeepStorageBlock extends BaseEntityBlock implements IWrenchableMachine {

    public static final String STORAGE_KEY = "DeepStorageData";

    public DeepStorageBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(2f));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof MenuProvider menuProvider) {
            NetworkHooks.openGui((ServerPlayer) player, menuProvider, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (blockEntity instanceof DeepStorageBlockEntity deepStorage) {
            ItemStack stack = saveToStack(deepStorage);
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        }

        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity player, ItemStack stack) {
        super.setPlacedBy(level, pos, state, player, stack);

        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(STORAGE_KEY) && level.getBlockEntity(pos) instanceof DeepStorageBlockEntity deepStorage) {
            deepStorage.deserializeNBT(tag.getCompound(STORAGE_KEY));
        }
    }

    @Override
    public void onWrenched(Level level, Player player, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof DeepStorageBlockEntity deepStorage) {
            ItemStack stack = saveToStack(deepStorage);
            level.destroyBlock(pos, false, player);
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        return Collections.emptyList();
    }

    private ItemStack saveToStack(DeepStorageBlockEntity deepStorage) {
        ItemStack stack = new ItemStack(this);
        if (deepStorage != null) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.put(STORAGE_KEY, deepStorage.saveWithoutMetadata());
            stack.setTag(tag);
        }

        return stack;
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return level.isClientSide() ? null :
                createTickerHelper(entityType, ModBlockEntities.DEEP_STORAGE.get(), DeepStorageBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DeepStorageBlockEntity(pos, state);
    }
}
