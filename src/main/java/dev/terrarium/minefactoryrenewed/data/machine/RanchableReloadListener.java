package dev.terrarium.minefactoryrenewed.data.machine;

import dev.terrarium.minefactoryrenewed.api.item.Ranchable;
import dev.terrarium.minefactoryrenewed.data.GenericReloadListener;

public class RanchableReloadListener extends GenericReloadListener<Ranchable> {

    public RanchableReloadListener() {
        super(null, Ranchable.CODEC, RanchManager.getInstance()::addEntry, RanchManager.getInstance()::clear, "rancher");
    }
}