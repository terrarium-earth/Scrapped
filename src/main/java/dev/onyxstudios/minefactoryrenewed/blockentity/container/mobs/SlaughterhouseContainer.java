package dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.SlaughterhouseBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlaughterhouseContainer extends MachineContainer {

    public SlaughterhouseContainer(int id, Inventory inventory, SlaughterhouseBlockEntity slaughterhouse) {
        super(ModBlockEntities.SLAUGHTERHOUSE_CONTAINER.get(), id, inventory, slaughterhouse, false);
        addPlayerSlots(inventory, false);
    }
}
