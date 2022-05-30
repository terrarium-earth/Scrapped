package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Disenchantment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.*;

public class DisenchantmentManager {

    private static final DisenchantmentManager INSTANCE = new DisenchantmentManager();
    private final Map<ResourceLocation, Disenchantment> enchantments = new HashMap<>();

    public boolean isEnchantmentBurnable(ItemStack stack) {
        if(stack.isEnchanted() || stack.is(Items.ENCHANTED_BOOK)) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            for(Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
                if(this.enchantments.containsKey(enchantment.getKey().getRegistryName())) return true;
            }
        }
        return false;
    }

    /**
     * Returns the burn time of the longest burning enchant on the itemstack
     * @param stack
     * @return
     */
    public int getBurnTime(ItemStack stack) {
        Set<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet();
        return enchantments.stream().mapToInt(value -> this.enchantments.get(value.getRegistryName()).burnTime()).max().orElse(0);
    }

    public int getEnergyGen(ItemStack stack) {
        int energyGen = 0;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        for(Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
            if(this.enchantments.containsKey(enchantment.getKey().getRegistryName())) {
                double multiplier = getMultiplier(enchantment.getKey(), enchantment.getValue());
                energyGen += this.enchantments.get(enchantment.getKey().getRegistryName()).energyGen() * multiplier;
            }
        }
        return energyGen;
    }

    /**
     * Calculates a fair multiplier for the power generated per tick for the enchantment
     * depending on the level of the enchantment.
     * The formula is one divided by the quantity of 4 times the max level of the enchantment
     * multiplied by the square of the quantity of the current level minus one all plus one
     * @param enchantment the enchantment being burned
     * @param level the current level of the enchantment applied
     * @return
     */
    public double getMultiplier(Enchantment enchantment, int level) {
        return (1 / (4.0 * enchantment.getMaxLevel())) * Mth.square(level - 1) + 1;
    }

    public void addEntry(Disenchantment disenchantment) {
        this.enchantments.put(disenchantment.resourceLocation(), disenchantment);
    }

    public void clear() {
        enchantments.clear();
    }

    public static DisenchantmentManager getInstance() {
        return INSTANCE;
    }
}
