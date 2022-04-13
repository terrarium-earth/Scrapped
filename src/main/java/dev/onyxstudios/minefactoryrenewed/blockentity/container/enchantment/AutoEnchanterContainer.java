package dev.onyxstudios.minefactoryrenewed.blockentity.container.enchantment;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.OutputSlot;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class AutoEnchanterContainer extends MachineContainer {

    public AutoEnchanterContainer(int id, Inventory inventory, MachineBlockEntity blockEntity) {
        super(ModBlockEntities.AUTO_ENCHANTER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 80, 21));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 1, 80, 57));

        //Player Slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return InventoryUtils.handleShiftClick(this, player, index);
    }
}
