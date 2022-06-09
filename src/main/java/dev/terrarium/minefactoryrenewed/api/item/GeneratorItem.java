package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public record GeneratorItem(Item item, int energyGen, int burnTime) {

    public static final Codec<GeneratorItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(GeneratorItem::item),
            Codec.INT.fieldOf("energyGen").forGetter(GeneratorItem::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(GeneratorItem::burnTime)
    ).apply(instance, GeneratorItem::new));
}
