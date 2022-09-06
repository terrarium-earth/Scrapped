package earth.terrarium.minefactoryrenewed.fabric;

import earth.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.fabricmc.api.ModInitializer;

public class MinefactoryRenewedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MinefactoryRenewed.init();
    }
}