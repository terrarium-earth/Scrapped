package dev.terrarium.minefactoryrenewed.compat;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.generator.GeneratorBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.generator.LavaGenBlockEntity;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class TOPCompat implements Function<ITheOneProbe, Void> {

    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        theOneProbe.registerProvider(new IProbeInfoProvider() {
            @Override
            public ResourceLocation getID() {
                return new ResourceLocation(MinefactoryRenewed.MODID, "default");
            }

            @Override
            public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, Player player, Level level, BlockState blockState, IProbeHitData hitData) {
                if (level.getBlockEntity(hitData.getPos()) instanceof GeneratorBlockEntity generator) {
                    if(generator instanceof LavaGenBlockEntity lavaGenBlock) {
                        probeInfo.tank(lavaGenBlock.getTank());
                    }
                    probeInfo.text(generator.getDisplayText());
                }
            }
        });

        return null;
    }
}
