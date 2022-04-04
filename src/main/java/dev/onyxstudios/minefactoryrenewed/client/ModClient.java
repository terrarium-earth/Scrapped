package dev.onyxstudios.minefactoryrenewed.client;

import dev.onyxstudios.minefactoryrenewed.client.entity.PinkSlimeRenderer;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FarmerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FertilizerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FruitPickerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.PlanterScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.mobs.SlaughterhouseScreen;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import dev.onyxstudios.minefactoryrenewed.registry.ModEntities;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ModClient {

    public static void init() {
        initLayers();
        initScreens();
        initEntities();
    }

    private static void initLayers() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_SLIME_BLOCK.get(), RenderType.translucent());
    }

    private static void initScreens() {
        MenuScreens.register(ModBlockEntities.PLANTER_CONTAINER.get(), PlanterScreen::new);
        MenuScreens.register(ModBlockEntities.FARMER_CONTAINER.get(), FarmerScreen::new);
        MenuScreens.register(ModBlockEntities.FERTILIZER_CONTAINER.get(), FertilizerScreen::new);
        MenuScreens.register(ModBlockEntities.FRUIT_PICKER_CONTAINER.get(), FruitPickerScreen::new);
        MenuScreens.register(ModBlockEntities.SLAUGHTERHOUSE_CONTAINER.get(), SlaughterhouseScreen::new);
    }

    private static void initEntities() {
        EntityRenderers.register(ModEntities.SAFARI_NET.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.PINK_SLIME.get(), PinkSlimeRenderer::new);
    }
}
