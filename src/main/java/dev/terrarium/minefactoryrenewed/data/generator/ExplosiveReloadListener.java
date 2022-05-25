package dev.terrarium.minefactoryrenewed.data.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.api.item.Explosive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ExplosiveReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static final String FOLDER_ID = "generators";

    public ExplosiveReloadListener() {
        super(GSON, FOLDER_ID);
    }

    @Override
    public void apply(Map<ResourceLocation, JsonElement> elements, @NotNull ResourceManager manager, @NotNull ProfilerFiller filter) {
        ExplosiveManager.getInstance().clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : elements.entrySet()) {
            ResourceLocation id = entry.getKey();
            if (!id.getNamespace().equals(MinefactoryRenewed.MODID) || !id.getPath().startsWith("explosive/")) continue;
            Explosive explosive = Explosive.CODEC.parse(JsonOps.INSTANCE, entry.getValue().getAsJsonObject())
                    .getOrThrow(false, s ->
                            MinefactoryRenewed.LOGGER.error("Unable to load Explosive data for {}, \n{}", id.toString(), s));

            ExplosiveManager.getInstance().addEntry(explosive);
        }
    }
}
