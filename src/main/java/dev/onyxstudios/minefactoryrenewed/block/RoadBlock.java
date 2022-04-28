package dev.onyxstudios.minefactoryrenewed.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

public class RoadBlock extends Block {

    public RoadBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 3f).requiresCorrectToolForDrops());
    }

    @Override
    public void stepOn(Level le, BlockPos pos, BlockState sta, Entity entity) {
        super.stepOn(le, pos, sta, entity);

        float speed = 0.35f;
        Vec3 movement = entity.getDeltaMovement().multiply(speed, speed, speed);
        entity.setDeltaMovement(entity.getDeltaMovement().add(movement));
    }
}
