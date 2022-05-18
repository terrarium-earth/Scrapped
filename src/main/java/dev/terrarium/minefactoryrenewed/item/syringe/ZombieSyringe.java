package dev.terrarium.minefactoryrenewed.item.syringe;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class ZombieSyringe extends SyringeItem {

    @Override
    public void inject(Level level, LivingEntity entity) {
        if (entity instanceof Animal animal) {
            animal.setBaby(false);

            //20% chance to turn into a zombie
            if (entity.level.getRandom().nextFloat() <= 0.2) {
                Zombie zombie = EntityType.ZOMBIE.create(level);

                if (zombie != null) {
                    zombie.setPos(entity.position());
                    level.addFreshEntity(zombie);
                    entity.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        }
    }

    @Override
    public boolean canInject(LivingEntity entity) {
        return entity instanceof Animal animal && animal.isBaby();
    }
}
