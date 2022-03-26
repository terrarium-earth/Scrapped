package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class SafariNetItem extends Item {

    private final boolean singleUse;

    public SafariNetItem(boolean singleUse) {
        super(new Item.Properties().stacksTo(1).tab(MinefactoryRenewed.TAB));
        this.singleUse = singleUse;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return super.useOn(context);
    }

    public boolean isSingleUse() {
        return singleUse;
    }
}
