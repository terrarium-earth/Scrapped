package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Hellish;

public class HellishReloadListener extends GeneratorReloadListener<Hellish> {

    public HellishReloadListener() {
        super("hellish", Hellish.CODEC, HellishManager.getInstance()::addEntry, HellishManager.getInstance()::clear);
    }
}