package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.PotionData;

public class PotionReloadListener extends GeneratorReloadListener<PotionData> {

    public PotionReloadListener() {
        super("potion", PotionData.CODEC, PotionManager.getInstance()::addEntry, PotionManager.getInstance()::clear);
    }
}
