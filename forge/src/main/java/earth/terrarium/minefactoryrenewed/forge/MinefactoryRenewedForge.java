package earth.terrarium.minefactoryrenewed.forge;

import earth.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MinefactoryRenewed.MOD_ID)
public class MinefactoryRenewedForge {
    public MinefactoryRenewedForge() {
        MinefactoryRenewed.init();
    }
}