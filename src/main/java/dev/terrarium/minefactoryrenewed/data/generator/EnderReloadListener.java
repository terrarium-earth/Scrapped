package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class EnderReloadListener extends GenericReloadListener<GeneratorItem> {

    public EnderReloadListener() {
        super("ender", GeneratorItem.CODEC, GeneratorItemManager.getEnder()::addEntry, GeneratorItemManager.getEnder()::clear);
    }
}