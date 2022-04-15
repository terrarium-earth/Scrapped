package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FocusItem extends Item {

    private final TagKey<Block> tag;
    private final DyeColor color;

    public FocusItem(TagKey<Block> tag, DyeColor color) {
        super(new Item.Properties().stacksTo(1).tab(MinefactoryRenewed.TAB));
        this.tag = tag;
        this.color = color;
    }

    public TagKey<Block> getTag() {
        return tag;
    }

    public DyeColor getColor() {
        return color;
    }
}
