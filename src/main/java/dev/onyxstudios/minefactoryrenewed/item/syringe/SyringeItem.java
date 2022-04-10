package dev.onyxstudios.minefactoryrenewed.item.syringe;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public abstract class SyringeItem extends Item {

    public SyringeItem() {
        super(new Item.Properties().stacksTo(1).tab(MinefactoryRenewed.TAB));
    }

    public static void entityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        Entity entity = event.getTarget();
        ItemStack heldItem = player.getMainHandItem();
        if (player.level.isClientSide()) return;

        if (heldItem.getItem() instanceof SyringeItem syringeItem && entity instanceof LivingEntity livingEntity) {
            if (syringeItem.canInject(livingEntity)) {
                syringeItem.inject(player.level, livingEntity);
                player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.EMPTY_SYRINGE.get()));
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    public abstract void inject(Level level, LivingEntity entity);
    public abstract boolean canInject(LivingEntity entity);
}
