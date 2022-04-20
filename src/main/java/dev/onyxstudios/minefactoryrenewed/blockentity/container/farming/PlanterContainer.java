package dev.onyxstudios.minefactoryrenewed.blockentity.container.farming;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.PlanterBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PlanterContainer extends MachineContainer {

    public PlanterContainer(int id, Inventory inventory, PlanterBlockEntity planter) {
        super(ModBlockEntities.PLANTER_CONTAINER.get(), id, inventory, planter);

        //Input Slots
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.addSlot(new SlotItemHandler(planter.getInventory(), 1 + (i * 4 + j), 66 + j * 18, 12 + i * 18));
            }
        }

        //Filter Slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new FilterSlot(planter.getFilterInventory(), i * 3 + j, 8 + j * 18, 30 + i * 18));
            }
        }
    }

    private static class FilterSlot extends SlotItemHandler {

        public FilterSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }
}
