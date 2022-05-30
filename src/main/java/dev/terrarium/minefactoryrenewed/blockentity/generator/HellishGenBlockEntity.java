package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.data.generator.HellishManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class HellishGenBlockEntity extends BurnableGenBlockEntity {

    private int witherTime; //woot woot
    private boolean isWithering;
    private final AABB witherArea;

    public HellishGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HELLISH_GENERATOR.get(), pos, state, 100000, 1000, 2000);
        this.witherArea = new AABB(pos).inflate(4);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("isWithering", isWithering);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        isWithering = tag.getBoolean("isWithering");
    }

    @Override
    public void burnItem(ItemStack stack) {
        setEnergyGen(HellishManager.getInstance().getEnergyGen(stack));
        setBurnTime(HellishManager.getInstance().getBurnTime(stack));
        if(HellishManager.getInstance().doesWithering(stack)) isWithering = true;
    }

    @Override
    public void onBurn() {
        super.onBurn();
        if(level == null) return;
        if (isWithering) {
            witherTime++;
            if (witherTime == 5) {
                witherTime = 0;
                List<Entity> entities = level.getEntities((Entity) null, witherArea, MachineBlockEntity.ALL_ENTITY_PREDICATE);
                if (!entities.isEmpty()) {
                    for (Entity entity : entities) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        Map<MobEffect, MobEffectInstance> map = livingEntity.getActiveEffectsMap();
                        if (!map.containsKey(MobEffects.WITHER))
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 220, 0));
                    }
                }
            }
        }
    }

    @Override
    public void afterBurn() {
        isWithering = false;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return HellishManager.getInstance().isValid(stack);
    }
}
