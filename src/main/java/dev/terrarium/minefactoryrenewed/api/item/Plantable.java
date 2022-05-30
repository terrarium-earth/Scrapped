package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public record Plantable(Item seeds, List<Block> soilTypes) {

    public static final Codec<Plantable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(Plantable::seeds),
            ForgeRegistries.BLOCKS.getCodec().listOf().fieldOf("soilTypes").forGetter(Plantable::soilTypes)
    ).apply(instance, Plantable::new));
}
