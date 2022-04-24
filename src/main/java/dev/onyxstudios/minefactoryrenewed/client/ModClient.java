package dev.onyxstudios.minefactoryrenewed.client;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.client.blockentity.LaserChargerRenderer;
import dev.onyxstudios.minefactoryrenewed.client.blockentity.LaserDrillRenderer;
import dev.onyxstudios.minefactoryrenewed.client.blockentity.SteamTurbineRenderer;
import dev.onyxstudios.minefactoryrenewed.client.entity.PinkSlimeRenderer;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.animals.*;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.blocks.BlockPlacerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.blocks.BlockSmasherScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.blocks.DeepStorageScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.enchantment.AutoAnvilScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.enchantment.AutoDisenchanterScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.enchantment.AutoEnchanterScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FarmerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FertilizerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.FruitPickerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.farming.PlanterScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.mobs.*;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.power.EthanolGeneratorScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.power.SteamTurbineScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.processing.EthanolReactorScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.processing.LaserChargerScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.processing.LaserDrillScreen;
import dev.onyxstudios.minefactoryrenewed.client.gui.machine.processing.SteamBoilerScreen;
import dev.onyxstudios.minefactoryrenewed.item.FocusItem;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import dev.onyxstudios.minefactoryrenewed.registry.ModEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = MinefactoryRenewed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClient {

    private static final ResourceLocation BLADES_LOCATION = new ResourceLocation(MinefactoryRenewed.MODID, "block/steam_turbine_blades");
    public static BakedModel BLADES_MODEL;

    public static ShaderInstance RAINBOW_SHADER;
    public static Uniform TIME;

    public static void init(FMLClientSetupEvent event) {
        initLayers();
        initScreens();
        initEntities();
        initBlockEntities();
        initColors(event);
    }

    private static void initLayers() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_SLIME_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.STEAM_TURBINE.get(), RenderType.translucent());
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
        MenuScreens.register(ModBlockEntities.FISHER_CONTAINER.get(), FisherScreen::new);
        MenuScreens.register(ModBlockEntities.RANCHER_CONTAINER.get(), RancherScreen::new);
        MenuScreens.register(ModBlockEntities.BREEDER_CONTAINER.get(), BreederScreen::new);
        MenuScreens.register(ModBlockEntities.CHRONOTYPER_CONTAINER.get(), ChronotyperScreen::new);
        MenuScreens.register(ModBlockEntities.SEWER_CONTAINER.get(), SewerScreen::new);
        MenuScreens.register(ModBlockEntities.VETERINARY_CONTAINER.get(), VeterinaryScreen::new);
        MenuScreens.register(ModBlockEntities.BLOCK_PLACER_CONTAINER.get(), BlockPlacerScreen::new);
        MenuScreens.register(ModBlockEntities.BLOCK_SMASHER_CONTAINER.get(), BlockSmasherScreen::new);
        MenuScreens.register(ModBlockEntities.DEEP_STORAGE_CONTAINER.get(), DeepStorageScreen::new);
        MenuScreens.register(ModBlockEntities.AUTO_DISENCHANTER_CONTAINER.get(), AutoDisenchanterScreen::new);
        MenuScreens.register(ModBlockEntities.AUTO_ENCHANTER_CONTAINER.get(), AutoEnchanterScreen::new);
        MenuScreens.register(ModBlockEntities.AUTO_ANVIL_CONTAINER.get(), AutoAnvilScreen::new);
        MenuScreens.register(ModBlockEntities.LASER_DRILL_CONTAINER.get(), LaserDrillScreen::new);
        MenuScreens.register(ModBlockEntities.LASER_CHARGER_CONTAINER.get(), LaserChargerScreen::new);
        MenuScreens.register(ModBlockEntities.STEAM_BOILER_CONTAINER.get(), SteamBoilerScreen::new);
        MenuScreens.register(ModBlockEntities.STEAM_TURBINE_CONTAINER.get(), SteamTurbineScreen::new);
        MenuScreens.register(ModBlockEntities.ETHANOL_GENERATOR_CONTAINER.get(), EthanolGeneratorScreen::new);
        MenuScreens.register(ModBlockEntities.ETHANOL_REACTOR_CONTAINER.get(), EthanolReactorScreen::new);
    }

    private static void initEntities() {
        EntityRenderers.register(ModEntities.SAFARI_NET.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.PINK_SLIME.get(), PinkSlimeRenderer::new);
    }

    private static void initBlockEntities() {
        BlockEntityRenderers.register(ModBlockEntities.LASER_CHARGER.get(), ctx -> new LaserChargerRenderer());
        BlockEntityRenderers.register(ModBlockEntities.LASER_DRILL.get(), ctx -> new LaserDrillRenderer());
        BlockEntityRenderers.register(ModBlockEntities.STEAM_TURBINE.get(), ctx -> new SteamTurbineRenderer());
    }

    private static void initColors(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            for (RegistryObject<Item> entry : ModItems.ITEMS.getEntries()) {
                if (entry.get() instanceof FocusItem focusItem) {
                    minecraft.getItemColors().register((stack, tintIndex) ->
                            tintIndex == 1 ? focusItem.getColor().getFireworkColor() : 0xFFFFFF, focusItem);
                }
            }
        });
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

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        ForgeModelBakery.addSpecialModel(BLADES_LOCATION);
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        BLADES_MODEL = event.getModelManager().getModel(BLADES_LOCATION);
    }
}
