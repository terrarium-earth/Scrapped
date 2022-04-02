package dev.onyxstudios.minefactoryrenewed.api.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public record Pickable(Item plant, boolean breakPlant) {

    public static final Codec<Pickable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.ITEM.byNameCodec().fieldOf("plant").forGetter(Pickable::plant),
            Codec.BOOL.fieldOf("breakPlant").orElse(true).forGetter(Pickable::breakPlant)
    ).apply(instance, Pickable::new));
}
