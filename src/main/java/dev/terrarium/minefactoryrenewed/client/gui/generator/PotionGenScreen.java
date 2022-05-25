package dev.terrarium.minefactoryrenewed.client.gui.generator;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.GeneratorContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.PotionGenContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.PinkGenBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.generator.PotionGenBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PotionGenScreen extends GeneratorScreen<PotionGenBlockEntity, PotionGenContainer> {

    private static final ResourceLocation POTION_GEN_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/potion_generator_gui.png");

    public PotionGenScreen(PotionGenContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, POTION_GEN_GUI);
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        PotionGenBlockEntity generator = menu.getGenerator();
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
