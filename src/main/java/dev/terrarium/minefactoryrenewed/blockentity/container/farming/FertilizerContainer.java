package dev.terrarium.minefactoryrenewed.blockentity.container.farming;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.farming.FertilizerBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import net.minecraft.world.entity.player.Inventory;
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
