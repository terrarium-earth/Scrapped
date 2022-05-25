package dev.terrarium.minefactoryrenewed.client.gui.generator;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.ExplosionGenContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.ExplosionGenBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExplosionGenScreen extends GeneratorScreen<ExplosionGenBlockEntity, ExplosionGenContainer> {

    private static final ResourceLocation EXPLOSION_GEN_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/furnace_generator_gui.png");

    public ExplosionGenScreen(ExplosionGenContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, EXPLOSION_GEN_GUI);
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        ExplosionGenBlockEntity generator = menu.getGenerator();
        int burnTime = generator.getBurnTime();
        if (burnTime > 0) {
            int i = (int) (13 * (burnTime / (float) generator.getMaxBurnTime()));
            this.blit(
                    poseStack,
                    getGuiLeft() + 80, getGuiTop() + 54 + (13 - i),
                    180, 69 - i,
                    13, i
            );
        }
    }
}
