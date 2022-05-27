package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.HellishGenBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class NetherGenContainer extends GeneratorContainer<HellishGenBlockEntity> {
    public NetherGenContainer(@Nullable MenuType<?> menuType, int id, Inventory inventory, HellishGenBlockEntity generator) {
        super(menuType, id, inventory, generator);
        this.addSlot(new SlotItemHandler(generator.getInventory(), 0, 80, 35));
    }
}
