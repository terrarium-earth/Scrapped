package dev.terrarium.minefactoryrenewed.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    @NotNull
    @Override
    public List<ItemStack> getDrops(@NotNull BlockState state, LootContext.@NotNull Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        if (drops.isEmpty()) {
            drops.add(new ItemStack(this));
        }

        return drops;
    }
}
