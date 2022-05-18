package dev.terrarium.minefactoryrenewed.item;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.api.item.LauncherMode;
import dev.terrarium.minefactoryrenewed.entity.SafariNetEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SafariNetLauncherItem extends BaseItem {

    public SafariNetLauncherItem() {
        super(new Item.Properties().stacksTo(1).tab(MinefactoryRenewed.TAB));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getMainHandItem();
        CompoundTag tag = stack.getOrCreateTag();

        if (player.isShiftKeyDown() && !level.isClientSide()) {
            LauncherMode mode = LauncherMode.values()[tag.getInt("mode")] == LauncherMode.CAPTURE ?
                    LauncherMode.RELEASE : LauncherMode.CAPTURE;
            tag.putInt("mode", mode.ordinal());
            stack.setTag(tag);

            player.displayClientMessage(new TranslatableComponent("tooltip.safari_net_launcher.mode",
                    new TranslatableComponent(mode.getLangKey()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)))
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.YELLOW)), true);
            return InteractionResultHolder.pass(stack);
        }

        LauncherMode mode = LauncherMode.values()[tag.getInt("mode")];
        int slot = findSafariNet(player, mode == LauncherMode.CAPTURE);
        if (slot == -1) {
            player.displayClientMessage(new TranslatableComponent("tooltip.safari_net_launcher.no_safari_net")
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)), true);
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide()) {
            ItemStack safariNet = player.getInventory().getItem(slot);
            SafariNetEntity entity = new SafariNetEntity(level);
            entity.setItem(safariNet);
            entity.setPos(player.getX(), player.getEyeY() - 0.1f, player.getZ());
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);

            level.addFreshEntity(entity);
            stack.shrink(1);
        }

        return InteractionResultHolder.success(stack);
    }

    private int findSafariNet(Player player, boolean empty) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.items.size(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof SafariNetItem) {
                CompoundTag tag = stack.getOrCreateTag();
                boolean hasEntity = tag.contains(SafariNetItem.ENTITY_KEY);
                if ((!empty && hasEntity) || (empty && !hasEntity))
                    return i;
            }
        }

        return -1;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        CompoundTag tag = stack.getOrCreateTag();
        LauncherMode mode = LauncherMode.values()[tag.getInt("mode")];
        Component text = new TranslatableComponent("tooltip.safari_net_launcher.mode", new TranslatableComponent(mode.getLangKey())
                .setStyle(Style.EMPTY.applyFormat(ChatFormatting.YELLOW)))
                .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY));

        tooltip.add(text);
    }
}
