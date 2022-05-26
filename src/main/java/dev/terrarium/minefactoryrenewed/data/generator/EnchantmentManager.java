package dev.terrarium.minefactoryrenewed.data.generator;

import dev.terrarium.minefactoryrenewed.api.item.Disenchantment;
import dev.terrarium.minefactoryrenewed.api.item.PotionData;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.*;

public class EnchantmentManager {

    private static final EnchantmentManager INSTANCE = new EnchantmentManager();
    private final Map<ResourceLocation, Disenchantment> enchantments = new HashMap<>();

    public boolean isEnchantmentBurnable(ItemStack stack) {
        if(stack.isEnchanted()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            for(Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
                if(this.enchantments.containsKey(enchantment.getKey().getRegistryName())) return true;
            }
        }
        return false;
    }

    public int getBurnTime(ItemStack stack) {
        Set<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet();
        return enchantments.stream().mapToInt(value -> this.enchantments.get(value.getRegistryName()).burnTime()).max().orElse(0);
    }

    public int getEnergyGen(ItemStack stack) {
        int energyGen = 0;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        for(Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
            if(this.enchantments.containsKey(enchantment.getKey().getRegistryName())) {
                double multiplier = (1 / (4.0 * enchantment.getKey().getMaxLevel())) * (enchantment.getValue() - 1) * (enchantment.getValue() - 1) + 1;
                energyGen += this.enchantments.get(enchantment.getKey().getRegistryName()).energyGen() * multiplier;
            }
        }
        return energyGen;
    }

    public void addEntry(Disenchantment disenchantment) {
        this.enchantments.put(disenchantment.resourceLocation(), disenchantment);
    }

    public void clear() {
        enchantments.clear();
    }

    public static EnchantmentManager getInstance() {
        return INSTANCE;
    }
}
