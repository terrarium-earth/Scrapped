package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public record Explosive(Item item, int energyGen, int burnTime) {

    public static final Codec<Explosive> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(Explosive::item),
            Codec.INT.fieldOf("energyGen").forGetter(Explosive::energyGen),
            Codec.INT.fieldOf("burnTime").forGetter(Explosive::burnTime)
    ).apply(instance, Explosive::new));
}
