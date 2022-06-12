package dev.terrarium.minefactoryrenewed.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public record Ranchable(EntityType<?> entityType,
                        Item tool, ToolInteractType interactType, int damage,
                        Function<LivingEntity, List<ItemStack>> resultFunction,
                        Predicate<LivingEntity> entityPredicate,
                        Consumer<LivingEntity> consumer) {

    public static final Codec<Ranchable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ForgeRegistries.ENTITIES.getCodec().fieldOf("entity").forGetter(Ranchable::entityType),
            ForgeRegistries.ITEMS.getCodec().fieldOf("tool").forGetter(Ranchable::tool),
            ToolInteractType.CODEC.fieldOf("interactType").orElse(ToolInteractType.NOTHING).forGetter(Ranchable::interactType),
            Codec.INT.fieldOf("damage").orElse(0).forGetter(Ranchable::damage),
            ItemStack.CODEC.fieldOf("result").forGetter(ranchable -> ItemStack.EMPTY)
    ).apply(instance, Ranchable::new));

    public Ranchable(EntityType<?> entityType, Item tool, ToolInteractType interactType, int damage, ItemStack result) {
        this(entityType, tool, interactType, damage, entity -> List.of(result), entity -> true, entity -> {});
    }

    public static Ranchable ofConsumable(EntityType<?> entityType, Item tool, Function<LivingEntity, List<ItemStack>> resultFunction, Predicate<LivingEntity> entityPredicate, Consumer<LivingEntity> consumer) {
        return new Ranchable(entityType, tool, ToolInteractType.CONSUME, 0, resultFunction, entityPredicate, consumer);
    }

    public static Ranchable ofDamaging(EntityType<?> entityType, Item tool, int damage, Function<LivingEntity, List<ItemStack>> resultFunction, Predicate<LivingEntity> entityPredicate, Consumer<LivingEntity> consumer) {
        return new Ranchable(entityType, tool, ToolInteractType.DAMAGING, damage, resultFunction, entityPredicate, consumer);
    }

    public List<ItemStack> result(LivingEntity entity) {
        return resultFunction.apply(entity);
    }

    public enum ToolInteractType {
        CONSUME,
        DAMAGING,
        NOTHING;

        public static final Codec<ToolInteractType> CODEC = Codec.STRING.xmap(ToolInteractType::valueOf, ToolInteractType::name);
    }
}
