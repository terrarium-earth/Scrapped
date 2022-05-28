package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public record Ender(Item item, int energyGen, int burnTime) {

    public static final Codec<Ender> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.ITEM.byNameCodec().fieldOf("item").forGetter(Ender::item),
            Codec.INT.fieldOf("energyGen").forGetter(Ender::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(Ender::burnTime)
    ).apply(instance, Ender::new));
}
