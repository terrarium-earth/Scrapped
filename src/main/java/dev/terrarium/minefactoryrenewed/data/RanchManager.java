package dev.terrarium.minefactoryrenewed.data;

import com.google.common.collect.Maps;
import dev.terrarium.minefactoryrenewed.api.item.Ranchable;
import net.minecraft.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class RanchManager {

    private static final RanchManager INSTANCE = new RanchManager();
    private final Map<EntityType<?>, Ranchable> ranchables = new HashMap<>();
    //Copied from Sheep for now bcs stupid private
    private static final Map<DyeColor, ItemLike> WOOL_COLORS = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
        map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        map.put(DyeColor.LIME, Blocks.LIME_WOOL);
        map.put(DyeColor.PINK, Blocks.PINK_WOOL);
        map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        map.put(DyeColor.RED, Blocks.RED_WOOL);
        map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });

    public RanchManager() {
        addRanchable(new Ranchable(EntityType.SHEEP, Items.SHEARS, false,
                entity -> entity instanceof Sheep sheep ? new ItemStack(WOOL_COLORS.get(sheep.getColor())) : ItemStack.EMPTY,
                entity -> entity instanceof Sheep sheep && sheep.readyForShearing(),
                entity -> {
                    if (entity instanceof Sheep sheep)
                        sheep.setSheared(true);
                }
        ));

        addRanchable(new Ranchable(EntityType.COW, Items.BUCKET, true,
                entity -> new ItemStack(Items.MILK_BUCKET),
                entity -> entity instanceof Cow && !(entity instanceof MushroomCow),
                entity -> {
                }
        ));

        addRanchable(new Ranchable(EntityType.MOOSHROOM, Items.BOWL, true, new ItemStack(Items.MUSHROOM_STEW)));
        addRanchable(new Ranchable(EntityType.SQUID, null, false, new ItemStack(Items.INK_SAC)));
        addRanchable(new Ranchable(EntityType.GLOW_SQUID, null, false, new ItemStack(Items.GLOW_INK_SAC)));
    }

    public void addRanchable(Ranchable ranchable) {
        this.ranchables.put(ranchable.entityType(), ranchable);
    }

    public boolean hasRanchable(LivingEntity entity) {
        return ranchables.containsKey(entity.getType());
    }

    public Ranchable getRanchable(LivingEntity entity) {
        return ranchables.get(entity.getType());
    }

    public static RanchManager getInstance() {
        return INSTANCE;
    }
}
