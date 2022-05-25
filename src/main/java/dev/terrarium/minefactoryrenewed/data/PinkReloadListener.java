package dev.terrarium.minefactoryrenewed.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.api.item.Pink;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class PinkReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static final String FOLDER_ID = "generators";

    public PinkReloadListener() {
        super(GSON, FOLDER_ID);
    }

    @Override
    public void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager manager, ProfilerFiller filter) {
        PinkManager.getInstance().clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : elements.entrySet()) {
            ResourceLocation id = entry.getKey();
            if (!id.getNamespace().equals(MinefactoryRenewed.MODID) || !id.getPath().startsWith("pink/")) continue;
            Pink pink = Pink.CODEC.parse(JsonOps.INSTANCE, entry.getValue().getAsJsonObject())
                    .getOrThrow(false, s ->
                            MinefactoryRenewed.LOGGER.error("Unable to load Pink data for {}, \n{}", id.toString(), s));

            PinkManager.getInstance().addEntry(pink);
        }
    }
}
