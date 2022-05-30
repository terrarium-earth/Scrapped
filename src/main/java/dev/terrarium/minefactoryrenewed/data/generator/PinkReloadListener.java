package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;
import dev.terrarium.minefactoryrenewed.api.item.Pink;

public class PinkReloadListener extends GeneratorReloadListener<GeneratorItem> {

    public PinkReloadListener() {
        super("pink", GeneratorItem.CODEC, GeneratorItemManager.getPink()::addEntry, GeneratorItemManager.getPink()::clear);
    }
}