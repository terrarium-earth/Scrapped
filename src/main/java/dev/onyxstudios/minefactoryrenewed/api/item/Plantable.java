package dev.onyxstudios.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record Plantable(Item seeds, List<Block> soilTypes) {

    public static final Codec<Plantable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.ITEM.byNameCodec().fieldOf("item").forGetter(Plantable::seeds),
            Registry.BLOCK.byNameCodec().listOf().fieldOf("soilTypes").forGetter(Plantable::soilTypes)
    ).apply(instance, Plantable::new));
}
