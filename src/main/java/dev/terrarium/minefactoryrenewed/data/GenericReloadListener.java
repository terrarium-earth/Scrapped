package dev.terrarium.minefactoryrenewed.data;

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

public class GenericReloadListener<T> extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static final String FOLDER_ID = "generators";
    private final Codec<T> dataType;
    private final Consumer<T> consumer;
    private final String path;
    private final String folder;
    private final Runnable clear;

    public GenericReloadListener(String path, Codec<T> dataType, Consumer<T> consumer, Runnable clear, String folder) {
        super(GSON, folder);
        this.folder = folder;
        this.path = path;
        this.dataType = dataType;
        this.consumer = consumer;
        this.clear = clear;
    }

    public GenericReloadListener(String path, Codec<T> dataType, Consumer<T> consumer, Runnable clear) {
        this(path, dataType, consumer, clear, FOLDER_ID);
    }

    @Override
    public void apply(Map<ResourceLocation, JsonElement> elements, @NotNull ResourceManager manager, @NotNull ProfilerFiller filter) {
        //clears
        clear.run();

        for (Map.Entry<ResourceLocation, JsonElement> entry : elements.entrySet()) {
            ResourceLocation id = entry.getKey();
            if (!id.getNamespace().equals(MinefactoryRenewed.MODID) || (path != null && !id.getPath().startsWith(path + "/"))) continue;
            T dataEntry = dataType.parse(JsonOps.INSTANCE, entry.getValue().getAsJsonObject())
                    .getOrThrow(false, s ->
                            MinefactoryRenewed.LOGGER.error("Unable to load" + (path != null ? path : folder) + "data for {}, \n{}", id.toString(), s));
            consumer.accept(dataEntry);
        }
    }
}