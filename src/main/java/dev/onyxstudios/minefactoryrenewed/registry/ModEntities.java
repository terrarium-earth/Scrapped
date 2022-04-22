package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.entity.PinkSlimeEntity;
import dev.onyxstudios.minefactoryrenewed.entity.SafariNetEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MinefactoryRenewed.MODID);

    public static final RegistryObject<EntityType<SafariNetEntity>> SAFARI_NET = ENTITIES.register("safari_net",
            () -> EntityType.Builder.<SafariNetEntity>of(SafariNetEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("safari_net")
    );

    public static final RegistryObject<EntityType<PinkSlimeEntity>> PINK_SLIME = ENTITIES.register("pink_slime",
            () -> EntityType.Builder.<PinkSlimeEntity>of(PinkSlimeEntity::new, MobCategory.MISC)
                    .sized(2.04F, 2.04F)
                    .clientTrackingRange(10)
                    .build("pink_slime")
    );

    public static void attributeEvent(EntityAttributeCreationEvent event) {
        event.put(PINK_SLIME.get(), Monster.createMonsterAttributes().build());
    }
}
