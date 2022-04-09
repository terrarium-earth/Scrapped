package dev.onyxstudios.minefactoryrenewed.client.gui.machine.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs.AutoSpawnerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.AutoSpawnerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.MachineScreen;
import dev.onyxstudios.minefactoryrenewed.network.ModPackets;
import dev.onyxstudios.minefactoryrenewed.network.SpawnerButtonMessage;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AutoSpawnerScreen extends MachineScreen<AutoSpawnerContainer> {

    private static final ResourceLocation AUTO_SPAWNER_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/auto_spawner_gui.png");
    private Button spawnExact;

    public AutoSpawnerScreen(AutoSpawnerContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, AUTO_SPAWNER_GUI);
        this.imageHeight = 166;
        this.inventoryLabelY = imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        AutoSpawnerBlockEntity autoSpawner = (AutoSpawnerBlockEntity) menu.getBlockEntity();
        this.spawnExact = this.addRenderableWidget(new Button(
                getGuiLeft() + 29, getGuiTop() + 20, 95, 20,
                new TranslatableComponent("gui.button.auto_spawner.spawn_exact",
                        (autoSpawner.spawnExact() ? "Yes" : "No")),
                button -> {
                    boolean exact = !autoSpawner.spawnExact();
                    ModPackets.INSTANCE.sendToServer(new SpawnerButtonMessage(autoSpawner.getBlockPos(), exact));
                    autoSpawner.setSpawnExact(exact);
                    button.setMessage(new TranslatableComponent("gui.button.auto_spawner.spawn_exact",
                            (autoSpawner.spawnExact() ? "Yes" : "No")));
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
