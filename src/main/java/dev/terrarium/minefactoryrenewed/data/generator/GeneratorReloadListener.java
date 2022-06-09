package dev.terrarium.minefactoryrenewed.data.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class GeneratorReloadListener<T> extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static final String FOLDER_ID = "generators";
    private final Codec<T> dataType;
    private final Consumer<T> consumer;
    private final String path;
    private final Runnable clear;

    public GeneratorReloadListener(String path, Codec<T> dataType, Consumer<T> consumer, Runnable clear) {
        super(GSON, FOLDER_ID);
        this.path = path;
        this.dataType = dataType;
        this.consumer = consumer;
        this.clear = clear;
    }

    @Override
    public void apply(Map<ResourceLocation, JsonElement> elements, @NotNull ResourceManager manager, @NotNull ProfilerFiller filter) {
        //clears
        clear.run();

        for (Map.Entry<ResourceLocation, JsonElement> entry : elements.entrySet()) {
            ResourceLocation id = entry.getKey();
            if (!id.getNamespace().equals(MinefactoryRenewed.MODID) || !id.getPath().startsWith(path + "/")) continue;
            T dataEntry = dataType.parse(JsonOps.INSTANCE, entry.getValue().getAsJsonObject())
                    .getOrThrow(false, s ->
                            MinefactoryRenewed.LOGGER.error("Unable to load" + path + "data for {}, \n{}", id.toString(), s));
            consumer.accept(dataEntry);
        }
    }
}