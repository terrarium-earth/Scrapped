package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.PotionData;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class PotionReloadListener extends GenericReloadListener<PotionData> {

    public PotionReloadListener() {
        super("potion", PotionData.CODEC, PotionManager.getInstance()::addEntry, PotionManager.getInstance()::clear);
    }
}
