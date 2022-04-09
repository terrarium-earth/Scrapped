package dev.onyxstudios.minefactoryrenewed.client;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.client.entity.PinkSlimeRenderer;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FarmerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FertilizerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FruitPickerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.PlanterScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.mobs.*;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import dev.onyxstudios.minefactoryrenewed.registry.ModEntities;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = MinefactoryRenewed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClient {

    public static ShaderInstance RAINBOW_SHADER;
    public static Uniform TIME;

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
        MenuScreens.register(ModBlockEntities.GRINDER_CONTAINER.get(), GrinderScreen::new);
        MenuScreens.register(ModBlockEntities.MEAT_PACKER_CONTAINER.get(), MeatPackerScreen::new);
        MenuScreens.register(ModBlockEntities.MOB_ROUTER_CONTAINER.get(), MobRouterScreen::new);
        MenuScreens.register(ModBlockEntities.AUTO_SPAWNER_CONTAINER.get(), AutoSpawnerScreen::new);
    }

    private static void initEntities() {
        EntityRenderers.register(ModEntities.SAFARI_NET.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.PINK_SLIME.get(), PinkSlimeRenderer::new);
    }

    @SubscribeEvent
    public static void registerShaderEvent(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceManager(),
                        new ResourceLocation(MinefactoryRenewed.MODID, "rainbow"), DefaultVertexFormat.NEW_ENTITY),
                shaderInstance -> {
                    RAINBOW_SHADER = shaderInstance;
                    TIME = shaderInstance.getUniform("Time");
                });
    }
}
