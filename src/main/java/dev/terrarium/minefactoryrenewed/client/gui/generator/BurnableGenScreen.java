package dev.terrarium.minefactoryrenewed.client.gui.generator;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.BurnableGenContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.BurnableGenBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BurnableGenScreen extends GeneratorScreen<BurnableGenBlockEntity, BurnableGenContainer> {

    private static final ResourceLocation BURNABLE_GEN_SCREEN = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/burnable_generator_gui.png");

    public BurnableGenScreen(BurnableGenContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, BURNABLE_GEN_SCREEN);
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        BurnableGenBlockEntity generator = menu.getGenerator();
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
