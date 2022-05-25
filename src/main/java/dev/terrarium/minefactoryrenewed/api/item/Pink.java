package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public record Pink(Item item, int energyGen, int burnTime) {

    public static final Codec<Pink> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.ITEM.byNameCodec().fieldOf("item").forGetter(Pink::item),
            Codec.INT.fieldOf("energyGen").forGetter(Pink::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(Pink::burnTime)
    ).apply(instance, Pink::new));
}
