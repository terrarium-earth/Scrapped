package dev.terrarium.minefactoryrenewed.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.jetbrains.annotations.NotNull;

public class EnergyCellModelLoader implements IModelLoader<EnergyCellModelGeometry> {

    public static final EnergyCellModelLoader INSTANCE = new EnergyCellModelLoader();

    @NotNull
    @Override
    public EnergyCellModelGeometry read(@NotNull JsonDeserializationContext deserializationContext, @NotNull JsonObject modelContents) {
        ModelLoaderRegistry.VanillaProxy proxy = ModelLoaderRegistry.VanillaProxy.Loader.INSTANCE.read(deserializationContext, modelContents);
        return new EnergyCellModelGeometry(proxy);
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
    }
}
