package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.GeneratorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class GeneratorItemManager extends HashMap<Item, GeneratorItem> {

    private static final GeneratorItemManager PINK = new GeneratorItemManager();
    private static final GeneratorItemManager DEATH = new GeneratorItemManager();
    private static final GeneratorItemManager ENDER = new GeneratorItemManager();
    private static final GeneratorItemManager EXPLOSIVE = new GeneratorItemManager();
    private static final GeneratorItemManager FROSTY = new GeneratorItemManager();
    private static final GeneratorItemManager SLIMEY = new GeneratorItemManager();

    private GeneratorItemManager() {
    }

    public boolean isValid(ItemStack stack) {
        return containsKey(stack.getItem());
    }

    public int getBurnTime(ItemStack stack) {
        return get(stack.getItem()).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return get(stack.getItem()).energyGen();
    }

    public void addEntry(GeneratorItem generatorItem) {
        put(generatorItem.item(), generatorItem);
    }

    public static GeneratorItemManager getPink() {
        return PINK;
    }

    public static GeneratorItemManager getDeath() {
        return DEATH;
    }

    public static GeneratorItemManager getEnder() {
        return ENDER;
    }

    public static GeneratorItemManager getExplosive() {
        return EXPLOSIVE;
    }

    public static GeneratorItemManager getFrosty() {
        return FROSTY;
    }

    public static GeneratorItemManager getSlimey() {
        return SLIMEY;
    }
}
