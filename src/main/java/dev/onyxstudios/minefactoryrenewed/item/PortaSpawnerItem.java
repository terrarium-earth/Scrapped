package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.mixin.common.BaseSpawnerAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PortaSpawnerItem extends BaseItem {

    public static final String SPAWNER_KEY = "capturedSpawner";
    public static final String ENTITY_KEY = "spawnerEntityName";

    public PortaSpawnerItem() {
        super(new Item.Properties().tab(MinefactoryRenewed.TAB).stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @NotNull
    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        CompoundTag tag = stack.getOrCreateTag();
        if (level.isClientSide()) return super.useOn(context);

        if (tag.contains(SPAWNER_KEY)) {
            Direction direction = context.getClickedFace();
            BlockPlaceContext placeContext = placeContext(level, player, pos, direction, hand, Items.SPAWNER.getDefaultInstance());
            BlockPos placePos = placeContext.getClickedPos();

            if (placeContext.canPlace()) {
                BlockState state = Blocks.SPAWNER.getStateForPlacement(placeContext);
                if (state == null || !level.setBlockAndUpdate(placePos, state)) return InteractionResult.FAIL;

                BlockEntity blockEntity = level.getBlockEntity(placePos);
                if (blockEntity == null) return InteractionResult.FAIL;

                CompoundTag spawnerTag = tag.getCompound(SPAWNER_KEY);
                spawnerTag.putInt("x", placePos.getX());
                spawnerTag.putInt("y", placePos.getY());
                spawnerTag.putInt("z", placePos.getZ());
                blockEntity.deserializeNBT(spawnerTag);
                stack.shrink(1);
                level.sendBlockUpdated(placePos, state, state, Block.UPDATE_ALL);

                SoundType sound = SoundType.STONE;
                level.playSound(player, placePos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
                return InteractionResult.SUCCESS;
            }
        } else {
            if (level.getBlockEntity(pos) instanceof SpawnerBlockEntity spawner) {
                SpawnData spawnData = ((BaseSpawnerAccessor) spawner.getSpawner()).getSpawnData();
                if (!spawnData.getEntityToSpawn().contains("id")) return InteractionResult.FAIL;

                CompoundTag spawnerTag = spawner.serializeNBT();
                tag.put(SPAWNER_KEY, spawnerTag);
                tag.putString(ENTITY_KEY, spawnData.getEntityToSpawn().getString("id"));
                stack.setTag(tag);
                level.removeBlock(pos, false);
                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }

    private static BlockPlaceContext placeContext(Level level, Player player, BlockPos pos, Direction direction, InteractionHand hand, ItemStack stack) {
        return new BlockPlaceContext(
                level, player, hand, stack,
                new BlockHitResult(new Vec3(
                        pos.getX() + 0.5 + direction.getStepX() * 0.5,
                        pos.getY() + 0.5 + direction.getStepY() * 0.5,
                        pos.getZ() + 0.5 + direction.getStepZ() * 0.5D
                ), direction, pos, false)
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(ENTITY_KEY)) {
            Component entityName = new TranslatableComponent(tag.getString(ENTITY_KEY))
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD));
            tooltip.add(new TranslatableComponent("tooltip.portaspawner.captured_spawner", entityName)
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        } else {
            tooltip.add(new TranslatableComponent("tooltip.portaspawner.no_spawner")
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        }
    }
}
