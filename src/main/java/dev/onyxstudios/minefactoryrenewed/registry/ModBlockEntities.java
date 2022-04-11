package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.animals.*;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.blocks.BlockPlacerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.blocks.BlockSmasherContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.FarmerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.FertilizerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.FruitPickerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.PlanterContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs.*;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.animals.*;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.blocks.BlockBreakerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.blocks.BlockPlacerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.blocks.BlockSmasherBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FarmerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FertilizerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FruitPickerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.PlanterBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.*;
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

    public static final RegistryObject<BlockEntityType<MobRouterBlockEntity>> MOB_ROUTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("mob_router_block_entity", () ->
                    BlockEntityType.Builder.of(MobRouterBlockEntity::new, ModBlocks.MOB_ROUTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<AutoSpawnerBlockEntity>> AUTO_SPAWNER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("auto_spawner_block_entity", () ->
                    BlockEntityType.Builder.of(AutoSpawnerBlockEntity::new, ModBlocks.AUTO_SPAWNER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FisherBlockEntity>> FISHER =
            BLOCK_ENTITIES.register("fisher_block_entity", () ->
                    BlockEntityType.Builder.of(FisherBlockEntity::new, ModBlocks.FISHER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<RancherBlockEntity>> RANCHER =
            BLOCK_ENTITIES.register("rancher_block_entity", () ->
                    BlockEntityType.Builder.of(RancherBlockEntity::new, ModBlocks.RANCHER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<BreederBlockEntity>> BREEDER =
            BLOCK_ENTITIES.register("breeder_block_entity", () ->
                    BlockEntityType.Builder.of(BreederBlockEntity::new, ModBlocks.BREEDER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ChronotyperBlockEntity>> CHRONOTYPER =
            BLOCK_ENTITIES.register("chronotyper_block_entity", () ->
                    BlockEntityType.Builder.of(ChronotyperBlockEntity::new, ModBlocks.CHRONOTYPER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<SewerBlockEntity>> SEWER =
            BLOCK_ENTITIES.register("sewer_block_entity", () ->
                    BlockEntityType.Builder.of(SewerBlockEntity::new, ModBlocks.SEWER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<VeterinaryBlockEntity>> VETERINARY =
            BLOCK_ENTITIES.register("veterinary_block_entity", () ->
                    BlockEntityType.Builder.of(VeterinaryBlockEntity::new, ModBlocks.VETERINARY.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<BlockPlacerBlockEntity>> BLOCK_PLACER =
            BLOCK_ENTITIES.register("block_placer_block_entity", () ->
                    BlockEntityType.Builder.of(BlockPlacerBlockEntity::new, ModBlocks.BLOCK_PLACER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<BlockBreakerBlockEntity>> BLOCK_BREAKER =
            BLOCK_ENTITIES.register("block_breaker_block_entity", () ->
                    BlockEntityType.Builder.of(BlockBreakerBlockEntity::new, ModBlocks.BLOCK_BREAKER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<BlockSmasherBlockEntity>> BLOCK_SMASHER =
            BLOCK_ENTITIES.register("block_smasher_block_entity", () ->
                    BlockEntityType.Builder.of(BlockSmasherBlockEntity::new, ModBlocks.BLOCK_SMASHER.get())
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

    public static final RegistryObject<MenuType<MobRouterContainer>> MOB_ROUTER_CONTAINER =
            CONTAINERS.register("mob_router_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        MobRouterBlockEntity mobRouter = (MobRouterBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new MobRouterContainer(windowId, inv, mobRouter);
                    })
            );

    public static final RegistryObject<MenuType<AutoSpawnerContainer>> AUTO_SPAWNER_CONTAINER =
            CONTAINERS.register("auto_spawner_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        AutoSpawnerBlockEntity autoSpawner = (AutoSpawnerBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new AutoSpawnerContainer(windowId, inv, autoSpawner);
                    })
            );

    public static final RegistryObject<MenuType<FisherContainer>> FISHER_CONTAINER =
            CONTAINERS.register("fisher_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        FisherBlockEntity fisher = (FisherBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new FisherContainer(windowId, inv, fisher);
                    })
            );

    public static final RegistryObject<MenuType<RancherContainer>> RANCHER_CONTAINER =
            CONTAINERS.register("rancher_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        RancherBlockEntity rancher = (RancherBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new RancherContainer(windowId, inv, rancher);
                    })
            );

    public static final RegistryObject<MenuType<BreederContainer>> BREEDER_CONTAINER =
            CONTAINERS.register("breeder_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        BreederBlockEntity breeder = (BreederBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new BreederContainer(windowId, inv, breeder);
                    })
            );

    public static final RegistryObject<MenuType<ChronotyperContainer>> CHRONOTYPER_CONTAINER =
            CONTAINERS.register("chronotyper_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        ChronotyperBlockEntity chronotyper = (ChronotyperBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new ChronotyperContainer(windowId, inv, chronotyper);
                    })
            );

    public static final RegistryObject<MenuType<SewerContainer>> SEWER_CONTAINER =
            CONTAINERS.register("sewer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        SewerBlockEntity sewer = (SewerBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new SewerContainer(windowId, inv, sewer);
                    })
            );

    public static final RegistryObject<MenuType<VeterinaryContainer>> VETERINARY_CONTAINER =
            CONTAINERS.register("veterinary_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        VeterinaryBlockEntity veterinary = (VeterinaryBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new VeterinaryContainer(windowId, inv, veterinary);
                    })
            );

    public static final RegistryObject<MenuType<BlockPlacerContainer>> BLOCK_PLACER_CONTAINER =
            CONTAINERS.register("block_placer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        BlockPlacerBlockEntity blockPlacer = (BlockPlacerBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new BlockPlacerContainer(windowId, inv, blockPlacer);
                    })
            );

    public static final RegistryObject<MenuType<BlockSmasherContainer>> BLOCK_SMASHER_CONTAINER =
            CONTAINERS.register("block_smasher_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        BlockSmasherBlockEntity blockSmasher = (BlockSmasherBlockEntity) inv.player.level.getBlockEntity(pos);
                        return new BlockSmasherContainer(windowId, inv, blockSmasher);
                    })
            );
}
