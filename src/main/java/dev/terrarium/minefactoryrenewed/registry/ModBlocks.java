package dev.terrarium.minefactoryrenewed.registry;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.block.MeatBlock;
import dev.terrarium.minefactoryrenewed.block.PinkSlimeBlock;
import dev.terrarium.minefactoryrenewed.block.RoadBlock;
import dev.terrarium.minefactoryrenewed.block.fluid.*;
import dev.terrarium.minefactoryrenewed.block.generator.*;
import dev.terrarium.minefactoryrenewed.block.machine.animals.*;
import dev.terrarium.minefactoryrenewed.block.machine.blocks.BlockBreakerBlock;
import dev.terrarium.minefactoryrenewed.block.machine.blocks.BlockPlacerBlock;
import dev.terrarium.minefactoryrenewed.block.machine.blocks.BlockSmasherBlock;
import dev.terrarium.minefactoryrenewed.block.machine.blocks.DeepStorageBlock;
import dev.terrarium.minefactoryrenewed.block.machine.enchantment.AutoAnvilBlock;
import dev.terrarium.minefactoryrenewed.block.machine.enchantment.AutoDisenchanterBlock;
import dev.terrarium.minefactoryrenewed.block.machine.enchantment.AutoEnchanterBlock;
import dev.terrarium.minefactoryrenewed.block.machine.farming.FarmerBlock;
import dev.terrarium.minefactoryrenewed.block.machine.farming.FertilizerBlock;
import dev.terrarium.minefactoryrenewed.block.machine.farming.FruitPickerBlock;
import dev.terrarium.minefactoryrenewed.block.machine.farming.PlanterBlock;
import dev.terrarium.minefactoryrenewed.block.machine.mobs.*;
import dev.terrarium.minefactoryrenewed.block.machine.power.CreativeEnergyBlock;
import dev.terrarium.minefactoryrenewed.block.machine.power.EthanolGeneratorBlock;
import dev.terrarium.minefactoryrenewed.block.machine.power.SteamTurbineBlock;
import dev.terrarium.minefactoryrenewed.block.machine.processing.ComposterBlock;
import dev.terrarium.minefactoryrenewed.block.machine.processing.*;
import dev.terrarium.minefactoryrenewed.block.rubber.RubberLogBlock;
import dev.terrarium.minefactoryrenewed.block.rubber.RubberStandingSignBlock;
import dev.terrarium.minefactoryrenewed.block.rubber.RubberTreeGrower;
import dev.terrarium.minefactoryrenewed.block.rubber.RubberWallSignBlock;
import dev.terrarium.minefactoryrenewed.block.transport.ConveyorBeltBlock;
import dev.terrarium.minefactoryrenewed.block.transport.EjectorBlock;
import dev.terrarium.minefactoryrenewed.block.transport.ItemCollectorBlock;
import dev.terrarium.minefactoryrenewed.block.transport.ItemRouterBlock;
import dev.terrarium.minefactoryrenewed.blockentity.generator.LunarGenBlockEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MinefactoryRenewed.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MinefactoryRenewed.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MinefactoryRenewed.MODID);

    private static final BlockBehaviour.Properties BASE_FLUID_PROPS = BlockBehaviour.Properties.of(Material.WATER).randomTicks().noDrops();
    private static final BlockBehaviour.Properties PLANK_PROPERTIES = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);

    public static final WoodType RUBBER_TYPE = WoodType.register(WoodType.create(MinefactoryRenewed.MODID + ":rubber"));

    public static final RegistryObject<Block> ROAD = BLOCKS.register("road", RoadBlock::new);
    public static final RegistryObject<BlockItem> ROAD_ITEM = ITEMS.register("road", () -> new BlockItem(ROAD.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> DARK_ROAD = BLOCKS.register("dark_road", RoadBlock::new);
    public static final RegistryObject<BlockItem> DARK_ROAD_ITEM = ITEMS.register("dark_road", () -> new BlockItem(DARK_ROAD.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> CONVEYOR_BELT = BLOCKS.register("conveyor_belt", ConveyorBeltBlock::new);
    public static final RegistryObject<BlockItem> CONVEYOR_BELT_ITEM = ITEMS.register("conveyor_belt", () -> new BlockItem(CONVEYOR_BELT.get(), new Item.Properties().stacksTo(16).tab(MinefactoryRenewed.TAB)));

    public static final RegistryObject<Block> CREATIVE_ENERGY = BLOCKS.register("creative_energy", CreativeEnergyBlock::new);
    public static final RegistryObject<BlockItem> CREATIVE_ENERGY_ITEM = ITEMS.register("creative_energy", () -> new BlockItem(CREATIVE_ENERGY.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> PLANTER = BLOCKS.register("planter", PlanterBlock::new);
    public static final RegistryObject<BlockItem> PLANTER_ITEM = ITEMS.register("planter", () -> new BlockItem(PLANTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FARMER = BLOCKS.register("farmer", FarmerBlock::new);
    public static final RegistryObject<BlockItem> FARMER_ITEM = ITEMS.register("farmer", () -> new BlockItem(FARMER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FERTILIZER = BLOCKS.register("fertilizer", FertilizerBlock::new);
    public static final RegistryObject<BlockItem> FERTILIZER_ITEM = ITEMS.register("fertilizer", () -> new BlockItem(FERTILIZER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FRUIT_PICKER = BLOCKS.register("fruit_picker", FruitPickerBlock::new);
    public static final RegistryObject<BlockItem> FRUIT_PICKER_ITEM = ITEMS.register("fruit_picker", () -> new BlockItem(FRUIT_PICKER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RAW_MEAT_BLOCK = BLOCKS.register("raw_meat_block", MeatBlock::new);
    public static final RegistryObject<BlockItem> RAW_MEAT_BLOCK_ITEM = ITEMS.register("raw_meat_block", () -> new BlockItem(RAW_MEAT_BLOCK.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> COOKED_MEAT_BLOCK = BLOCKS.register("cooked_meat_block", MeatBlock::new);
    public static final RegistryObject<BlockItem> COOKED_MEAT_BLOCK_ITEM = ITEMS.register("cooked_meat_block", () -> new BlockItem(COOKED_MEAT_BLOCK.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> PINK_SLIME_BLOCK = BLOCKS.register("pink_slime_block", PinkSlimeBlock::new);
    public static final RegistryObject<BlockItem> PINK_SLIME_BLOCK_ITEM = ITEMS.register("pink_slime_block", () -> new BlockItem(PINK_SLIME_BLOCK.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> SLAUGHTERHOUSE = BLOCKS.register("slaughterhouse", SlaughterhouseBlock::new);
    public static final RegistryObject<BlockItem> SLAUGHTERHOUSE_ITEM = ITEMS.register("slaughterhouse", () -> new BlockItem(SLAUGHTERHOUSE.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> GRINDER = BLOCKS.register("grinder", GrinderBlock::new);
    public static final RegistryObject<BlockItem> GRINDER_ITEM = ITEMS.register("grinder", () -> new BlockItem(GRINDER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> MEAT_PACKER = BLOCKS.register("meat_packer", MeatPackerBlock::new);
    public static final RegistryObject<BlockItem> MEAT_PACKER_ITEM = ITEMS.register("meat_packer", () -> new BlockItem(MEAT_PACKER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> MOB_COUNTER = BLOCKS.register("mob_counter", MobCounterBlock::new);
    public static final RegistryObject<BlockItem> MOB_COUNTER_ITEM = ITEMS.register("mob_counter", () -> new BlockItem(MOB_COUNTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> MOB_ROUTER = BLOCKS.register("mob_router", MobRouterBlock::new);
    public static final RegistryObject<BlockItem> MOB_ROUTER_ITEM = ITEMS.register("mob_router", () -> new BlockItem(MOB_ROUTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> AUTO_SPAWNER = BLOCKS.register("auto_spawner", AutoSpawnerBlock::new);
    public static final RegistryObject<BlockItem> AUTO_SPAWNER_ITEM = ITEMS.register("auto_spawner", () -> new BlockItem(AUTO_SPAWNER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FISHER = BLOCKS.register("fisher", FisherBlock::new);
    public static final RegistryObject<BlockItem> FISHER_ITEM = ITEMS.register("fisher", () -> new BlockItem(FISHER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RANCHER = BLOCKS.register("rancher", RancherBlock::new);
    public static final RegistryObject<BlockItem> RANCHER_ITEM = ITEMS.register("rancher", () -> new BlockItem(RANCHER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> BREEDER = BLOCKS.register("breeder", BreederBlock::new);
    public static final RegistryObject<BlockItem> BREEDER_ITEM = ITEMS.register("breeder", () -> new BlockItem(BREEDER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> CHRONOTYPER = BLOCKS.register("chronotyper", ChronotyperBlock::new);
    public static final RegistryObject<BlockItem> CHRONOTYPER_ITEM = ITEMS.register("chronotyper", () -> new BlockItem(CHRONOTYPER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> SEWER = BLOCKS.register("sewer", SewerBlock::new);
    public static final RegistryObject<BlockItem> SEWER_ITEM = ITEMS.register("sewer", () -> new BlockItem(SEWER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> VETERINARY = BLOCKS.register("veterinary", VeterinaryBlock::new);
    public static final RegistryObject<BlockItem> VETERINARY_ITEM = ITEMS.register("veterinary", () -> new BlockItem(VETERINARY.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> BLOCK_PLACER = BLOCKS.register("block_placer", BlockPlacerBlock::new);
    public static final RegistryObject<BlockItem> BLOCK_PLACER_ITEM = ITEMS.register("block_placer", () -> new BlockItem(BLOCK_PLACER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> BLOCK_BREAKER = BLOCKS.register("block_breaker", BlockBreakerBlock::new);
    public static final RegistryObject<BlockItem> BLOCK_BREAKER_ITEM = ITEMS.register("block_breaker", () -> new BlockItem(BLOCK_BREAKER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> BLOCK_SMASHER = BLOCKS.register("block_smasher", BlockSmasherBlock::new);
    public static final RegistryObject<BlockItem> BLOCK_SMASHER_ITEM = ITEMS.register("block_smasher", () -> new BlockItem(BLOCK_SMASHER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> DEEP_STORAGE = BLOCKS.register("deep_storage", DeepStorageBlock::new);
    public static final RegistryObject<BlockItem> DEEP_STORAGE_ITEM = ITEMS.register("deep_storage", () -> new BlockItem(DEEP_STORAGE.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> AUTO_DISENCHANTER = BLOCKS.register("auto_disenchanter", AutoDisenchanterBlock::new);
    public static final RegistryObject<BlockItem> AUTO_DISENCHANTER_ITEM = ITEMS.register("auto_disenchanter", () -> new BlockItem(AUTO_DISENCHANTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> AUTO_ENCHANTER = BLOCKS.register("auto_enchanter", AutoEnchanterBlock::new);
    public static final RegistryObject<BlockItem> AUTO_ENCHANTER_ITEM = ITEMS.register("auto_enchanter", () -> new BlockItem(AUTO_ENCHANTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> AUTO_ANVIL = BLOCKS.register("auto_anvil", AutoAnvilBlock::new);
    public static final RegistryObject<BlockItem> AUTO_ANVIL_ITEM = ITEMS.register("auto_anvil", () -> new BlockItem(AUTO_ANVIL.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> LASER_DRILL = BLOCKS.register("laser_drill", LaserDrillBlock::new);
    public static final RegistryObject<BlockItem> LASER_DRILL_ITEM = ITEMS.register("laser_drill", () -> new BlockItem(LASER_DRILL.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> LASER_CHARGER = BLOCKS.register("laser_charger", LaserChargerBlock::new);
    public static final RegistryObject<BlockItem> LASER_CHARGER_ITEM = ITEMS.register("laser_charger", () -> new BlockItem(LASER_CHARGER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> STEAM_BOILER = BLOCKS.register("steam_boiler", SteamBoilerBlock::new);
    public static final RegistryObject<BlockItem> STEAM_BOILER_ITEM = ITEMS.register("steam_boiler", () -> new BlockItem(STEAM_BOILER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> STEAM_TURBINE = BLOCKS.register("steam_turbine", SteamTurbineBlock::new);
    public static final RegistryObject<BlockItem> STEAM_TURBINE_ITEM = ITEMS.register("steam_turbine", () -> new BlockItem(STEAM_TURBINE.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> ETHANOL_GENERATOR = BLOCKS.register("ethanol_generator", EthanolGeneratorBlock::new);
    public static final RegistryObject<BlockItem> ETHANOL_GENERATOR_ITEM = ITEMS.register("ethanol_generator", () -> new BlockItem(ETHANOL_GENERATOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> ETHANOL_REACTOR = BLOCKS.register("ethanol_reactor", EthanolReactorBlock::new);
    public static final RegistryObject<BlockItem> ETHANOL_REACTOR_ITEM = ITEMS.register("ethanol_reactor", () -> new BlockItem(ETHANOL_REACTOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> LAVA_FABRICATOR = BLOCKS.register("lava_fabricator", LavaFabBlock::new);
    public static final RegistryObject<BlockItem> LAVA_FABRICATOR_ITEM = ITEMS.register("lava_fabricator", () -> new BlockItem(LAVA_FABRICATOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> COMPOSTER = BLOCKS.register("composter", ComposterBlock::new);
    public static final RegistryObject<BlockItem> COMPOSTER_ITEM = ITEMS.register("composter", () -> new BlockItem(COMPOSTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> SLUDGE_BOILER = BLOCKS.register("sludge_boiler", SludgeBoilerBlock::new);
    public static final RegistryObject<BlockItem> SLUDGE_BOILER_ITEM = ITEMS.register("sludge_boiler", () -> new BlockItem(SLUDGE_BOILER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> WEATHER = BLOCKS.register("weather", WeatherBlock::new);
    public static final RegistryObject<BlockItem> WEATHER_ITEM = ITEMS.register("weather", () -> new BlockItem(WEATHER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> ITEM_COLLECTOR = BLOCKS.register("item_collector", ItemCollectorBlock::new);
    public static final RegistryObject<BlockItem> ITEM_COLLECTOR_ITEM = ITEMS.register("item_collector", () -> new BlockItem(ITEM_COLLECTOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> EJECTOR = BLOCKS.register("ejector", EjectorBlock::new);
    public static final RegistryObject<BlockItem> EJECTOR_ITEM = ITEMS.register("ejector", () -> new BlockItem(EJECTOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> ITEM_ROUTER = BLOCKS.register("item_router", ItemRouterBlock::new);
    public static final RegistryObject<BlockItem> ITEM_ROUTER_ITEM = ITEMS.register("item_router", () -> new BlockItem(ITEM_ROUTER.get(), ModItems.PROPERTIES));

    //Generators for adrian's sanity
    public static final RegistryObject<Block> FURNACE_GENERATOR = BLOCKS.register("furnace_generator", FurnaceGenBlock::new);
    public static final RegistryObject<BlockItem> FURNACE_GENERATOR_ITEM = ITEMS.register("furnace_generator", () -> new BlockItem(FURNACE_GENERATOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> LAVA_GENERATOR = BLOCKS.register("lava_generator", LavaGenBlock::new);
    public static final RegistryObject<BlockItem> LAVA_GENERATOR_ITEM = ITEMS.register("lava_generator", () -> new BlockItem(LAVA_GENERATOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> CULINARY_GENERATOR = BLOCKS.register("culinary_generator", CulinaryGenBlock::new);
    public static final RegistryObject<BlockItem> CULINARY_GENERATOR_ITEM = ITEMS.register("culinary_generator", () -> new BlockItem(CULINARY_GENERATOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> LUNAR_GENERATOR = BLOCKS.register("lunar_generator", LunarGenBlock::new);
    public static final RegistryObject<BlockItem> LUNAR_GENERATOR_ITEM = ITEMS.register("lunar_generator", () -> new BlockItem(LUNAR_GENERATOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> SOLAR_GENERATOR = BLOCKS.register("solar_generator", SolarGenBlock::new);
    public static final RegistryObject<BlockItem> SOLAR_GENERATOR_ITEM = ITEMS.register("solar_generator", () -> new BlockItem(SOLAR_GENERATOR.get(), ModItems.PROPERTIES));



    //Rubbery stuff
    public static final RegistryObject<Block> RUBBER_LOG = BLOCKS.register("rubber_log", () -> new RubberLogBlock(ModBlocks.STRIPPED_RUBBER_LOG));
    public static final RegistryObject<BlockItem> RUBBER_LOG_ITEM = ITEMS.register("rubber_log", () -> new BlockItem(RUBBER_LOG.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_WOOD = BLOCKS.register("rubber_wood", () -> new RubberLogBlock(ModBlocks.STRIPPED_RUBBER_WOOD));
    public static final RegistryObject<BlockItem> RUBBER_WOOD_ITEM = ITEMS.register("rubber_wood", () -> new BlockItem(RUBBER_WOOD.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> STRIPPED_RUBBER_LOG = BLOCKS.register("stripped_rubber_log", ModBlocks::log);
    public static final RegistryObject<BlockItem> STRIPPED_RUBBER_LOG_ITEM = ITEMS.register("stripped_rubber_log", () -> new BlockItem(STRIPPED_RUBBER_LOG.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> STRIPPED_RUBBER_WOOD = BLOCKS.register("stripped_rubber_wood", () -> new Block(PLANK_PROPERTIES));
    public static final RegistryObject<BlockItem> STRIPPED_RUBBER_WOOD_ITEM = ITEMS.register("stripped_rubber_wood", () -> new BlockItem(STRIPPED_RUBBER_WOOD.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_PLANKS = BLOCKS.register("rubber_planks", () -> new Block(PLANK_PROPERTIES));
    public static final RegistryObject<BlockItem> RUBBER_PLANKS_ITEM = ITEMS.register("rubber_planks", () -> new BlockItem(RUBBER_PLANKS.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_LEAVES = BLOCKS.register("rubber_leaves", ModBlocks::leaves);
    public static final RegistryObject<BlockItem> RUBBER_LEAVES_ITEM = ITEMS.register("rubber_leaves", () -> new BlockItem(RUBBER_LEAVES.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_STAIRS = BLOCKS.register("rubber_stairs", () -> new StairBlock(RUBBER_PLANKS.get()::defaultBlockState, BlockBehaviour.Properties.copy(RUBBER_PLANKS.get())));
    public static final RegistryObject<BlockItem> RUBBER_STAIRS_ITEM = ITEMS.register("rubber_stairs", () -> new BlockItem(RUBBER_STAIRS.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_SIGN = BLOCKS.register("rubber_sign", RubberStandingSignBlock::new);
    public static final RegistryObject<Block> RUBBER_WALL_SIGN = BLOCKS.register("rubber_wall_sign", RubberWallSignBlock::new);
    public static final RegistryObject<BlockItem> RUBBER_SIGN_ITEM = ITEMS.register("rubber_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(MinefactoryRenewed.TAB), RUBBER_SIGN.get(), RUBBER_WALL_SIGN.get()));

    public static final RegistryObject<Block> RUBBER_FENCE = BLOCKS.register("rubber_fence", () -> new FenceBlock(PLANK_PROPERTIES));
    public static final RegistryObject<BlockItem> RUBBER_FENCE_ITEM = ITEMS.register("rubber_fence", () -> new BlockItem(RUBBER_FENCE.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_TRAPDOOR = BLOCKS.register("rubber_trapdoor", () -> new TrapDoorBlock(PLANK_PROPERTIES.noOcclusion().isValidSpawn((state, getter, pos, entityType) -> false)));
    public static final RegistryObject<BlockItem> RUBBER_TRAPDOOR_ITEM = ITEMS.register("rubber_trapdoor", () -> new BlockItem(RUBBER_TRAPDOOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_FENCE_GATE = BLOCKS.register("rubber_fence_gate", () -> new FenceGateBlock(PLANK_PROPERTIES));
    public static final RegistryObject<BlockItem> RUBBER_FENCE_GATE_ITEM = ITEMS.register("rubber_fence_gate", () -> new BlockItem(RUBBER_FENCE_GATE.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_SLAB = BLOCKS.register("rubber_slab", () -> new SlabBlock(PLANK_PROPERTIES));
    public static final RegistryObject<BlockItem> RUBBER_SLAB_ITEM = ITEMS.register("rubber_slab", () -> new BlockItem(RUBBER_SLAB.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_DOOR = BLOCKS.register("rubber_door", () -> new DoorBlock(PLANK_PROPERTIES.noCollission()));
    public static final RegistryObject<BlockItem> RUBBER_DOOR_ITEM = ITEMS.register("rubber_door", () -> new DoubleHighBlockItem(RUBBER_DOOR.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RUBBER_SAPLING = BLOCKS.register("rubber_sapling", () -> new SaplingBlock(new RubberTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Item> RUBBER_SAPLING_ITEM = ITEMS.register("rubber_sapling", () -> new BlockItem(RUBBER_SAPLING.get(), ModItems.PROPERTIES));

    //Fluids
    public static final RegistryObject<SludgeFluid> SLUDGE = FLUIDS.register("sludge", SludgeFluid.Source::new);
    public static final RegistryObject<SludgeFluid> SLUDGE_FLOWING = FLUIDS.register("sludge_flowing", SludgeFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> SLUDGE_BLOCK = BLOCKS.register("sludge", () -> new BaseFluidBlock(SLUDGE::get, BASE_FLUID_PROPS));

    public static final RegistryObject<MeatFluid> MEAT = FLUIDS.register("meat", MeatFluid.Source::new);
    public static final RegistryObject<MeatFluid> MEAT_FLOWING = FLUIDS.register("meat_flowing", MeatFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> MEAT_FLUID_BLOCK = BLOCKS.register("meat", () -> new BaseFluidBlock(MEAT::get, BASE_FLUID_PROPS));

    public static final RegistryObject<PinkSlimeFluid> PINK_SLIME = FLUIDS.register("pink_slime", PinkSlimeFluid.Source::new);
    public static final RegistryObject<PinkSlimeFluid> PINK_SLIME_FLOWING = FLUIDS.register("pink_slime_flowing", PinkSlimeFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> PINK_SLIME_FLUID_BLOCK = BLOCKS.register("pink_slime", () -> new PinkSlimeFluidBlock(PINK_SLIME::get, BASE_FLUID_PROPS));

    public static final RegistryObject<EssenceFluid> ESSENCE = FLUIDS.register("essence", EssenceFluid.Source::new);
    public static final RegistryObject<EssenceFluid> ESSENCE_FLOWING = FLUIDS.register("essence_flowing", EssenceFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> ESSENCE_FLUID_BLOCK = BLOCKS.register("essence", () -> new BaseFluidBlock(ESSENCE::get, BASE_FLUID_PROPS));

    public static final RegistryObject<SewageFluid> SEWAGE = FLUIDS.register("sewage", SewageFluid.Source::new);
    public static final RegistryObject<SewageFluid> SEWAGE_FLOWING = FLUIDS.register("sewage_flowing", SewageFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> SEWAGE_FLUID_BLOCK = BLOCKS.register("sewage", () -> new BaseFluidBlock(SEWAGE::get, BASE_FLUID_PROPS));

    public static final RegistryObject<SteamFluid> STEAM = FLUIDS.register("steam", SteamFluid.Source::new);
    public static final RegistryObject<SteamFluid> STEAM_FLOWING = FLUIDS.register("steam_flowing", SteamFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> STEAM_FLUID_BLOCK = BLOCKS.register("steam", () -> new BaseFluidBlock(STEAM::get, BASE_FLUID_PROPS));

    public static final RegistryObject<EthanolFluid> ETHANOL = FLUIDS.register("ethanol", EthanolFluid.Source::new);
    public static final RegistryObject<EthanolFluid> ETHANOL_FLOWING = FLUIDS.register("ethanol_flowing", EthanolFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> ETHANOL_FLUID_BLOCK = BLOCKS.register("ethanol", () -> new BaseFluidBlock(ETHANOL::get, BASE_FLUID_PROPS));

    private static RotatedPillarBlock log() {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> MaterialColor.WOOD)
                .strength(2.0F)
                .sound(SoundType.WOOD));
    }

    private static LeavesBlock leaves() {
        return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn((state, getter, pos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT)
                .isSuffocating((state, getter, pos) -> false)
                .isViewBlocking((state, getter, pos) -> false));
    }
}
