package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;

public class ExplosiveReloadListener extends GeneratorReloadListener<GeneratorItem> {

    public ExplosiveReloadListener() {
        super("explosive", GeneratorItem.CODEC, GeneratorItemManager.getExplosive()::addEntry, GeneratorItemManager.getExplosive()::clear);
    }
}