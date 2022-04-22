package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class MeatIngotItem extends BaseItem {

    public MeatIngotItem(int hunger, float saturation) {
        super(new Item.Properties()
                .tab(MinefactoryRenewed.TAB)
                .food(new FoodProperties.Builder()
                        .nutrition(hunger)
                        .saturationMod(saturation)
                        .build()
                )
        );
    }
}
