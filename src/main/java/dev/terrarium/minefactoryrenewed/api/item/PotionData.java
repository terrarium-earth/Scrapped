package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record PotionData(ResourceLocation resourceLocation, int energyGen, int burnTime) {
    public static final Codec<PotionData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("potionId").forGetter(PotionData::resourceLocation),
            Codec.INT.fieldOf("energyGen").forGetter(PotionData::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(PotionData::burnTime)
    ).apply(instance, PotionData::new));
}
