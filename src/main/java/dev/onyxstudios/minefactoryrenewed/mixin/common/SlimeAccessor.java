package dev.onyxstudios.minefactoryrenewed.mixin.common;

import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Slime.class)
public interface SlimeAccessor {

    @Invoker("setSize")
    void invokeSetSize(int size, boolean resetHealth);
}
