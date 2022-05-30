package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;

public class SlimeyReloadListener extends GeneratorReloadListener<GeneratorItem> {

    public SlimeyReloadListener() {
        super("slimey", GeneratorItem.CODEC, GeneratorItemManager.getSlimey()::addEntry, GeneratorItemManager.getSlimey()::clear);
    }
}