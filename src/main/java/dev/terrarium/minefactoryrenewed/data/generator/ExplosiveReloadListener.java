package dev.terrarium.minefactoryrenewed.data.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.api.item.Explosive;
import dev.terrarium.minefactoryrenewed.api.item.Hellish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ExplosiveReloadListener extends GeneratorReloadListener<Explosive> {

    public ExplosiveReloadListener() {
        super("explosive", Explosive.CODEC, GeneratorItemManager.getExplosive()::addEntry, GeneratorItemManager.getExplosive()::clear);
    }
}