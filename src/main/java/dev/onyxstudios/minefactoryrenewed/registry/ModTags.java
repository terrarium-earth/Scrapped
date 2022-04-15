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

    public static TagKey<Block> WHITE_FOCUS = createModBlockTag("white_focus");
    public static TagKey<Block> YELLOW_FOCUS = createModBlockTag("yellow_focus");
    public static TagKey<Block> BLACK_FOCUS = createModBlockTag("black_focus");
    public static TagKey<Block> BLUE_FOCUS = createModBlockTag("blue_focus");
    public static TagKey<Block> BROWN_FOCUS = createModBlockTag("brown_focus");
    public static TagKey<Block> CYAN_FOCUS = createModBlockTag("cyan_focus");
    public static TagKey<Block> GRAY_FOCUS = createModBlockTag("gray_focus");
    public static TagKey<Block> GREEN_FOCUS = createModBlockTag("green_focus");
    public static TagKey<Block> LIGHT_BLUE_FOCUS = createModBlockTag("light_blue_focus");
    public static TagKey<Block> LIGHT_GRAY_FOCUS = createModBlockTag("light_gray_focus");
    public static TagKey<Block> LIME_FOCUS = createModBlockTag("lime_focus");
    public static TagKey<Block> MAGENTA_FOCUS = createModBlockTag("magenta_focus");
    public static TagKey<Block> ORANGE_FOCUS = createModBlockTag("orange_focus");
    public static TagKey<Block> PINK_FOCUS = createModBlockTag("pink_focus");
    public static TagKey<Block> PURPLE_FOCUS = createModBlockTag("purple_focus");
    public static TagKey<Block> RED_FOCUS = createModBlockTag("red_focus");

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
