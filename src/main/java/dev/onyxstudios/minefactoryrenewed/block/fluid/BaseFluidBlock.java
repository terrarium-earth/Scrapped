package dev.onyxstudios.minefactoryrenewed.block.fluid;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.registry.ModTags;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
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
        FluidState fluidState = event.getCamera().getBlockAtCamera().getFluidState();
        if (fluidState.is(ModTags.SLUDGE)) {
            event.setRed(9 / 255.0f);
            event.setGreen(11 / 255.0f);
            event.setBlue(29 / 255.0f);
        }else if (fluidState.is(ModTags.MEAT)) {
            event.setRed(227 / 255.0f);
            event.setGreen(163 / 255.0f);
            event.setBlue(130 / 255.0f);
        }else if (fluidState.is(ModTags.PINK_SLIME)) {
            event.setRed(227 / 255.0f);
            event.setGreen(134 / 255.0f);
            event.setBlue(138 / 255.0f);
        }
    }
}
