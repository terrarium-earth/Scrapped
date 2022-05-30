package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public record Slimey(Item item, int energyGen, int burnTime) {

    public static final Codec<Slimey> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(Slimey::item),
            Codec.INT.fieldOf("energyGen").forGetter(Slimey::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(Slimey::burnTime)
    ).apply(instance, Slimey::new));
}
