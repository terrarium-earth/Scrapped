package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.PlanterContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.PlanterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MinefactoryRenewed.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MinefactoryRenewed.MODID);

    public static final RegistryObject<BlockEntityType<PlanterBlockEntity>> PLANTER_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("planter_block_entity", () -> BlockEntityType.Builder.of(PlanterBlockEntity::new, ModBlocks.PLANTER.get()).build(null));

    public static final RegistryObject<MenuType<PlanterContainer>> PLANTER_CONTAINER = CONTAINERS.register("planter_container",
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                PlanterBlockEntity planter = (PlanterBlockEntity) inv.player.level.getBlockEntity(pos);
                return new PlanterContainer(windowId, inv, planter);
            })
    );
}
