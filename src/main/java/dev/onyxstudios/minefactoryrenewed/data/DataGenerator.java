package dev.onyxstudios.minefactoryrenewed.data;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = MinefactoryRenewed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (event.includeClient()) {
            event.getGenerator().addProvider(new ItemModels(event.getGenerator(), event.getExistingFileHelper()));
        }
    }
}
