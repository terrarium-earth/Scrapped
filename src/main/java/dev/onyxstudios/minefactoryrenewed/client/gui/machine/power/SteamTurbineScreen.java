package dev.onyxstudios.minefactoryrenewed.client.gui.machine.power;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.power.SteamTurbineContainer;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SteamTurbineScreen extends MachineScreen<SteamTurbineContainer> {

    private static final ResourceLocation STEAM_TURBINE_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/steam_turbine_gui.png");

    public SteamTurbineScreen(SteamTurbineContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, STEAM_TURBINE_GUI, false);
        this.powerBarX = idleBarX;
        this.fluidBarX = 147;
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
    }
}
