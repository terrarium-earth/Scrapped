package dev.terrarium.minefactoryrenewed.registry;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MinefactoryRenewed.MODID);

    public static void init() {
    }
}
