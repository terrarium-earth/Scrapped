package dev.terrarium.minefactoryrenewed.item;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
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

public class SafariNetItem extends BaseItem {

    public static final String ENTITY_KEY = "trappedEntity";
    public static final String ENTITY_ID_KEY = "trappedEntityId";
    private final boolean singleUse;

    public SafariNetItem(boolean singleUse) {
        super(new Item.Properties().stacksTo(1).tab(MinefactoryRenewed.TAB));
        this.singleUse = singleUse;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        if (level.isClientSide()) return InteractionResult.sidedSuccess(true);

        if (tryReleaseEntity(level, pos, stack)) {
            if (((SafariNetItem) stack.getItem()).isSingleUse())
                stack.shrink(1);

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    public static void entityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        Entity entity = event.getTarget();

        if (player.getMainHandItem().getItem() instanceof SafariNetItem &&
                tryCaptureEntity(entity, player.getMainHandItem())) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    public static boolean hasEntity(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains(ENTITY_KEY) && tag.contains(ENTITY_ID_KEY);
    }

    public static boolean tryReleaseEntity(Level level, BlockPos pos, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();

        if (hasEntity(stack)) {
            ResourceLocation entityTypeId = new ResourceLocation(tag.getString(ENTITY_ID_KEY));
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(entityTypeId);
            CompoundTag entityTag = tag.getCompound(ENTITY_KEY);

            Entity entity;
            if (entityType != null && (entity = entityType.create(level)) != null) {
                entity.load(entityTag);
                entity.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                level.addFreshEntity(entity);
            }

            tag.remove(ENTITY_KEY);
            tag.remove(ENTITY_ID_KEY);
            stack.setTag(tag);
            return true;
        }

        return false;
    }

    public static boolean tryCaptureEntity(Entity entity, ItemStack stack) {
        if (!entity.getLevel().isClientSide() && entity instanceof LivingEntity livingEntity &&
                !(livingEntity instanceof Player) && livingEntity.canChangeDimensions()) {
            if (stack.isEmpty() && !(stack.getItem() instanceof SafariNetItem)) return false;
            CompoundTag tag = stack.getOrCreateTag();

            if (!hasEntity(stack)) {
                ResourceLocation registryName = livingEntity.getType().getRegistryName();
                if (registryName == null) return false;

                CompoundTag entityTag = new CompoundTag();
                livingEntity.saveWithoutId(entityTag);
                tag.put(ENTITY_KEY, entityTag);
                tag.putString(ENTITY_ID_KEY, registryName.toString());
                stack.setTag(tag);

                livingEntity.remove(Entity.RemovalReason.DISCARDED);
                return true;
            }
        }

        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        CompoundTag tag = stack.getOrCreateTag();

        TranslatableComponent text = new TranslatableComponent("tooltip.safari_net.no_entity");
        if (tag.contains(ENTITY_ID_KEY)) {
            ResourceLocation entityTypeId = new ResourceLocation(tag.getString(ENTITY_ID_KEY));
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
