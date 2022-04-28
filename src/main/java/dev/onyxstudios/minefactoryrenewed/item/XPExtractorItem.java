package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class XPExtractorItem extends BaseItem {

    public XPExtractorItem() {
        super(new Item.Properties().tab(MinefactoryRenewed.TAB).stacksTo(1));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);

        if (livingEntity instanceof Player player && getUseDuration(stack) - timeCharged >= 18 && player.experienceLevel >= 1) {
            int slot = findBucketSlot(player);

            if (slot >= 0) {
                player.experienceLevel -= 1;
                ItemStack essenceBucket = new ItemStack(ModItems.ESSENCE_BUCKET.get());
                player.getInventory().getItem(slot).shrink(1);
                if (!player.addItem(essenceBucket)) {
                    player.drop(essenceBucket, false);
                }
            }
        }
    }

    private int findBucketSlot(Player player) {
        return player.getInventory().findSlotMatchingItem(new ItemStack(Items.BUCKET));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    public int getUseDuration(ItemStack stack) {
        return 1000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
