package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class FrostyReloadListener extends GenericReloadListener<GeneratorItem> {

    public FrostyReloadListener() {
        super("frosty", GeneratorItem.CODEC, GeneratorItemManager.getFrosty()::addEntry, GeneratorItemManager.getFrosty()::clear);
    }
}