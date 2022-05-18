package dev.terrarium.minefactoryrenewed.blockentity.container;

import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class MachineContainer extends AbstractContainerMenu {

    private final MachineBlockEntity blockEntity;

    public MachineContainer(@Nullable MenuType<?> menuType, int id, Inventory inventory, MachineBlockEntity blockEntity) {
        this(menuType, id, inventory, blockEntity, true);
    }

    public MachineContainer(@Nullable MenuType<?> menuType, int id, Inventory inventory,
                            MachineBlockEntity blockEntity, boolean addSlots) {
        super(menuType, id);
        this.blockEntity = blockEntity;

        if (addSlots) {
            if (blockEntity.getInventory() != null)
                this.addSlot(new RangeUpgradeSlot(blockEntity.getInventory(), 0, 152, 75));

            addPlayerSlots(inventory);
        }
    }

    protected void addPlayerSlots(Inventory inventory) {
        this.addPlayerSlots(inventory, true);
    }

    protected void addPlayerSlots(Inventory inventory, boolean extended) {
        //Player Slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, (extended ? 97 : 84) + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, extended ? 155 : 142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return InventoryUtils.handleShiftClick(this, player, index);
    }

    public MachineBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
