package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Hellish;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class HellishManager extends HashMap<Item, Hellish> {

    private static final HellishManager INSTANCE = new HellishManager();

    public boolean isValid(ItemStack stack) {
        return containsKey(stack.getItem());
    }

    public boolean doesWithering(ItemStack stack) {
        return get(stack.getItem()).doesWithering();
    }

    public int getBurnTime(ItemStack stack) {
        return get(stack.getItem()).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return get(stack.getItem()).energyGen();
    }

    public void addEntry(Hellish hellishEntry) {
        put(hellishEntry.item(), hellishEntry);
    }

    public static HellishManager getInstance() {
        return INSTANCE;
    }
}
