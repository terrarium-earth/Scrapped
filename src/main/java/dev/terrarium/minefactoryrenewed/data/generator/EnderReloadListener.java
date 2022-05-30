package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Ender;
import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;

public class EnderReloadListener extends GeneratorReloadListener<GeneratorItem> {

    public EnderReloadListener() {
        super("ender", GeneratorItem.CODEC, GeneratorItemManager.getEnder()::addEntry, GeneratorItemManager.getEnder()::clear);
    }
}