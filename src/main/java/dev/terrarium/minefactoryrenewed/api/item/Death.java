package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public record Death(Item item, int energyGen, int burnTime) {

    public static final Codec<Death> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.ITEM.byNameCodec().fieldOf("item").forGetter(Death::item),
            Codec.INT.fieldOf("energyGen").forGetter(Death::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(Death::burnTime)
    ).apply(instance, Death::new));
}
