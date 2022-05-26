package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record Disenchantment(ResourceLocation resourceLocation, int energyGen, int burnTime) {
    public static final Codec<Disenchantment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("enchantmentId").forGetter(Disenchantment::resourceLocation),
            Codec.INT.fieldOf("energyGen").forGetter(Disenchantment::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(Disenchantment::burnTime)
    ).apply(instance, Disenchantment::new));
}
