package dev.onyxstudios.minefactoryrenewed.blockentity.machine;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.PlanterContainer;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PlanterBlockEntity extends MachineBlockEntity implements MenuProvider {

    private final ItemStackHandler filterInventory = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            PlanterBlockEntity.this.setChanged();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public PlanterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLANTER_BLOCK_ENTITY.get(), pos, state, 10000, 17);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("filterInventory", filterInventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("filterInventory"))
            filterInventory.deserializeNBT(tag.getCompound("filterInventory"));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PlanterBlockEntity blockEntity) {
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.planter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new PlanterContainer(id, inventory, this);
    }

    public ItemStackHandler getFilterInventory() {
        return filterInventory;
    }
}
