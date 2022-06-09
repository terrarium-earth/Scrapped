package dev.terrarium.minefactoryrenewed.data.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.api.item.Disenchantment;
import dev.terrarium.minefactoryrenewed.api.item.Hellish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class DisenchantmentReloadListener extends GeneratorReloadListener<Disenchantment> {

    public DisenchantmentReloadListener() {
        super("disenchantment", Disenchantment.CODEC, DisenchantmentManager.getInstance()::addEntry, DisenchantmentManager.getInstance()::clear);
    }
}