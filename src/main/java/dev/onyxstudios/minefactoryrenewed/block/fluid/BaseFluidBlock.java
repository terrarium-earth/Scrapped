package dev.onyxstudios.minefactoryrenewed.block.fluid;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.registry.ModTags;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = MinefactoryRenewed.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BaseFluidBlock extends LiquidBlock {

    public BaseFluidBlock(Supplier<FlowingFluid> fluidSupplier, Properties properties) {
        super(fluidSupplier, properties);
    }

    @SubscribeEvent
    public static void onFogColor(EntityViewRenderEvent.FogColors event) {
        if (event.getCamera().getBlockAtCamera().getFluidState().is(ModTags.SLUDGE)) {
            event.setRed(9 / 255.0f);
            event.setGreen(11 / 255.0f);
            event.setBlue(29 / 255.0f);
        }
    }
}
