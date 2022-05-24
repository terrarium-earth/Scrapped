package dev.terrarium.minefactoryrenewed.registry;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.animals.*;
import dev.terrarium.minefactoryrenewed.blockentity.container.blocks.BlockPlacerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.blocks.BlockSmasherContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.blocks.DeepStorageContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.enchantment.AutoAnvilContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.enchantment.AutoDisenchanterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.enchantment.AutoEnchanterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.farming.FarmerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.farming.FertilizerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.farming.FruitPickerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.farming.PlanterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.FurnaceGenContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.generator.LavaGenContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.mobs.*;
import dev.terrarium.minefactoryrenewed.blockentity.container.power.EthanolGeneratorContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.power.SteamTurbineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.container.processing.*;
import dev.terrarium.minefactoryrenewed.blockentity.container.transport.ItemRouterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.generator.FurnaceGenBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.generator.LavaGenBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.animals.*;
import dev.terrarium.minefactoryrenewed.blockentity.machine.blocks.BlockBreakerBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.blocks.BlockPlacerBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.blocks.BlockSmasherBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.blocks.DeepStorageBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment.AutoAnvilBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment.AutoDisenchanterBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment.AutoEnchanterBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.farming.FarmerBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.farming.FertilizerBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.farming.FruitPickerBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.farming.PlanterBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.mobs.*;
import dev.terrarium.minefactoryrenewed.blockentity.machine.power.CreativeEnergyBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.power.EthanolGeneratorBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.power.SteamTurbineBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.processing.*;
import dev.terrarium.minefactoryrenewed.blockentity.transport.EjectorBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.transport.ItemCollectorBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.transport.ItemRouterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MinefactoryRenewed.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MinefactoryRenewed.MODID);

    public static final RegistryObject<BlockEntityType<CreativeEnergyBlockEntity>> CREATIVE_ENERGY =
            BLOCK_ENTITIES.register("creative_energy_block_entity", () ->
                    BlockEntityType.Builder.of(CreativeEnergyBlockEntity::new, ModBlocks.CREATIVE_ENERGY.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<PlanterBlockEntity>> PLANTER =
            BLOCK_ENTITIES.register("planter_block_entity", () ->
                    BlockEntityType.Builder.of(PlanterBlockEntity::new, ModBlocks.PLANTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FarmerBlockEntity>> FARMER =
            BLOCK_ENTITIES.register("farmer_block_entity", () ->
                    BlockEntityType.Builder.of(FarmerBlockEntity::new, ModBlocks.FARMER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FertilizerBlockEntity>> FERTILIZER =
            BLOCK_ENTITIES.register("fertilizer_block_entity", () ->
                    BlockEntityType.Builder.of(FertilizerBlockEntity::new, ModBlocks.FERTILIZER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FruitPickerBlockEntity>> FRUIT_PICKER =
            BLOCK_ENTITIES.register("fruit_picker_block_entity", () ->
                    BlockEntityType.Builder.of(FruitPickerBlockEntity::new, ModBlocks.FRUIT_PICKER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<SlaughterhouseBlockEntity>> SLAUGHTERHOUSE =
            BLOCK_ENTITIES.register("slaughterhouse_block_entity", () ->
                    BlockEntityType.Builder.of(SlaughterhouseBlockEntity::new, ModBlocks.SLAUGHTERHOUSE.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<GrinderBlockEntity>> GRINDER =
            BLOCK_ENTITIES.register("grinder_block_entity", () ->
                    BlockEntityType.Builder.of(GrinderBlockEntity::new, ModBlocks.GRINDER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<MeatPackerBlockEntity>> MEAT_PACKER =
            BLOCK_ENTITIES.register("meat_packer_block_entity", () ->
                    BlockEntityType.Builder.of(MeatPackerBlockEntity::new, ModBlocks.MEAT_PACKER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<MobCounterBlockEntity>> MOB_COUNTER =
            BLOCK_ENTITIES.register("mob_counter_block_entity", () ->
                    BlockEntityType.Builder.of(MobCounterBlockEntity::new, ModBlocks.MOB_COUNTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<MobRouterBlockEntity>> MOB_ROUTER =
            BLOCK_ENTITIES.register("mob_router_block_entity", () ->
                    BlockEntityType.Builder.of(MobRouterBlockEntity::new, ModBlocks.MOB_ROUTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<AutoSpawnerBlockEntity>> AUTO_SPAWNER =
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

    public static final RegistryObject<BlockEntityType<DeepStorageBlockEntity>> DEEP_STORAGE =
            BLOCK_ENTITIES.register("deep_storage_block_entity", () ->
                    BlockEntityType.Builder.of(DeepStorageBlockEntity::new, ModBlocks.DEEP_STORAGE.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<AutoDisenchanterBlockEntity>> AUTO_DISENCHANTER =
            BLOCK_ENTITIES.register("auto_disenchanter_block_entity", () ->
                    BlockEntityType.Builder.of(AutoDisenchanterBlockEntity::new, ModBlocks.AUTO_DISENCHANTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<AutoEnchanterBlockEntity>> AUTO_ENCHANTER =
            BLOCK_ENTITIES.register("auto_enchanter_block_entity", () ->
                    BlockEntityType.Builder.of(AutoEnchanterBlockEntity::new, ModBlocks.AUTO_ENCHANTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<AutoAnvilBlockEntity>> AUTO_ANVIL =
            BLOCK_ENTITIES.register("auto_anvil_block_entity", () ->
                    BlockEntityType.Builder.of(AutoAnvilBlockEntity::new, ModBlocks.AUTO_ANVIL.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<LaserDrillBlockEntity>> LASER_DRILL =
            BLOCK_ENTITIES.register("laser_drill_block_entity", () ->
                    BlockEntityType.Builder.of(LaserDrillBlockEntity::new, ModBlocks.LASER_DRILL.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<LaserChargerBlockEntity>> LASER_CHARGER =
            BLOCK_ENTITIES.register("laser_charger_block_entity", () ->
                    BlockEntityType.Builder.of(LaserChargerBlockEntity::new, ModBlocks.LASER_CHARGER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<SteamBoilerBlockEntity>> STEAM_BOILER =
            BLOCK_ENTITIES.register("steam_boiler_block_entity", () ->
                    BlockEntityType.Builder.of(SteamBoilerBlockEntity::new, ModBlocks.STEAM_BOILER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<SteamTurbineBlockEntity>> STEAM_TURBINE =
            BLOCK_ENTITIES.register("steam_turbine_block_entity", () ->
                    BlockEntityType.Builder.of(SteamTurbineBlockEntity::new, ModBlocks.STEAM_TURBINE.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<EthanolGeneratorBlockEntity>> ETHANOL_GENERATOR =
            BLOCK_ENTITIES.register("ethanol_generator_block_entity", () ->
                    BlockEntityType.Builder.of(EthanolGeneratorBlockEntity::new, ModBlocks.ETHANOL_GENERATOR.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<EthanolReactorBlockEntity>> ETHANOL_REACTOR =
            BLOCK_ENTITIES.register("ethanol_reactor_block_entity", () ->
                    BlockEntityType.Builder.of(EthanolReactorBlockEntity::new, ModBlocks.ETHANOL_REACTOR.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<LavaFabBlockEntity>> LAVA_FABRICATOR =
            BLOCK_ENTITIES.register("lava_fabricator_block_entity", () ->
                    BlockEntityType.Builder.of(LavaFabBlockEntity::new, ModBlocks.LAVA_FABRICATOR.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ComposterBlockEntity>> COMPOSTER =
            BLOCK_ENTITIES.register("composter_block_entity", () ->
                    BlockEntityType.Builder.of(ComposterBlockEntity::new, ModBlocks.COMPOSTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<SludgeBoilerBlockEntity>> SLUDGE_BOILER =
            BLOCK_ENTITIES.register("sludge_boiler_block_entity", () ->
                    BlockEntityType.Builder.of(SludgeBoilerBlockEntity::new, ModBlocks.SLUDGE_BOILER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<WeatherBlockEntity>> WEATHER =
            BLOCK_ENTITIES.register("weather_block_entity", () ->
                    BlockEntityType.Builder.of(WeatherBlockEntity::new, ModBlocks.WEATHER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ItemCollectorBlockEntity>> ITEM_COLLECTOR =
            BLOCK_ENTITIES.register("item_collector_block_entity", () ->
                    BlockEntityType.Builder.of(ItemCollectorBlockEntity::new, ModBlocks.ITEM_COLLECTOR.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<EjectorBlockEntity>> EJECTOR =
            BLOCK_ENTITIES.register("ejector_block_entity", () ->
                    BlockEntityType.Builder.of(EjectorBlockEntity::new, ModBlocks.EJECTOR.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ItemRouterBlockEntity>> ITEM_ROUTER =
            BLOCK_ENTITIES.register("item_router_block_entity", () ->
                    BlockEntityType.Builder.of(ItemRouterBlockEntity::new, ModBlocks.ITEM_ROUTER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<SignBlockEntity>> RUBBER_SIGN =
            BLOCK_ENTITIES.register("rubber_sign_block_entity", () ->
                    BlockEntityType.Builder.of(SignBlockEntity::new,
                                    ModBlocks.RUBBER_SIGN.get(), ModBlocks.RUBBER_WALL_SIGN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FurnaceGenBlockEntity>> FURNACE_GENERATOR =
            BLOCK_ENTITIES.register("furnace_generator", () ->
                    BlockEntityType.Builder.of(FurnaceGenBlockEntity::new, ModBlocks.FURNACE_GENERATOR.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FurnaceGenBlockEntity>> LAVA_GENERATOR =
            BLOCK_ENTITIES.register("lava_generator", () ->
                    BlockEntityType.Builder.of(LavaGenBlockEntity::new, ModBlocks.LAVA_GENERATOR.get())
                            .build(null));

    /**
     * Containers
     */
    public static final RegistryObject<MenuType<PlanterContainer>> PLANTER_CONTAINER =
            CONTAINERS.register("planter_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                PlanterBlockEntity planter = (PlanterBlockEntity) inv.player.level.getBlockEntity(pos);
                return new PlanterContainer(windowId, inv, planter);
            }));

    public static final RegistryObject<MenuType<FarmerContainer>> FARMER_CONTAINER =
            CONTAINERS.register("farmer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                FarmerBlockEntity farmer = (FarmerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new FarmerContainer(windowId, inv, farmer);
            }));

    public static final RegistryObject<MenuType<FertilizerContainer>> FERTILIZER_CONTAINER =
            CONTAINERS.register("fertilizer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                FertilizerBlockEntity fertilizer = (FertilizerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new FertilizerContainer(windowId, inv, fertilizer);
            }));

    public static final RegistryObject<MenuType<FruitPickerContainer>> FRUIT_PICKER_CONTAINER =
            CONTAINERS.register("fruit_picker_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                FruitPickerBlockEntity fruitPicker = (FruitPickerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new FruitPickerContainer(windowId, inv, fruitPicker);
            }));

    public static final RegistryObject<MenuType<SlaughterhouseContainer>> SLAUGHTERHOUSE_CONTAINER =
            CONTAINERS.register("slaughterhouse_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                SlaughterhouseBlockEntity slaughterhouse =
                        (SlaughterhouseBlockEntity) inv.player.level.getBlockEntity(pos);
                return new SlaughterhouseContainer(windowId, inv, slaughterhouse);
            }));

    public static final RegistryObject<MenuType<GrinderContainer>> GRINDER_CONTAINER =
            CONTAINERS.register("grinder_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                GrinderBlockEntity grinder = (GrinderBlockEntity) inv.player.level.getBlockEntity(pos);
                return new GrinderContainer(windowId, inv, grinder);
            }));

    public static final RegistryObject<MenuType<MeatPackerContainer>> MEAT_PACKER_CONTAINER =
            CONTAINERS.register("meat_packer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                MeatPackerBlockEntity meatPacker = (MeatPackerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new MeatPackerContainer(windowId, inv, meatPacker);
            }));

    public static final RegistryObject<MenuType<MobRouterContainer>> MOB_ROUTER_CONTAINER =
            CONTAINERS.register("mob_router_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                MobRouterBlockEntity mobRouter = (MobRouterBlockEntity) inv.player.level.getBlockEntity(pos);
                return new MobRouterContainer(windowId, inv, mobRouter);
            }));

    public static final RegistryObject<MenuType<AutoSpawnerContainer>> AUTO_SPAWNER_CONTAINER =
            CONTAINERS.register("auto_spawner_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                AutoSpawnerBlockEntity autoSpawner = (AutoSpawnerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new AutoSpawnerContainer(windowId, inv, autoSpawner);
            }));

    public static final RegistryObject<MenuType<FisherContainer>> FISHER_CONTAINER =
            CONTAINERS.register("fisher_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                FisherBlockEntity fisher = (FisherBlockEntity) inv.player.level.getBlockEntity(pos);
                return new FisherContainer(windowId, inv, fisher);
            }));

    public static final RegistryObject<MenuType<RancherContainer>> RANCHER_CONTAINER =
            CONTAINERS.register("rancher_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                RancherBlockEntity rancher = (RancherBlockEntity) inv.player.level.getBlockEntity(pos);
                return new RancherContainer(windowId, inv, rancher);
            }));

    public static final RegistryObject<MenuType<BreederContainer>> BREEDER_CONTAINER =
            CONTAINERS.register("breeder_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BreederBlockEntity breeder = (BreederBlockEntity) inv.player.level.getBlockEntity(pos);
                return new BreederContainer(windowId, inv, breeder);
            }));

    public static final RegistryObject<MenuType<ChronotyperContainer>> CHRONOTYPER_CONTAINER =
            CONTAINERS.register("chronotyper_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                ChronotyperBlockEntity chronotyper = (ChronotyperBlockEntity) inv.player.level.getBlockEntity(pos);
                return new ChronotyperContainer(windowId, inv, chronotyper);
            }));

    public static final RegistryObject<MenuType<SewerContainer>> SEWER_CONTAINER =
            CONTAINERS.register("sewer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                SewerBlockEntity sewer = (SewerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new SewerContainer(windowId, inv, sewer);
            }));

    public static final RegistryObject<MenuType<VeterinaryContainer>> VETERINARY_CONTAINER =
            CONTAINERS.register("veterinary_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                VeterinaryBlockEntity veterinary = (VeterinaryBlockEntity) inv.player.level.getBlockEntity(pos);
                return new VeterinaryContainer(windowId, inv, veterinary);
            }));

    public static final RegistryObject<MenuType<BlockPlacerContainer>> BLOCK_PLACER_CONTAINER =
            CONTAINERS.register("block_placer_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockPlacerBlockEntity blockPlacer = (BlockPlacerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new BlockPlacerContainer(windowId, inv, blockPlacer);
            }));

    public static final RegistryObject<MenuType<BlockSmasherContainer>> BLOCK_SMASHER_CONTAINER =
            CONTAINERS.register("block_smasher_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockSmasherBlockEntity blockSmasher = (BlockSmasherBlockEntity) inv.player.level.getBlockEntity(pos);
                return new BlockSmasherContainer(windowId, inv, blockSmasher);
            }));

    public static final RegistryObject<MenuType<DeepStorageContainer>> DEEP_STORAGE_CONTAINER =
            CONTAINERS.register("deep_storage_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                DeepStorageBlockEntity deepStorage = (DeepStorageBlockEntity) inv.player.level.getBlockEntity(pos);
                return new DeepStorageContainer(windowId, inv, deepStorage);
            }));

    public static final RegistryObject<MenuType<AutoDisenchanterContainer>> AUTO_DISENCHANTER_CONTAINER =
            CONTAINERS.register("auto_disenchanter_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                AutoDisenchanterBlockEntity autoDisenchanter = (AutoDisenchanterBlockEntity) inv.player.level.getBlockEntity(pos);
                return new AutoDisenchanterContainer(windowId, inv, autoDisenchanter);
            }));

    public static final RegistryObject<MenuType<AutoEnchanterContainer>> AUTO_ENCHANTER_CONTAINER =
            CONTAINERS.register("auto_enchanter_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                AutoEnchanterBlockEntity autoEnchanter = (AutoEnchanterBlockEntity) inv.player.level.getBlockEntity(pos);
                return new AutoEnchanterContainer(windowId, inv, autoEnchanter);
            }));

    public static final RegistryObject<MenuType<AutoAnvilContainer>> AUTO_ANVIL_CONTAINER =
            CONTAINERS.register("auto_anvil_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                AutoAnvilBlockEntity autoAnvil = (AutoAnvilBlockEntity) inv.player.level.getBlockEntity(pos);
                return new AutoAnvilContainer(windowId, inv, autoAnvil);
            }));

    public static final RegistryObject<MenuType<LaserDrillContainer>> LASER_DRILL_CONTAINER =
            CONTAINERS.register("laser_drill_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                LaserDrillBlockEntity laserDrill = (LaserDrillBlockEntity) inv.player.level.getBlockEntity(pos);
                return new LaserDrillContainer(windowId, inv, laserDrill);
            }));

    public static final RegistryObject<MenuType<LaserChargerContainer>> LASER_CHARGER_CONTAINER =
            CONTAINERS.register("laser_charger_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                LaserChargerBlockEntity laserCharger = (LaserChargerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new LaserChargerContainer(windowId, inv, laserCharger);
            }));

    public static final RegistryObject<MenuType<SteamBoilerContainer>> STEAM_BOILER_CONTAINER =
            CONTAINERS.register("steam_boiler_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                SteamBoilerBlockEntity steamBoiler = (SteamBoilerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new SteamBoilerContainer(windowId, inv, steamBoiler);
            }));

    public static final RegistryObject<MenuType<SteamTurbineContainer>> STEAM_TURBINE_CONTAINER =
            CONTAINERS.register("steam_turbine_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                SteamTurbineBlockEntity steamTurbine = (SteamTurbineBlockEntity) inv.player.level.getBlockEntity(pos);
                return new SteamTurbineContainer(windowId, inv, steamTurbine);
            }));

    public static final RegistryObject<MenuType<EthanolGeneratorContainer>> ETHANOL_GENERATOR_CONTAINER =
            CONTAINERS.register("ethanol_generator_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                EthanolGeneratorBlockEntity gen = (EthanolGeneratorBlockEntity) inv.player.level.getBlockEntity(pos);
                return new EthanolGeneratorContainer(windowId, inv, gen);
            }));

    public static final RegistryObject<MenuType<EthanolReactorContainer>> ETHANOL_REACTOR_CONTAINER =
            CONTAINERS.register("ethanol_reactor_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                EthanolReactorBlockEntity ethanolReactor = (EthanolReactorBlockEntity) inv.player.level.getBlockEntity(pos);
                return new EthanolReactorContainer(windowId, inv, ethanolReactor);
            }));

    public static final RegistryObject<MenuType<LavaFabContainer>> LAVA_FABRICATOR_CONTAINER =
            CONTAINERS.register("lava_fabricator_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                LavaFabBlockEntity lavaFabricator = (LavaFabBlockEntity) inv.player.level.getBlockEntity(pos);
                return new LavaFabContainer(windowId, inv, lavaFabricator);
            }));

    public static final RegistryObject<MenuType<ComposterContainer>> COMPOSTER_CONTAINER =
            CONTAINERS.register("composter_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                ComposterBlockEntity composter = (ComposterBlockEntity) inv.player.level.getBlockEntity(pos);
                return new ComposterContainer(windowId, inv, composter);
            }));

    public static final RegistryObject<MenuType<SludgeBoilerContainer>> SLUDGE_BOILER_CONTAINER =
            CONTAINERS.register("sludge_boiler_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                SludgeBoilerBlockEntity sludgeBoiler = (SludgeBoilerBlockEntity) inv.player.level.getBlockEntity(pos);
                return new SludgeBoilerContainer(windowId, inv, sludgeBoiler);
            }));

    public static final RegistryObject<MenuType<WeatherContainer>> WEATHER_CONTAINER =
            CONTAINERS.register("weather_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                WeatherBlockEntity weather = (WeatherBlockEntity) inv.player.level.getBlockEntity(pos);
                return new WeatherContainer(windowId, inv, weather);
            }));

    public static final RegistryObject<MenuType<ItemRouterContainer>> ITEM_ROUTER_CONTAINER =
            CONTAINERS.register("item_router_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                ItemRouterBlockEntity itemRouter = (ItemRouterBlockEntity) inv.player.level.getBlockEntity(pos);
                return new ItemRouterContainer(windowId, inv, itemRouter);
            }));

    public static final RegistryObject<MenuType<FurnaceGenContainer>> FURNACE_GENERATOR_CONTAINER =
            CONTAINERS.register("furnace_generator_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                FurnaceGenBlockEntity furnaceGen = (FurnaceGenBlockEntity) inv.player.level.getBlockEntity(pos);
                return new FurnaceGenContainer(windowId, inv, furnaceGen);
            }));

    public static final RegistryObject<MenuType<LavaGenContainer>> LAVA_GENERATOR_CONTAINER =
            CONTAINERS.register("lava_generator_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                LavaGenBlockEntity furnaceGen = (LavaGenBlockEntity) inv.player.level.getBlockEntity(pos);
                return new LavaGenContainer(windowId, inv, furnaceGen);
            }));
}
