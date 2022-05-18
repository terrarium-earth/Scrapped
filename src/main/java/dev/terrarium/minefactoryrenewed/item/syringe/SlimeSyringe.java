package dev.terrarium.minefactoryrenewed.item.syringe;

import dev.terrarium.minefactoryrenewed.mixin.common.SlimeAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;

public class SlimeSyringe extends SyringeItem {

    @Override
    public void inject(Level level, LivingEntity entity) {
        if (entity instanceof Slime slime) {
            ((SlimeAccessor) slime).invokeSetSize(Math.min(slime.getSize() << 1, Slime.MAX_SIZE), true);
        }
    }

    @Override
    public boolean canInject(LivingEntity entity) {
        return entity instanceof Slime;
    }
}
