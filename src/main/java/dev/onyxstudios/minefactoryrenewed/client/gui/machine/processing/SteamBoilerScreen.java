package dev.onyxstudios.minefactoryrenewed.client.gui.machine.processing;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.processing.SteamBoilerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.processing.SteamBoilerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SteamBoilerScreen extends MachineScreen<SteamBoilerContainer> {

    private static final ResourceLocation STEAM_BOILER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/steam_boiler_gui.png");

    public SteamBoilerScreen(SteamBoilerContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, STEAM_BOILER_GUI, false);
        this.fluidBarX = 138;
        this.fluidBarY = 69;
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        SteamBoilerBlockEntity machine = (SteamBoilerBlockEntity) menu.getBlockEntity();
        renderBarTooltip(poseStack, machine.getWorkTime(), machine.getMaxWorkTime(), workBarX, workBarY, x, y, "Wk");
        renderBarTooltip(poseStack, machine.getIdleTime(), machine.getMaxIdleTime(), idleBarX, idleBarY, x, y, "Ticks");

        renderBarTooltip(
                poseStack,
                machine.getSteamTank().getFluidAmount(),
                machine.getSteamTank().getCapacity(),
                8, fluidBarY,
                fluidBarWidth, fluidBarHeight,
                x, y,
                "MB Steam"
        );

        renderBarTooltip(
                poseStack,
                machine.getTemperature(),
                SteamBoilerBlockEntity.MAX_TEMPERATURE,
                80, 69,
                18, 18,
                x, y,
                "\u00B0C"
        );
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        SteamBoilerBlockEntity machine = (SteamBoilerBlockEntity) menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
        renderIdle(poseStack, machine.getIdleTime(), machine.getMaxIdleTime());

        int burnTime = machine.getBurnTime();
        if (burnTime > 0) {
            int i = (int) (13 * (burnTime / (float) machine.getMaxBurnTime()));
            this.blit(
                    poseStack,
                    getGuiLeft() + 80, getGuiTop() + 54 + (13 - i),
                    180, 69 - i,
                    13, i
            );
        }

        if (machine.getSteamTank().getFluidAmount() > 0)
            renderFluid(poseStack, machine.getSteamTank(), 8, fluidBarY, fluidBarWidth, fluidBarHeight);
    }
}
