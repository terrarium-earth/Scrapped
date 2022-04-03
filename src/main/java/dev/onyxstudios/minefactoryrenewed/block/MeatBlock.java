package dev.onyxstudios.minefactoryrenewed.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class MeatBlock extends Block {

    public MeatBlock() {
        super(BlockBehaviour.Properties.of(Material.CLAY)
                .strength(0.55f)
                .sound(SoundType.SLIME_BLOCK)
        );
    }
}
