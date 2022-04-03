package dev.onyxstudios.minefactoryrenewed.entity;

import dev.onyxstudios.minefactoryrenewed.registry.ModEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class PinkSlimeEntity extends Slime {

    public PinkSlimeEntity(EntityType<PinkSlimeEntity> entityType, Level level) {
        super(entityType, level);
        setSize(1, true);
    }

    public PinkSlimeEntity(Level level, int size) {
        super(ModEntities.PINK_SLIME.get(), level);
        setSize(size, true);
    }

    @Override
    protected int getJumpDelay() {
        return this.random.nextInt(10) + 5;
    }

    @Override
    protected boolean spawnCustomParticles() {
        return true;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
