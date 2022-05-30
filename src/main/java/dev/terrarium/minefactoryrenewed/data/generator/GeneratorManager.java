package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Hellish;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public abstract class GeneratorManager<T> extends HashMap<Item, T> {
    private static final HellishManager INSTANCE = new HellishManager();

    public boolean isValid(ItemStack stack);

    public int getBurnTime(ItemStack stack);

    public int getEnergyGen(ItemStack stack);

    public static HellishManager getInstance() {
        return INSTANCE;
    }
}
