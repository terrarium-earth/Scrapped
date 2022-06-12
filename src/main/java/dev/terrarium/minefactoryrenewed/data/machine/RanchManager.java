package dev.terrarium.minefactoryrenewed.data.machine;

import dev.terrarium.minefactoryrenewed.api.item.Ranchable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.List;

public class RanchManager extends HashMap<EntityType<?>, Ranchable> {

    private static final RanchManager INSTANCE = new RanchManager();

    private RanchManager() {
        addEntry(Ranchable.ofDamaging(EntityType.SHEEP, Items.SHEARS, 1,
                entity -> entity instanceof Sheep sheep ? sheep.onSheared(null, ItemStack.EMPTY, sheep.level, sheep.blockPosition(), 0) : List.of(ItemStack.EMPTY),
                entity -> entity instanceof Sheep sheep && sheep.readyForShearing(),
                entity -> {}
        ));

        addEntry(Ranchable.ofConsumable(EntityType.COW, Items.BUCKET,
                entity -> List.of(new ItemStack(Items.MILK_BUCKET)),
                entity -> entity instanceof Cow && !(entity instanceof MushroomCow),
                entity -> {}
        ));

        addEntry(new Ranchable(EntityType.MOOSHROOM, Items.BOWL, Ranchable.ToolInteractType.CONSUME, 0, new ItemStack(Items.MUSHROOM_STEW)));
        addEntry(new Ranchable(EntityType.SQUID, null, Ranchable.ToolInteractType.NOTHING, 0, new ItemStack(Items.INK_SAC)));
        addEntry(new Ranchable(EntityType.GLOW_SQUID, null, Ranchable.ToolInteractType.NOTHING, 0, new ItemStack(Items.GLOW_INK_SAC)));
    }

    public void addEntry(Ranchable ranchable) {
        put(ranchable.entityType(), ranchable);
    }

    public Ranchable getEntry(LivingEntity entity) {
        return get(entity.getType());
    }

    public static RanchManager getInstance() {
        return INSTANCE;
    }
}