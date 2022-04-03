package dev.onyxstudios.minefactoryrenewed.block;

import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class PinkSlimeBlock extends SlimeBlock {

    public PinkSlimeBlock() {
        super(Properties.of(Material.CLAY)
                .strength(0.55f)
                .sound(SoundType.SLIME_BLOCK)
                .noOcclusion()
        );
    }
}
