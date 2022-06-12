package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class ExplosiveReloadListener extends GenericReloadListener<GeneratorItem> {

    public ExplosiveReloadListener() {
        super("explosive", GeneratorItem.CODEC, GeneratorItemManager.getExplosive()::addEntry, GeneratorItemManager.getExplosive()::clear);
    }
}