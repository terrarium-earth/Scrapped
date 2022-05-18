package dev.terrarium.minefactoryrenewed.data;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.api.item.Pickable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class PickableManager {

    private static final PickableManager INSTANCE = new PickableManager();
    private final Map<Block, Pickable> pickables = new HashMap<>();

    public void addEntry(Pickable pickable) {
        if (!(pickable.plant() instanceof BlockItem blockItem)) {
            MinefactoryRenewed.LOGGER.error("Item is not a valid pickable. Fruit picker entries must be BlockItems! {}",
                    pickable.plant().getRegistryName());
            return;
        }

        pickables.put(blockItem.getBlock(), pickable);
    }

    public Pickable getPickable(Block block) {
        return pickables.get(block);
    }

    public boolean isPickable(Item item) {
        if (!(item instanceof BlockItem blockItem)) return false;

        return pickables.containsKey(blockItem.getBlock());
    }

    public boolean isPickable(Block block) {
        return pickables.containsKey(block);
    }

    public void clear() {
        pickables.clear();
    }

    public static PickableManager getInstance() {
        return INSTANCE;
    }
}
