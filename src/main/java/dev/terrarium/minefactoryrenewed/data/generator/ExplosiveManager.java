package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Explosive;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ExplosiveManager {

    private static final ExplosiveManager INSTANCE = new ExplosiveManager();
    private final Map<Item, Explosive> explosiveMap = new HashMap<>();

    public boolean isExplosive(ItemStack stack) {
        return explosiveMap.containsKey(stack.getItem());
    }

    public int getBurnTime(ItemStack stack) {
        return explosiveMap.get(stack.getItem()).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return explosiveMap.get(stack.getItem()).energyGen();
    }

    public void addEntry(Explosive explosiveEntry) {
        this.explosiveMap.put(explosiveEntry.item(), explosiveEntry);
    }

    public void clear() {
        explosiveMap.clear();
    }

    public static ExplosiveManager getInstance() {
        return INSTANCE;
    }
}
