package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;

public class FrostyReloadListener extends GeneratorReloadListener<GeneratorItem> {

    public FrostyReloadListener() {
        super("frosty", GeneratorItem.CODEC, GeneratorItemManager.getFrosty()::addEntry, GeneratorItemManager.getFrosty()::clear);
    }
}