package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.world.item.Item;

public class BaseItem extends Item {

    public BaseItem() {
        this(new Item.Properties().tab(MinefactoryRenewed.TAB));
    }

    public BaseItem(Properties properties) {
        super(properties);
    }
}
