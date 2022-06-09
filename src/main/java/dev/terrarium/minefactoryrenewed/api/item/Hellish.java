package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public record Hellish(Item item, boolean doesWithering, int energyGen, int burnTime) {
    public static final Codec<Hellish> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(Hellish::item),
            Codec.BOOL.fieldOf("doesWithering").orElse(false).forGetter(Hellish::doesWithering),
            Codec.INT.fieldOf("energyGen").forGetter(Hellish::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(Hellish::burnTime)
    ).apply(instance, Hellish::new));
}
