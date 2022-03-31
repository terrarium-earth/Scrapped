package dev.onyxstudios.minefactoryrenewed.blockentity.container;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class MachineContainer extends AbstractContainerMenu {

    private final MachineBlockEntity blockEntity;

    protected MachineContainer(@Nullable MenuType<?> menuType, int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(menuType, id);
        this.blockEntity = blockEntity;
        if (blockEntity.getInventory() != null)
            this.addSlot(new RangeUpgradeSlot(blockEntity.getInventory(), 0, 152, 75));

        //Player Slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 155));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public MachineBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
