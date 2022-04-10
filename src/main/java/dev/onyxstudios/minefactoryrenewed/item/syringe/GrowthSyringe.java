package dev.onyxstudios.minefactoryrenewed.item.syringe;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class GrowthSyringe extends SyringeItem {

    @Override
    public void inject(Level level, LivingEntity entity) {
        if (entity instanceof Animal animal) {
            animal.setBaby(false);
        }
    }

    @Override
    public boolean canInject(LivingEntity entity) {
        return entity instanceof Animal animal && animal.isBaby();
    }
}
