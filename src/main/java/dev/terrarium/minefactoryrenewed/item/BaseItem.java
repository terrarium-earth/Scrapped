package dev.terrarium.minefactoryrenewed.item;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.world.item.Item;

public class BaseItem extends Item {

    public BaseItem() {
        this(new Item.Properties().tab(MinefactoryRenewed.TAB));
    }

    public BaseItem(Properties properties) {
        super(properties);
    }
}
