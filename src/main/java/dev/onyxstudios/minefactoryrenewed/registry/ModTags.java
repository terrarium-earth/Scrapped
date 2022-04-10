package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {

    public static TagKey<Fluid> SLUDGE = createModFluidTag("sludge");
    public static TagKey<Fluid> MEAT = createModFluidTag("meat");
    public static TagKey<Fluid> PINK_SLIME = createModFluidTag("pink_slime");
    public static TagKey<Fluid> SEWAGE = createModFluidTag("sewage");

    public static TagKey<Item> createModItemTag(String name) {
        return ItemTags.create(new ResourceLocation(MinefactoryRenewed.MODID, name));
    }

    public static TagKey<Block> createModBlockTag(String name) {
        return BlockTags.create(new ResourceLocation(MinefactoryRenewed.MODID, name));
    }

    public static TagKey<Fluid> createModFluidTag(String name) {
        return FluidTags.create(new ResourceLocation(MinefactoryRenewed.MODID, name));
    }
}
