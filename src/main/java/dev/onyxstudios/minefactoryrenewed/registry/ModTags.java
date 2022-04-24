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
    public static TagKey<Fluid> STEAM = createModFluidTag("steam");
    public static TagKey<Fluid> ETHANOL = createModFluidTag("ethanol");

    public static TagKey<Item> LASER_ORE = createModItemTag("laser_ore");
    public static TagKey<Item> WHITE_FOCUS = createModItemTag("white_focus");
    public static TagKey<Item> YELLOW_FOCUS = createModItemTag("yellow_focus");
    public static TagKey<Item> BLACK_FOCUS = createModItemTag("black_focus");
    public static TagKey<Item> BLUE_FOCUS = createModItemTag("blue_focus");
    public static TagKey<Item> BROWN_FOCUS = createModItemTag("brown_focus");
    public static TagKey<Item> CYAN_FOCUS = createModItemTag("cyan_focus");
    public static TagKey<Item> GRAY_FOCUS = createModItemTag("gray_focus");
    public static TagKey<Item> GREEN_FOCUS = createModItemTag("green_focus");
    public static TagKey<Item> LIGHT_BLUE_FOCUS = createModItemTag("light_blue_focus");
    public static TagKey<Item> LIGHT_GRAY_FOCUS = createModItemTag("light_gray_focus");
    public static TagKey<Item> LIME_FOCUS = createModItemTag("lime_focus");
    public static TagKey<Item> MAGENTA_FOCUS = createModItemTag("magenta_focus");
    public static TagKey<Item> ORANGE_FOCUS = createModItemTag("orange_focus");
    public static TagKey<Item> PINK_FOCUS = createModItemTag("pink_focus");
    public static TagKey<Item> PURPLE_FOCUS = createModItemTag("purple_focus");
    public static TagKey<Item> RED_FOCUS = createModItemTag("red_focus");

    public static TagKey<Item> ETHANOL_SOURCE = createModItemTag("ethanol_source");

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
