package dev.terrarium.minefactoryrenewed.item;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class FocusItem extends BaseItem {

    private final TagKey<Item> tag;
    private final DyeColor color;

    public FocusItem(TagKey<Item> tag, DyeColor color) {
        super(new Item.Properties().stacksTo(1).tab(MinefactoryRenewed.TAB));
        this.tag = tag;
        this.color = color;
    }

    public TagKey<Item> getTag() {
        return tag;
    }

    public DyeColor getColor() {
        return color;
    }
}
