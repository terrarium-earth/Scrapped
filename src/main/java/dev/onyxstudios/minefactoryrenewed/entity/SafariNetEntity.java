package dev.onyxstudios.minefactoryrenewed.entity;

import dev.onyxstudios.minefactoryrenewed.item.SafariNetItem;
import dev.onyxstudios.minefactoryrenewed.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SafariNetEntity extends ThrowableItemProjectile {

    public SafariNetEntity(EntityType<SafariNetEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SafariNetEntity(Level level) {
        super(ModEntities.SAFARI_NET.get(), level);
    }

    @Override
    public void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        BlockPos pos = hitResult.getBlockPos();
        ItemStack stack = getItem();
        if (stack.isEmpty() || level.isClientSide()) return;
        if (!tryRelease(pos, stack))
            dropSafariNet(stack);

        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        BlockPos pos = new BlockPos(hitResult.getLocation());
        ItemStack stack = getItem();
        Entity entity = hitResult.getEntity();
        if (stack.isEmpty() || level.isClientSide()) return;

        if (!tryRelease(pos, stack)) {
            SafariNetItem.tryCaptureEntity(entity, stack);
            dropSafariNet(stack);
        }

        this.remove(RemovalReason.DISCARDED);
    }

    private boolean tryRelease(BlockPos pos, ItemStack stack) {
        if (SafariNetItem.hasEntity(stack)) {
            SafariNetItem.tryReleaseEntity(level, pos, stack);
            if (!((SafariNetItem) stack.getItem()).isSingleUse())
                dropSafariNet(stack);

            return true;
        }

        return false;
    }

    private void dropSafariNet(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(level, getX(), getY(), getZ(), stack.copy());
        itemEntity.setPickUpDelay(40);
        level.addFreshEntity(itemEntity);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public Item getDefaultItem() {
        return getItemRaw().getItem();
    }
}
