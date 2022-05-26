package dev.terrarium.minefactoryrenewed.blockentity.container.generator;

import dev.terrarium.minefactoryrenewed.blockentity.generator.SteamTurbineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;

public class SteamTurbineContainer extends GeneratorContainer<SteamTurbineBlockEntity> {

    public SteamTurbineContainer(int id, Inventory inventory, SteamTurbineBlockEntity blockEntity) {
        super(ModBlockEntities.STEAM_TURBINE_CONTAINER.get(), id, inventory, blockEntity);
    }
}
