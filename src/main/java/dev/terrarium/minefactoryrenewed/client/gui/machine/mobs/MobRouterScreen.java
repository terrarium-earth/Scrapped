package dev.terrarium.minefactoryrenewed.client.gui.machine.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.mobs.MobRouterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.mobs.MobRouterBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import dev.terrarium.minefactoryrenewed.network.ModPackets;
import dev.terrarium.minefactoryrenewed.network.RouterButtonMessage;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MobRouterScreen extends MachineScreen<MobRouterContainer> {

    private static final ResourceLocation MOB_ROUTER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/mob_router_gui.png");

    public MobRouterScreen(MobRouterContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, MOB_ROUTER_GUI, false);
    }

    @Override
    protected void init() {
        super.init();
        MobRouterBlockEntity mobRouter = (MobRouterBlockEntity) menu.getBlockEntity();
        this.addRenderableWidget(new Button(
                getGuiLeft() + 29, getGuiTop() + 20, 110, 20,
                new TranslatableComponent("gui.button.mob_router." +
                        (mobRouter.whitelist() ? "whitelist" : "blacklist")),
                button -> {
                    boolean whitelist = !mobRouter.whitelist();
                    ModPackets.INSTANCE.sendToServer(new RouterButtonMessage(
                            mobRouter.getBlockPos(), whitelist, mobRouter.allowBabies()));
                    mobRouter.setWhitelist(whitelist);
                    button.setMessage(new TranslatableComponent("gui.button.mob_router." +
                            (mobRouter.whitelist() ? "whitelist" : "blacklist")));
                }
        ));

        this.addRenderableWidget(new Button(
                getGuiLeft() + 29, getGuiTop() + 41, 110, 20,
                new TranslatableComponent("gui.button.mob_router.allow_babies",
                        mobRouter.allowBabies() ? "Yes" : "No"),
                button -> {
                    boolean allowBabies = !mobRouter.allowBabies();
                    ModPackets.INSTANCE.sendToServer(new RouterButtonMessage(
                            mobRouter.getBlockPos(), mobRouter.whitelist(), allowBabies));
                    mobRouter.setAllowBabies(allowBabies);
                    button.setMessage(new TranslatableComponent("gui.button.mob_router.allow_babies",
                            mobRouter.allowBabies() ? "Yes" : "No"));
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
