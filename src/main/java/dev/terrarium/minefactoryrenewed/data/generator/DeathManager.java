package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Death;
import dev.terrarium.minefactoryrenewed.api.item.Pink;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class DeathManager {

    private static final DeathManager INSTANCE = new DeathManager();
    private final Map<Item, Death> items = new HashMap<>();

    public boolean isValid(ItemStack stack) {
        return items.containsKey(stack.getItem());
    }

    public int getBurnTime(ItemStack stack) {
        return items.get(stack.getItem()).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return items.get(stack.getItem()).energyGen();
    }

    public void addEntry(Death itemEntry) {
        this.items.put(itemEntry.item(), itemEntry);
    }

    public void clear() {
        items.clear();
    }

    public static DeathManager getInstance() {
        return INSTANCE;
    }
}
