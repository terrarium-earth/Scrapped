package dev.terrarium.minefactoryrenewed.blockentity.container.power;

import dev.terrarium.minefactoryrenewed.blockentity.power.EnergyCellBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnergyCellContainer extends AbstractContainerMenu {

    private final EnergyCellBlockEntity energyCell;

    public EnergyCellContainer(int id, Inventory inventory, EnergyCellBlockEntity energyCell) {
        super(ModBlockEntities.ENERGY_CELL_CONTAINER.get(), id);
        this.energyCell = energyCell;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int index) {
        return InventoryUtils.handleShiftClick(this, player, index);
    }

    public EnergyCellBlockEntity getEnergyCell() {
        return energyCell;
    }
}
