package dev.terrarium.minefactoryrenewed.blockentity.machine.blocks;

import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BlockBreakerBlockEntity extends MachineBlockEntity {

    public BlockBreakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLOCK_BREAKER.get(), pos, state);
        this.createEnergy(32000, 960);
        this.setMaxWorkTime(2);
        this.setMaxIdleTime(20);

        this.createMachineArea(pos, Direction.NORTH, 0);
        this.getMachineArea().setOneBlock(true);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        Direction direction = getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        BlockPos pos = getBlockPos().relative(direction);
        if (!level.isEmptyBlock(pos) && level.getBlockEntity(pos) == null) {
            BlockState state = level.getBlockState(pos);

            if (Items.IRON_PICKAXE.isCorrectToolForDrops(state) || Items.IRON_AXE.isCorrectToolForDrops(state) ||
                    Items.IRON_SHOVEL.isCorrectToolForDrops(state) || Items.AIR.isCorrectToolForDrops(state)) {
                insertOrDropItems(state.getDrops(createBuilder((ServerLevel) level, state, pos)));
                level.destroyBlock(pos, false);
                useEnergy();
                return true;
            }
        }

        return false;
    }

    public LootContext.Builder createBuilder(ServerLevel serverLevel, BlockState state, BlockPos breakPos) {
        ItemStack tool = new ItemStack(Items.IRON_PICKAXE);

        if (Items.IRON_SHOVEL.isCorrectToolForDrops(state)) tool = new ItemStack(Items.IRON_SHOVEL);
        if (Items.IRON_AXE.isCorrectToolForDrops(state)) tool = new ItemStack(Items.IRON_AXE);

        return new LootContext.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(breakPos))
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.BLOCK_ENTITY, this)
                .withParameter(LootContextParams.TOOL, tool);
    }
}
