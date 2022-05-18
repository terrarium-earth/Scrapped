package dev.terrarium.minefactoryrenewed.client.gui.machine.enchantment;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.enchantment.AutoEnchanterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment.AutoEnchanterBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import dev.terrarium.minefactoryrenewed.network.EnchanterButtonMessage;
import dev.terrarium.minefactoryrenewed.network.ModPackets;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AutoEnchanterScreen extends MachineScreen<AutoEnchanterContainer> {

    private static final ResourceLocation ENCHANTER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/auto_enchanter_gui.png");

    public AutoEnchanterScreen(AutoEnchanterContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, ENCHANTER_GUI, false);
    }

    @Override
    protected void init() {
        super.init();
        AutoEnchanterBlockEntity autoEnchanter = (AutoEnchanterBlockEntity) menu.getBlockEntity();
        this.addRenderableWidget(new Button(
                getGuiLeft() + 8, getGuiTop() + 16, 20, 20,
                new TextComponent("+"),
                button -> {
                    autoEnchanter.increaseLevel();
                    ModPackets.INSTANCE.sendToServer(new EnchanterButtonMessage(autoEnchanter.getBlockPos(), autoEnchanter.getEnchantLevel()));
                }
        ));

        this.addRenderableWidget(new Button(
                getGuiLeft() + 8, getGuiTop() + 50, 20, 20,
                new TextComponent("-"),
                button -> {
                    autoEnchanter.decreaseLevel();
                    ModPackets.INSTANCE.sendToServer(new EnchanterButtonMessage(autoEnchanter.getBlockPos(), autoEnchanter.getEnchantLevel()));
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
        AutoEnchanterBlockEntity machine = (AutoEnchanterBlockEntity) menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
        renderIdle(poseStack, machine.getIdleTime(), machine.getMaxIdleTime());

        String level = String.valueOf(machine.getEnchantLevel());
        font.draw(poseStack, String.valueOf(machine.getEnchantLevel()),
                getGuiLeft() + (18 - (font.width(level) / 2.0f)), getGuiTop() + 40, 0xFFFFFF);
    }
}
