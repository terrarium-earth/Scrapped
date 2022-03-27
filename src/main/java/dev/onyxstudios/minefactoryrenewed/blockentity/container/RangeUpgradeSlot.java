package dev.onyxstudios.minefactoryrenewed.blockentity.container;

import dev.onyxstudios.minefactoryrenewed.item.MachineUpgradeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class RangeUpgradeSlot extends SlotItemHandler {

    public RangeUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.getItem() instanceof MachineUpgradeItem;
    }
}
