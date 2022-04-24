package dev.onyxstudios.minefactoryrenewed.blockentity.transport;

import dev.onyxstudios.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.function.Predicate;

public class ItemCollectorBlockEntity extends BaseBlockEntity {

    private static final Predicate<Entity> ENTITY_PREDICATE = (entity) -> entity instanceof ItemEntity;
    private final ItemStackHandler inventory = new ItemStackHandler(1);
    private final AABB aabb;

    public ItemCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_COLLECTOR.get(), pos, state);
        aabb = new AABB(pos).inflate(0.1);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("inventory"));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ItemCollectorBlockEntity blockEntity) {
        blockEntity.tick();
    }

    private void tick() {
        if (level == null) return;

        ItemStack stack = inventory.getStackInSlot(0);
        List<Entity> entities = level.getEntities((Entity) null, aabb, ENTITY_PREDICATE);
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                ItemEntity itemEntity = (ItemEntity) entity;
                ItemStack remainder = InventoryUtils.tryInsertItem(level, inventory, itemEntity.getItem());
                if (remainder.isEmpty()) {
                    itemEntity.remove(Entity.RemovalReason.DISCARDED);
                } else {
                    itemEntity.setItem(remainder.copy());
                }
            }
        }

        if (!stack.isEmpty()) {
            tryInsertItem();
        }
    }

    protected void tryInsertItem() {
        LazyOptional<IItemHandler> optional = InventoryUtils.getNearbyInventory(level, getBlockPos());
        if (!optional.isPresent()) return;

        optional.ifPresent(neighborInv -> {
            ItemStack stack = inventory.getStackInSlot(0);
            ItemStack remaining = InventoryUtils.tryInsertItem(level, neighborInv, stack);
            inventory.setStackInSlot(0, remaining);
        });
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }
}
