package dev.onyxstudios.minefactoryrenewed;

import dev.onyxstudios.minefactoryrenewed.client.ModClient;
import dev.onyxstudios.minefactoryrenewed.data.PlantableManager;
import dev.onyxstudios.minefactoryrenewed.item.SafariNetItem;
import dev.onyxstudios.minefactoryrenewed.network.ModPackets;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import dev.onyxstudios.minefactoryrenewed.registry.ModEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MinefactoryRenewed.MODID)
public class MinefactoryRenewed {

    public static final String MODID = "minefactoryrenewed";
    public static final Logger LOGGER = LogManager.getLogger(MinefactoryRenewed.class);

    public static CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return Items.FURNACE.getDefaultInstance();
        }
    };

    public MinefactoryRenewed() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::init);
        eventBus.addListener(this::initClient);

        MinecraftForge.EVENT_BUS.addListener(SafariNetItem::entityInteract);
        MinecraftForge.EVENT_BUS.addListener(PlantableManager::reloadListenerEvent);

        ModItems.ITEMS.register(eventBus);
        ModEntities.ENTITIES.register(eventBus);

        ModBlocks.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        ModBlocks.FLUIDS.register(eventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(eventBus);
        ModBlockEntities.CONTAINERS.register(eventBus);
    }

    private void init(FMLCommonSetupEvent event) {
        ModPackets.init();
    }

    private void initClient(FMLClientSetupEvent event) {
        ModClient.init();
    }
}
