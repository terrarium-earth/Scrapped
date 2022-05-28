package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Death;
import dev.terrarium.minefactoryrenewed.api.item.Slimey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SlimeyManager {

    private static final SlimeyManager INSTANCE = new SlimeyManager();
    private final Map<Item, Slimey> items = new HashMap<>();

    public boolean isValid(ItemStack stack) {
        return items.containsKey(stack.getItem());
    }

    public int getBurnTime(ItemStack stack) {
        return items.get(stack.getItem()).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return items.get(stack.getItem()).energyGen();
    }

    public void addEntry(Slimey itemEntry) {
        this.items.put(itemEntry.item(), itemEntry);
    }

    public void clear() {
        items.clear();
    }

    public static SlimeyManager getInstance() {
        return INSTANCE;
    }
}
