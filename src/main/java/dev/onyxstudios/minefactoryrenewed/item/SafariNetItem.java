package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SafariNetItem extends Item {

    private final boolean singleUse;

    public SafariNetItem(boolean singleUse) {
        super(new Item.Properties().stacksTo(1).tab(MinefactoryRenewed.TAB));
        this.singleUse = singleUse;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CompoundTag tag = stack.getOrCreateTag();
        BlockPos pos = context.getClickedPos();

        if (tag.contains("trappedEntity")) {
            if (level.isClientSide()) return InteractionResult.sidedSuccess(true);
            ResourceLocation entityTypeId = new ResourceLocation(tag.getString("trappedEntityId"));
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(entityTypeId);
            CompoundTag entityTag = tag.getCompound("trappedEntity");

            Entity entity;
            if (entityType != null && (entity = entityType.create(level)) != null) {
                entity.load(entityTag);
                entity.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                level.addFreshEntity(entity);
            }

            tag.remove("trappedEntity");
            tag.remove("trappedEntityId");
            stack.setTag(tag);

            if (((SafariNetItem) stack.getItem()).isSingleUse()) {
                stack.shrink(1);
            }
        }

        return super.useOn(context);
    }

    public static void entityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        Entity entity = event.getTarget();

        if (!entity.getLevel().isClientSide() && entity instanceof LivingEntity livingEntity && livingEntity.canChangeDimensions()) {
            ItemStack heldItem = player.getMainHandItem();

            if (!heldItem.isEmpty() && heldItem.getItem() instanceof SafariNetItem) {
                CompoundTag tag = heldItem.getOrCreateTag();

                if (!tag.contains("trappedEntity")) {
                    ResourceLocation registryName = livingEntity.getType().getRegistryName();
                    if (registryName == null) return;

                    CompoundTag entityTag = new CompoundTag();
                    livingEntity.saveWithoutId(entityTag);
                    tag.put("trappedEntity", entityTag);
                    tag.putString("trappedEntityId", registryName.toString());
                    heldItem.setTag(tag);
                    livingEntity.remove(Entity.RemovalReason.DISCARDED);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        CompoundTag tag = stack.getOrCreateTag();

        TranslatableComponent text = new TranslatableComponent("tooltip.safari_net.no_entity");
        if (tag.contains("trappedEntityId")) {
            ResourceLocation entityTypeId = new ResourceLocation(tag.getString("trappedEntityId"));
            String nameStr = I18n.get("entity." + entityTypeId.getNamespace() + "." + entityTypeId.getPath());
            Component entityName = new TextComponent(nameStr).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD));

            text = new TranslatableComponent("tooltip.safari_net.captured_entity", entityName);
        }

        text.setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY));
        tooltip.add(text);
    }

    public boolean isSingleUse() {
        return singleUse;
    }
}
