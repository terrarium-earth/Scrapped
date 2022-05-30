package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Ender;

public class EnderReloadListener extends GeneratorReloadListener<Ender> {

    public EnderReloadListener() {
        super("ender", Ender.CODEC, GeneratorItemManager.getEnder()::addEntry, GeneratorItemManager.getEnder()::clear);
    }
}