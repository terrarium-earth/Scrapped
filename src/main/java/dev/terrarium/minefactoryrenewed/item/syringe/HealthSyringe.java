package dev.terrarium.minefactoryrenewed.item.syringe;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class HealthSyringe extends SyringeItem {

    @Override
    public void inject(Level level, LivingEntity entity) {
        entity.setHealth(entity.getMaxHealth());
    }

    @Override
    public boolean canInject(LivingEntity entity) {
        return entity.canChangeDimensions() && entity.getHealth() < entity.getMaxHealth();
    }
}
