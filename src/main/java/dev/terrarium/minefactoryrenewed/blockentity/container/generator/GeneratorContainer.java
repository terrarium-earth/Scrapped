package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.GeneratorBlockEntity;
import dev.terrarium.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GeneratorContainer<T extends GeneratorBlockEntity> extends AbstractContainerMenu {

    private final T generator;

    public GeneratorContainer(@Nullable MenuType<?> menuType, int id, Inventory inventory, T generator) {
        super(menuType, id);
        this.generator = generator;

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

    public T getGenerator() {
        return generator;
    }
}
