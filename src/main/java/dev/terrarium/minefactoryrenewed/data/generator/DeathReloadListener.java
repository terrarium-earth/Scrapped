package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class DeathReloadListener extends GenericReloadListener<GeneratorItem> {

    public DeathReloadListener() {
        super("death", GeneratorItem.CODEC, GeneratorItemManager.getDeath()::addEntry, GeneratorItemManager.getDeath()::clear);
    }
}