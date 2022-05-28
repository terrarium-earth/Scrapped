package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Death;
import dev.terrarium.minefactoryrenewed.api.item.Ender;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EnderManager {

    private static final EnderManager INSTANCE = new EnderManager();
    private final Map<Item, Ender> items = new HashMap<>();

    public boolean isValid(ItemStack stack) {
        return items.containsKey(stack.getItem());
    }

    public int getBurnTime(ItemStack stack) {
        return items.get(stack.getItem()).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return items.get(stack.getItem()).energyGen();
    }

    public void addEntry(Ender itemEntry) {
        this.items.put(itemEntry.item(), itemEntry);
    }

    public void clear() {
        items.clear();
    }

    public static EnderManager getInstance() {
        return INSTANCE;
    }
}
