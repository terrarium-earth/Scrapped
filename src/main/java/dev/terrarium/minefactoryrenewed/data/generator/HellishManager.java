package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Hellish;
import dev.terrarium.minefactoryrenewed.api.item.Pink;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HellishManager {

    private static final HellishManager INSTANCE = new HellishManager();
    private final Map<Item, Hellish> hellish = new HashMap<>();

    public boolean isHellish(ItemStack stack) {
        return hellish.containsKey(stack.getItem());
    }

    public boolean doesWithering(ItemStack stack) {
        return hellish.get(stack.getItem()).doesWithering();
    }

    public int getBurnTime(ItemStack stack) {
        return hellish.get(stack.getItem()).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return hellish.get(stack.getItem()).energyGen();
    }

    public void addEntry(Hellish hellishEntry) {
        this.hellish.put(hellishEntry.item(), hellishEntry);
    }

    public void clear() {
        hellish.clear();
    }

    public static HellishManager getInstance() {
        return INSTANCE;
    }
}
