package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class SlimeyReloadListener extends GenericReloadListener<GeneratorItem> {

    public SlimeyReloadListener() {
        super("slimey", GeneratorItem.CODEC, GeneratorItemManager.getSlimey()::addEntry, GeneratorItemManager.getSlimey()::clear);
    }
}