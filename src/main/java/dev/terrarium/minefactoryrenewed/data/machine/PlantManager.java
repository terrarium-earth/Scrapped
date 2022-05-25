package dev.terrarium.minefactoryrenewed.data.machine;

import dev.terrarium.minefactoryrenewed.api.item.Plantable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantManager {

    private static final PlantManager INSTANCE = new PlantManager();
    private final Map<Item, Plantable> plantables = new HashMap<>();

    public boolean canPlantSeeds(Level level, Item plant, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        return !belowState.isAir() &&
                (isValidSoil(plant, belowState.getBlock()) || tryReplaceSoil(level, plant, belowPos, belowState));
    }

    public boolean tryReplaceSoil(Level level, Item plant, BlockPos pos, BlockState state) {
        if (state.is(Blocks.FARMLAND) && isValidSoil(plant, Blocks.DIRT)) {
            level.setBlock(pos, Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
            return true;
        } else if ((state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK)) && isValidSoil(plant, Blocks.FARMLAND)) {
            level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), Block.UPDATE_ALL);
            return true;
        }

        return false;
    }

    public void addEntry(Plantable plantable) {
        plantables.put(plantable.seeds(), plantable);
    }

    public boolean isPlant(ItemStack stack) {
        //If not a block item, how do we plant seeds?
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem))
            return false;

        return isPlant(stack.getItem());
    }

    public boolean isPlant(Item plant) {
        return plantables.containsKey(plant);
    }

    public boolean isValidSoil(Item plant, Block soilBlock) {
        return getValidSoil(plant).contains(soilBlock);
    }

    public List<Block> getValidSoil(Item plant) {
        return plantables.get(plant).soilTypes();
    }

    public void clear() {
        plantables.clear();
    }

    public static PlantManager getInstance() {
        return INSTANCE;
    }
}
