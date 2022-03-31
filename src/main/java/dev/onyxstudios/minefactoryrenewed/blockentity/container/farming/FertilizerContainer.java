package dev.onyxstudios.minefactoryrenewed.blockentity.container.farming;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FertilizerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FertilizerContainer extends MachineContainer {

    public FertilizerContainer(int id, Inventory inventory, FertilizerBlockEntity fertilizer) {
        super(ModBlockEntities.FERTILIZER_CONTAINER.get(), id, inventory, fertilizer);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new FertilizerSlot(fertilizer.getInventory(), 1 + (i * 3 + j), 8 + j * 18, 21 + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return InventoryUtils.handleShiftClick(this, player, index);
    }

    private static class FertilizerSlot extends SlotItemHandler {

        public FertilizerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return stack.is(ModItems.INDUSTRIAL_FERTILIZER.get());
        }
    }
}
