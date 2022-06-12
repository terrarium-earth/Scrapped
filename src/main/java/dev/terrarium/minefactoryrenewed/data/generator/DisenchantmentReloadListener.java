package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Disenchantment;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class DisenchantmentReloadListener extends GenericReloadListener<Disenchantment> {

    public DisenchantmentReloadListener() {
        super("disenchantment", Disenchantment.CODEC, DisenchantmentManager.getInstance()::addEntry, DisenchantmentManager.getInstance()::clear);
    }
}