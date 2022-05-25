package dev.terrarium.minefactoryrenewed;

import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.client.ModClient;
import dev.terrarium.minefactoryrenewed.compat.TOPCompat;
import dev.terrarium.minefactoryrenewed.data.PickableReloadListener;
import dev.terrarium.minefactoryrenewed.data.PlantableReloadListener;
import dev.terrarium.minefactoryrenewed.item.SafariNetItem;
import dev.terrarium.minefactoryrenewed.item.syringe.SyringeItem;
import dev.terrarium.minefactoryrenewed.network.ModPackets;
import dev.terrarium.minefactoryrenewed.registry.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(MinefactoryRenewed.MODID)
public class MinefactoryRenewed {

    public static final String MODID = "minefactoryrenewed";
    public static final Logger LOGGER = LogManager.getLogger(MinefactoryRenewed.class);

    public static CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ModBlocks.FARMER_ITEM.get().getDefaultInstance();
        }
    };

    public MinefactoryRenewed() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::init);
        eventBus.addListener(this::initClient);
        eventBus.addListener(this::imcEnqueueEvent);

        MinecraftForge.EVENT_BUS.addListener(this::reloadListenerEvent);
        MinecraftForge.EVENT_BUS.addListener(SafariNetItem::entityInteract);
        MinecraftForge.EVENT_BUS.addListener(SyringeItem::entityInteract);
        MinecraftForge.EVENT_BUS.addListener(MachineBlockEntity::livingDropsEvent);

        ModItems.ITEMS.register(eventBus);
        ModEntities.ENTITIES.register(eventBus);

        ModBlocks.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        ModBlocks.FLUIDS.register(eventBus);
        ModWorldGen.PLACED_FEATURES.register(eventBus);
        ModWorldGen.CONFIGURED_FEATURES.register(eventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(eventBus);
        ModBlockEntities.CONTAINERS.register(eventBus);

        ModRecipes.RECIPES.register(eventBus);
        eventBus.addListener(ModEntities::attributeEvent);
    }

    private void init(FMLCommonSetupEvent event) {
        ModPackets.init();
        event.enqueueWork(ModRecipes::init);
    }

    private void initClient(FMLClientSetupEvent event) {
        ModClient.init(event);
    }

    private void imcEnqueueEvent(InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPCompat::new);
        }
    }

    private void reloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(new PlantableReloadListener());
        event.addListener(new PickableReloadListener());
    }
}
