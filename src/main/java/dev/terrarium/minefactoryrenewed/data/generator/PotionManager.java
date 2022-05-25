package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.PotionData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.HashMap;
import java.util.Map;

public class PotionManager {

    private static final PotionManager INSTANCE = new PotionManager();
    private final Map<ResourceLocation, PotionData> potionData = new HashMap<>();

    public boolean isPotionBurnable(ItemStack stack) {
        if(stack.is(Items.SPLASH_POTION) || stack.is(Items.LINGERING_POTION) || stack.is(Items.POTION)) {
            Potion potion = PotionUtils.getPotion(stack);
            return potionData.containsKey(potion.getRegistryName());
        }
        return false;
    }

    public int getBurnTime(ItemStack stack) {
        return potionData.get(getPotion(stack)).burnTime();
    }

    public int getEnergyGen(ItemStack stack) {
        return (int) (potionData.get(getPotion(stack)).energyGen() * getMultiplier(stack));
    }

    public void addEntry(PotionData pinkEntry) {
        this.potionData.put(pinkEntry.resourceLocation(), pinkEntry);
    }

    public void clear() {
        potionData.clear();
    }

    public ResourceLocation getPotion(ItemStack stack) {
        return PotionUtils.getPotion(stack).getRegistryName();
    }

    public double getMultiplier(ItemStack stack) {
        if(stack.is(Items.POTION)) return 1.0;
        if(stack.is(Items.SPLASH_POTION)) return 1.25;
        if(stack.is(Items.LINGERING_POTION)) return 1.75;
        return 0;
    }

    public static PotionManager getInstance() {
        return INSTANCE;
    }
}
