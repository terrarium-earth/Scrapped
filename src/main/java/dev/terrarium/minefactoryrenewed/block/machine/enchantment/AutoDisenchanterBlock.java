package dev.terrarium.minefactoryrenewed.block.machine.enchantment;

import dev.terrarium.minefactoryrenewed.block.machine.MachineBlock;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment.AutoDisenchanterBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class AutoDisenchanterBlock extends MachineBlock {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(12, 8, 6, 16, 12, 10),
            Block.box(0, 0, 0, 4, 8, 16),
            Block.box(4, 0, 0, 12, 4, 16),
            Block.box(4, 4, 2, 12, 16, 14),
            Block.box(12, 0, 0, 16, 8, 16),
            Block.box(0, 8, 6, 4, 12, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public AutoDisenchanterBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).noOcclusion().requiresCorrectToolForDrops());
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntityType<? extends MachineBlockEntity> getBlockEntityType() {
        return ModBlockEntities.AUTO_DISENCHANTER.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AutoDisenchanterBlockEntity(pos, state);
    }
}
