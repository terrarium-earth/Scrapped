package dev.onyxstudios.minefactoryrenewed.item.syringe;

import dev.onyxstudios.minefactoryrenewed.mixin.common.ZombieVillagerAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.level.Level;

public class DeZombieSyringe extends SyringeItem {

    @Override
    public void inject(Level level, LivingEntity entity) {
        if (entity instanceof ZombieVillager zombie) {
            ((ZombieVillagerAccessor) zombie).invokeStartConverting(null, 300);
        }
    }

    @Override
    public boolean canInject(LivingEntity entity) {
        return entity instanceof ZombieVillager;
    }
}
