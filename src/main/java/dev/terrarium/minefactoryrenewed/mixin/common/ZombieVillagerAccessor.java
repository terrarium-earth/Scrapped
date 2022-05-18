package dev.terrarium.minefactoryrenewed.mixin.common;

import net.minecraft.world.entity.monster.ZombieVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.UUID;

@Mixin(ZombieVillager.class)
public interface ZombieVillagerAccessor {

    @Invoker("startConverting")
    void invokeStartConverting(UUID pConversionStarter, int pConversionTime);
}
