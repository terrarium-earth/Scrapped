package dev.terrarium.minefactoryrenewed.blockentity.container.mobs;

import dev.terrarium.minefactoryrenewed.blockentity.container.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.mobs.SlaughterhouseBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class SlaughterhouseContainer extends MachineContainer {

    public SlaughterhouseContainer(int id, Inventory inventory, SlaughterhouseBlockEntity slaughterhouse) {
        super(ModBlockEntities.SLAUGHTERHOUSE_CONTAINER.get(), id, inventory, slaughterhouse, false);
        addPlayerSlots(inventory, false);
    }
}
