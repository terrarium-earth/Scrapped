package dev.terrarium.minefactoryrenewed.network;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MinefactoryRenewed.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        INSTANCE.registerMessage(0, HarvesterButtonMessage.class, HarvesterButtonMessage::encode,
                HarvesterButtonMessage::decode, HarvesterButtonMessage::handleMessage);
        INSTANCE.registerMessage(1, RouterButtonMessage.class, RouterButtonMessage::encode,
                RouterButtonMessage::decode, RouterButtonMessage::handleMessage);
        INSTANCE.registerMessage(2, SpawnerButtonMessage.class, SpawnerButtonMessage::encode,
                SpawnerButtonMessage::decode, SpawnerButtonMessage::handleMessage);
        INSTANCE.registerMessage(3, ChronotyperButtonMessage.class, ChronotyperButtonMessage::encode,
                ChronotyperButtonMessage::decode, ChronotyperButtonMessage::handleMessage);
        INSTANCE.registerMessage(4, SmasherButtonMessage.class, SmasherButtonMessage::encode,
                SmasherButtonMessage::decode, SmasherButtonMessage::handleMessage);
        INSTANCE.registerMessage(5, EnchanterButtonMessage.class, EnchanterButtonMessage::encode,
                EnchanterButtonMessage::decode, EnchanterButtonMessage::handleMessage);
    }
}
