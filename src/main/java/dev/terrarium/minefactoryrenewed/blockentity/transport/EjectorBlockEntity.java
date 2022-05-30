package dev.terrarium.minefactoryrenewed.blockentity.transport;

import dev.terrarium.minefactoryrenewed.blockentity.BaseBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EjectorBlockEntity extends BaseBlockEntity {

    private Direction facing;
    private int cooldown = 0;

    public EjectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EJECTOR.get(), pos, state);
        facing = state.getValue(HorizontalDirectionalBlock.FACING);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("facing", facing.ordinal());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        facing = InventoryUtils.VALUES[tag.getInt("facing")];
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EjectorBlockEntity blockEntity) {
        blockEntity.tick();
    }

    private void tick() {
        if (level == null || level.isClientSide() || level.hasNeighborSignal(getBlockPos())) return;
        cooldown++;

        if (cooldown >= 4) {
            BlockPos behind = getBlockPos().relative(facing.getOpposite());
            BlockEntity blockEntity = level.getBlockEntity(behind);
            if (blockEntity != null) {
                LazyOptional<IItemHandler> optional = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

                optional.ifPresent(itemHandler -> {
                    for (int i = 0; i < itemHandler.getSlots(); i++) {
                        ItemStack stack = itemHandler.extractItem(i, 1, false);

                        if (!stack.isEmpty()) {
                            BlockPos front = getBlockPos().relative(facing);
                            ItemEntity itemEntity = new ItemEntity(level,
                                    front.getX() + 0.5, front.getY() + 0.2, front.getZ() + 0.5,
                                    new ItemStack(stack.getItem(), 1));
                            itemEntity.setDeltaMovement(0, 0, 0);
                            level.addFreshEntity(itemEntity);
                            break;
                        }
                    }
                });
            }

            cooldown = 0;
        }
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public Direction getFacing() {
        return facing;
    }
}
