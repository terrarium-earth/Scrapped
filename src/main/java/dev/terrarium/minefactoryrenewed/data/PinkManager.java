package dev.terrarium.minefactoryrenewed.data;

import dev.terrarium.minefactoryrenewed.api.item.Pink;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PinkManager {

    private static final PinkManager INSTANCE = new PinkManager();
    private final Map<Item, Pink> pink = new HashMap<>();

    public boolean isPink(ItemStack stack) {
        return pink.containsKey(stack.getItem());
    }

    public int getBurnTime(ItemStack stack) {
        return pink.get(stack.getItem()).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return pink.get(stack.getItem()).energyGen();
    }

    public void addEntry(Pink pinkEntry) {
        this.pink.put(pinkEntry.item(), pinkEntry);
    }

    public void clear() {
        pink.clear();
    }

    public static PinkManager getInstance() {
        return INSTANCE;
    }
}
