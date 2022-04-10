package dev.onyxstudios.minefactoryrenewed.api.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record Ranchable(EntityType<?> entityType,
                        Item tool,
                        boolean consumeTool,
                        Function<LivingEntity, ItemStack> resultFunction,
                        Predicate<LivingEntity> entityPredicate,
                        Consumer<LivingEntity> consumer) {

    public Ranchable(EntityType<?> entityType, Item tool, boolean consumeTool, Function<LivingEntity, ItemStack> resultFunction) {
        this(entityType, tool, consumeTool, resultFunction, entity -> true, entity -> {});
    }

    public Ranchable(EntityType<?> entityType, Item tool, boolean consumeTool, ItemStack result) {
        this(entityType, tool, consumeTool, entity -> result, entity -> true, entity -> {});
    }

    public ItemStack result(LivingEntity entity) {
        return resultFunction.apply(entity);
    }
}
