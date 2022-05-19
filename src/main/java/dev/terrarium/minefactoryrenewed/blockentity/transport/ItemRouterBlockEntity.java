package dev.terrarium.minefactoryrenewed.blockentity.transport;

import dev.terrarium.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.container.transport.ItemRouterContainer;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ItemRouterBlockEntity extends BaseBlockEntity implements MenuProvider {

    private static final Map<Direction, Integer> SLOT_MAP = Map.of(
            Direction.NORTH, 0,
            Direction.SOUTH, 9,
            Direction.EAST, 18,
            Direction.WEST, 27
    );

    private static final Predicate<Entity> ENTITY_PREDICATE = (entity) -> entity instanceof ItemEntity;
    private final AABB aabb;
    private final ItemStackHandler inventory = new ItemStackHandler(36) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            ItemRouterBlockEntity.this.setChanged();
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    public ItemRouterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_ROUTER.get(), pos, state);
        aabb = new AABB(pos).inflate(0.25);
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

    public static void tick(Level level, BlockPos pos, BlockState state, ItemRouterBlockEntity blockEntity) {
        blockEntity.tick();
    }

    private void tick() {
        if (level == null || level.hasNeighborSignal(getBlockPos())) return;
        List<Entity> entities = level.getEntities((Entity) null, aabb, ENTITY_PREDICATE);
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                ItemEntity itemEntity = (ItemEntity) entity;
                if (routeItem(itemEntity)) {
                    break;
                }
            }
        }
    }

    private boolean routeItem(ItemEntity entity) {
        BlockPos pos = getBlockPos();
        Vec3 diff = entity.position().subtract(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5).normalize();
        int x = Math.round((float) diff.x());
        int z = Math.round((float) diff.z());
        Direction direction = Direction.fromNormal(x, 0, z);
        if (direction == Direction.UP || direction == Direction.SOUTH)
            direction = Direction.NORTH;

        Direction route = findRoute(entity.getItem(), direction);
        if (route != null) {
            BlockPos offsetPos = pos.relative(route);
            entity.moveTo(offsetPos.getX() + 0.5, offsetPos.getY() + 0.15, offsetPos.getZ() + 0.5);
            return true;
        }

        return false;
    }

    private Direction findRoute(ItemStack stack, Direction fromDir) {
        // Try to find a side where the Item is whitelisted, If no side is found, return the last side that has no
        // filters or null if none match
        Direction destination = null;

        for (Direction direction : InventoryUtils.HORIZONTAL) {
            if (direction == fromDir) continue;
            int startIdx = SLOT_MAP.get(direction);

            boolean isEmpty = true;
            for (int i = startIdx; i < startIdx + 9; i++) {
                ItemStack slotStack = inventory.getStackInSlot(i);
                if (slotStack.isEmpty()) continue;

                isEmpty = false;
                if (ItemStack.isSameItemSameTags(slotStack, stack)) {
                    return direction;
                }
            }

            if (isEmpty)
                destination = direction;
        }

        return destination;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.item_router");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ItemRouterContainer(id, inventory, this);
    }
}
