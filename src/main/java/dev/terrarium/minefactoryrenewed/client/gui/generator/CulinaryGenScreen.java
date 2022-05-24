package dev.terrarium.minefactoryrenewed.client.gui.generator;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.CulinaryGenContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.CulinaryGenBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CulinaryGenScreen extends GeneratorScreen<CulinaryGenBlockEntity, CulinaryGenContainer> {

    private static final ResourceLocation CULINARY_GEN_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/culinary_generator_gui.png");

    public CulinaryGenScreen(CulinaryGenContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, CULINARY_GEN_GUI);
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        CulinaryGenBlockEntity generator = menu.getGenerator();
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
