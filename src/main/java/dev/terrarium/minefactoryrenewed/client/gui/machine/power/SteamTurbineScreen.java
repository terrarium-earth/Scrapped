package dev.terrarium.minefactoryrenewed.client.gui.machine.power;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.SteamTurbineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.SteamTurbineBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.generator.GeneratorScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SteamTurbineScreen extends GeneratorScreen<SteamTurbineBlockEntity, SteamTurbineContainer> {

    private static final ResourceLocation STEAM_TURBINE_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/steam_turbine_gui.png");

    public SteamTurbineScreen(SteamTurbineContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, STEAM_TURBINE_GUI);
    }
}
