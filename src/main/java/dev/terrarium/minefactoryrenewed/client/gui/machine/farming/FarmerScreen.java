package dev.terrarium.minefactoryrenewed.client.gui.machine.farming;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.farming.FarmerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.farming.FarmerBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import dev.terrarium.minefactoryrenewed.network.HarvesterButtonMessage;
import dev.terrarium.minefactoryrenewed.network.ModPackets;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FarmerScreen extends MachineScreen<FarmerContainer> {

    private static final ResourceLocation FARMER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/farmer_gui.png");

    public FarmerScreen(FarmerContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, FARMER_GUI);
    }

    @Override
    protected void init() {
        super.init();
        FarmerBlockEntity farmer = (FarmerBlockEntity) menu.getBlockEntity();
        this.addRenderableWidget(new Button(
                getGuiLeft() + 8, getGuiTop() + 20, 117, 20,
                new TranslatableComponent("gui.button.harvester.shear_leaves",
                        farmer.shearLeaves() ? "Yes" : "No"),
                button -> {
                    boolean shearLeaves = !farmer.shearLeaves();
                    ModPackets.INSTANCE.sendToServer(new HarvesterButtonMessage(
                            farmer.getBlockPos(),
                            shearLeaves,
                            farmer.harvestSmallShrooms(),
                            farmer.harvestJungleWood()
                    ));

                    farmer.setShearLeaves(shearLeaves);
                    button.setMessage(new TranslatableComponent("gui.button.harvester.shear_leaves",
                            shearLeaves ? "Yes" : "No"));
                }
        ));

        this.addRenderableWidget(new Button(
                getGuiLeft() + 8, getGuiTop() + 41, 117, 20,
                new TranslatableComponent("gui.button.harvester.small_shrooms",
                        farmer.harvestSmallShrooms() ? "Yes" : "No"),
                button -> {
                    boolean smallShrooms = !farmer.harvestSmallShrooms();
                    ModPackets.INSTANCE.sendToServer(new HarvesterButtonMessage(
                            farmer.getBlockPos(),
                            farmer.shearLeaves(),
                            smallShrooms,
                            farmer.harvestJungleWood()
                    ));

                    farmer.setHarvestSmallShrooms(smallShrooms);
                    button.setMessage(new TranslatableComponent("gui.button.harvester.small_shrooms",
                            smallShrooms ? "Yes" : "No"));
                }
        ));

        this.addRenderableWidget(new Button(
                getGuiLeft() + 8, getGuiTop() + 62, 117, 20,
                new TranslatableComponent("gui.button.harvester.jungle_wood",
                        farmer.harvestJungleWood() ? "Yes" : "No"),
                button -> {
                    boolean jungleWood = !farmer.harvestJungleWood();
                    ModPackets.INSTANCE.sendToServer(new HarvesterButtonMessage(
                            farmer.getBlockPos(),
                            farmer.shearLeaves(),
                            farmer.harvestSmallShrooms(),
                            jungleWood
                    ));

                    farmer.setHarvestJungleWood(jungleWood);
                    button.setMessage(new TranslatableComponent("gui.button.harvester.jungle_wood",
                            jungleWood ? "Yes" : "No"));
                }
        ));
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        MachineBlockEntity machine = menu.getBlockEntity();
        renderBarTooltip(poseStack, machine.getWorkTime(), machine.getMaxWorkTime(), workBarX, workBarY, x, y, "Wk");
        renderBarTooltip(poseStack, machine.getIdleTime(), machine.getMaxIdleTime(), idleBarX, idleBarY, x, y, "Ticks");
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        MachineBlockEntity machine = menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
        renderIdle(poseStack, machine.getIdleTime(), machine.getMaxIdleTime());
    }
}
