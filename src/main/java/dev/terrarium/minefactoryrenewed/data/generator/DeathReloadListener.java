package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;

public class DeathReloadListener extends GeneratorReloadListener<GeneratorItem> {

    public DeathReloadListener() {
        super("death", GeneratorItem.CODEC, GeneratorItemManager.getDeath()::addEntry, GeneratorItemManager.getDeath()::clear);
    }
}