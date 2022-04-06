package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.FarmerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.FertilizerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.FruitPickerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.PlanterContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs.GrinderContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs.MeatPackerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs.SlaughterhouseContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FarmerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FertilizerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FruitPickerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.PlanterBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.GrinderBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.MeatPackerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.MobCounterBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.SlaughterhouseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MinefactoryRenewed.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, MinefactoryRenewed.MODID);

    public static final RegistryObject<BlockEntityType<PlanterBlockEntity>> PLANTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("planter_block_entity", () ->
                    BlockEntityType.Builder.of(PlanterBlockEntity::new, ModBlocks.PLANTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FarmerBlockEntity>> FARMER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("farmer_block_entity", () ->
                    BlockEntityType.Builder.of(FarmerBlockEntity::new, ModBlocks.FARMER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FertilizerBlockEntity>> FERTILIZER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fertilizer_block_entity", () ->
                    BlockEntityType.Builder.of(FertilizerBlockEntity::new, ModBlocks.FERTILIZER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FruitPickerBlockEntity>> FRUIT_PICKER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fruit_picker_block_entity", () ->
                    BlockEntityType.Builder.of(FruitPickerBlockEntity::new, ModBlocks.FRUIT_PICKER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<SlaughterhouseBlockEntity>> SLAUGHTERHOUSE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("slaughterhouse_block_entity", () ->
                    BlockEntityType.Builder.of(SlaughterhouseBlockEntity::new, ModBlocks.SLAUGHTERHOUSE.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<GrinderBlockEntity>> GRINDER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("grinder_block_entity", () ->
                    BlockEntityType.Builder.of(GrinderBlockEntity::new, ModBlocks.GRINDER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<MeatPackerBlockEntity>> MEAT_PACKER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("meat_packer_block_entity", () ->
                    BlockEntityType.Builder.of(MeatPackerBlockEntity::new, ModBlocks.MEAT_PACKER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<MobCounterBlockEntity>> MOB_COUNTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("mob_counter_block_entity", () ->
                    BlockEntityType.Builder.of(MobCounterBlockEntity::new, ModBlocks.MOB_COUNTER.get())
                            .build(null));

    /**
     * Containers
     */
    public static final RegistryObject<MenuType<PlanterContainer>> PLANTER_CONTAINER =
            CONTAINERS.register("planter_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        PlanterBlockEntity planter = (PlanterBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new PlanterContainer(windowId, inv, planter);
                    })
            );

    public static final RegistryObject<MenuType<FarmerContainer>> FARMER_CONTAINER =
            CONTAINERS.register("farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        FarmerBlockEntity farmer = (FarmerBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new FarmerContainer(windowId, inv, farmer);
                    })
            );

    public static final RegistryObject<MenuType<FertilizerContainer>> FERTILIZER_CONTAINER =
            CONTAINERS.register("fertilizer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        FertilizerBlockEntity fertilizer = (FertilizerBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new FertilizerContainer(windowId, inv, fertilizer);
                    })
            );

    public static final RegistryObject<MenuType<FruitPickerContainer>> FRUIT_PICKER_CONTAINER =
            CONTAINERS.register("fruit_picker_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        FruitPickerBlockEntity fruitPicker = (FruitPickerBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new FruitPickerContainer(windowId, inv, fruitPicker);
                    })
            );

    public static final RegistryObject<MenuType<SlaughterhouseContainer>> SLAUGHTERHOUSE_CONTAINER =
            CONTAINERS.register("slaughterhouse_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        SlaughterhouseBlockEntity slaughterhouse =
                                (SlaughterhouseBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new SlaughterhouseContainer(windowId, inv, slaughterhouse);
                    })
            );

    public static final RegistryObject<MenuType<GrinderContainer>> GRINDER_CONTAINER =
            CONTAINERS.register("grinder_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        GrinderBlockEntity grinder = (GrinderBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new GrinderContainer(windowId, inv, grinder);
                    })
            );

    public static final RegistryObject<MenuType<MeatPackerContainer>> MEAT_PACKER_CONTAINER =
            CONTAINERS.register("meat_packer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        MeatPackerBlockEntity meatPacker = (MeatPackerBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new MeatPackerContainer(windowId, inv, meatPacker);
                    })
            );
}
