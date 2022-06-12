package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Hellish;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class HellishReloadListener extends GenericReloadListener<Hellish> {

    public HellishReloadListener() {
        super("hellish", Hellish.CODEC, HellishManager.getInstance()::addEntry, HellishManager.getInstance()::clear);
    }
}