package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.block.ConveyorBeltBlock;
import dev.onyxstudios.minefactoryrenewed.block.MeatBlock;
import dev.onyxstudios.minefactoryrenewed.block.PinkSlimeBlock;
import dev.onyxstudios.minefactoryrenewed.block.fluid.*;
import dev.onyxstudios.minefactoryrenewed.block.machine.animals.*;
import dev.onyxstudios.minefactoryrenewed.block.machine.blocks.BlockBreakerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.blocks.BlockPlacerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.blocks.BlockSmasherBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.blocks.DeepStorageBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.enchantment.AutoAnvilBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.enchantment.AutoDisenchanterBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.enchantment.AutoEnchanterBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.farming.FarmerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.farming.FertilizerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.farming.FruitPickerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.farming.PlanterBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.mobs.*;
import dev.onyxstudios.minefactoryrenewed.block.machine.processing.LaserChargerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.processing.LaserDrillBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MinefactoryRenewed.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MinefactoryRenewed.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MinefactoryRenewed.MODID);

    private static final BlockBehaviour.Properties BASE_FLUID_PROPS = BlockBehaviour.Properties.of(Material.WATER)
            .randomTicks().noDrops();

    public static final RegistryObject<Block> CONVEYOR_BELT = BLOCKS.register("conveyor_belt", ConveyorBeltBlock::new);
    public static final RegistryObject<BlockItem> CONVEYOR_BELT_ITEM = ITEMS.register("conveyor_belt", () ->
            new BlockItem(CONVEYOR_BELT.get(), new Item.Properties().stacksTo(16).tab(MinefactoryRenewed.TAB)));

    public static final RegistryObject<Block> PLANTER = BLOCKS.register("planter", PlanterBlock::new);
    public static final RegistryObject<BlockItem> PLANTER_ITEM = ITEMS.register("planter", () ->
            new BlockItem(PLANTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FARMER = BLOCKS.register("farmer", FarmerBlock::new);
    public static final RegistryObject<BlockItem> FARMER_ITEM = ITEMS.register("farmer", () ->
            new BlockItem(FARMER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FERTILIZER = BLOCKS.register("fertilizer", FertilizerBlock::new);
    public static final RegistryObject<BlockItem> FERTILIZER_ITEM = ITEMS.register("fertilizer", () ->
            new BlockItem(FERTILIZER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FRUIT_PICKER = BLOCKS.register("fruit_picker", FruitPickerBlock::new);
    public static final RegistryObject<BlockItem> FRUIT_PICKER_ITEM = ITEMS.register("fruit_picker", () ->
            new BlockItem(FRUIT_PICKER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RAW_MEAT_BLOCK = BLOCKS.register("raw_meat_block", MeatBlock::new);
    public static final RegistryObject<BlockItem> RAW_MEAT_BLOCK_ITEM = ITEMS.register("raw_meat_block", () ->
            new BlockItem(RAW_MEAT_BLOCK.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> COOKED_MEAT_BLOCK = BLOCKS.register("cooked_meat_block", MeatBlock::new);
    public static final RegistryObject<BlockItem> COOKED_MEAT_BLOCK_ITEM = ITEMS.register("cooked_meat_block", () ->
            new BlockItem(COOKED_MEAT_BLOCK.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> PINK_SLIME_BLOCK = BLOCKS.register("pink_slime_block", PinkSlimeBlock::new);
    public static final RegistryObject<BlockItem> PINK_SLIME_BLOCK_ITEM = ITEMS.register("pink_slime_block", () ->
            new BlockItem(PINK_SLIME_BLOCK.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> SLAUGHTERHOUSE = BLOCKS.register("slaughterhouse", SlaughterhouseBlock::new);
    public static final RegistryObject<BlockItem> SLAUGHTERHOUSE_ITEM = ITEMS.register("slaughterhouse", () ->
            new BlockItem(SLAUGHTERHOUSE.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> GRINDER = BLOCKS.register("grinder", GrinderBlock::new);
    public static final RegistryObject<BlockItem> GRINDER_ITEM = ITEMS.register("grinder", () ->
            new BlockItem(GRINDER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> MEAT_PACKER = BLOCKS.register("meat_packer", MeatPackerBlock::new);
    public static final RegistryObject<BlockItem> MEAT_PACKER_ITEM = ITEMS.register("meat_packer", () ->
            new BlockItem(MEAT_PACKER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> MOB_COUNTER = BLOCKS.register("mob_counter", MobCounterBlock::new);
    public static final RegistryObject<BlockItem> MOB_COUNTER_ITEM = ITEMS.register("mob_counter", () ->
            new BlockItem(MOB_COUNTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> MOB_ROUTER = BLOCKS.register("mob_router", MobRouterBlock::new);
    public static final RegistryObject<BlockItem> MOB_ROUTER_ITEM = ITEMS.register("mob_router", () ->
            new BlockItem(MOB_ROUTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> AUTO_SPAWNER = BLOCKS.register("auto_spawner", AutoSpawnerBlock::new);
    public static final RegistryObject<BlockItem> AUTO_SPAWNER_ITEM = ITEMS.register("auto_spawner", () ->
            new BlockItem(AUTO_SPAWNER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FISHER = BLOCKS.register("fisher", FisherBlock::new);
    public static final RegistryObject<BlockItem> FISHER_ITEM = ITEMS.register("fisher", () ->
            new BlockItem(FISHER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> RANCHER = BLOCKS.register("rancher", RancherBlock::new);
    public static final RegistryObject<BlockItem> RANCHER_ITEM = ITEMS.register("rancher", () ->
            new BlockItem(RANCHER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> BREEDER = BLOCKS.register("breeder", BreederBlock::new);
    public static final RegistryObject<BlockItem> BREEDER_ITEM = ITEMS.register("breeder", () ->
            new BlockItem(BREEDER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> CHRONOTYPER = BLOCKS.register("chronotyper", ChronotyperBlock::new);
    public static final RegistryObject<BlockItem> CHRONOTYPER_ITEM = ITEMS.register("chronotyper", () ->
            new BlockItem(CHRONOTYPER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> SEWER = BLOCKS.register("sewer", SewerBlock::new);
    public static final RegistryObject<BlockItem> SEWER_ITEM = ITEMS.register("sewer", () ->
            new BlockItem(SEWER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> VETERINARY = BLOCKS.register("veterinary", VeterinaryBlock::new);
    public static final RegistryObject<BlockItem> VETERINARY_ITEM = ITEMS.register("veterinary", () ->
            new BlockItem(VETERINARY.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> BLOCK_PLACER = BLOCKS.register("block_placer", BlockPlacerBlock::new);
    public static final RegistryObject<BlockItem> BLOCK_PLACER_ITEM = ITEMS.register("block_placer", () ->
            new BlockItem(BLOCK_PLACER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> BLOCK_BREAKER = BLOCKS.register("block_breaker", BlockBreakerBlock::new);
    public static final RegistryObject<BlockItem> BLOCK_BREAKER_ITEM = ITEMS.register("block_breaker", () ->
            new BlockItem(BLOCK_BREAKER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> BLOCK_SMASHER = BLOCKS.register("block_smasher", BlockSmasherBlock::new);
    public static final RegistryObject<BlockItem> BLOCK_SMASHER_ITEM = ITEMS.register("block_smasher", () ->
            new BlockItem(BLOCK_SMASHER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> DEEP_STORAGE = BLOCKS.register("deep_storage", DeepStorageBlock::new);
    public static final RegistryObject<BlockItem> DEEP_STORAGE_ITEM = ITEMS.register("deep_storage", () ->
            new BlockItem(DEEP_STORAGE.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> AUTO_DISENCHANTER = BLOCKS.register("auto_disenchanter", AutoDisenchanterBlock::new);
    public static final RegistryObject<Item> AUTO_DISENCHANTER_ITEM = ITEMS.register("auto_disenchanter", () ->
            new BlockItem(AUTO_DISENCHANTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> AUTO_ENCHANTER = BLOCKS.register("auto_enchanter", AutoEnchanterBlock::new);
    public static final RegistryObject<Item> AUTO_ENCHANTER_ITEM = ITEMS.register("auto_enchanter", () ->
            new BlockItem(AUTO_ENCHANTER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> AUTO_ANVIL = BLOCKS.register("auto_anvil", AutoAnvilBlock::new);
    public static final RegistryObject<Item> AUTO_ANVIL_ITEM = ITEMS.register("auto_anvil", () ->
            new BlockItem(AUTO_ANVIL.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> LASER_DRILL = BLOCKS.register("laser_drill", LaserDrillBlock::new);
    public static final RegistryObject<Item> LASER_DRILL_ITEM = ITEMS.register("laser_drill", () ->
            new BlockItem(LASER_DRILL.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> LASER_CHARGER = BLOCKS.register("laser_charger", LaserChargerBlock::new);
    public static final RegistryObject<Item> LASER_CHARGER_ITEM = ITEMS.register("laser_charger", () ->
            new BlockItem(LASER_CHARGER.get(), ModItems.PROPERTIES));

    //Fluids
    public static final RegistryObject<SludgeFluid> SLUDGE = FLUIDS.register("sludge", SludgeFluid.Source::new);
    public static final RegistryObject<SludgeFluid> SLUDGE_FLOWING = FLUIDS.register("sludge_flowing", SludgeFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> SLUDGE_BLOCK = BLOCKS.register("sludge", () ->
            new BaseFluidBlock(SLUDGE::get, BASE_FLUID_PROPS));

    public static final RegistryObject<MeatFluid> MEAT = FLUIDS.register("meat", MeatFluid.Source::new);
    public static final RegistryObject<MeatFluid> MEAT_FLOWING = FLUIDS.register("meat_flowing", MeatFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> MEAT_FLUID_BLOCK = BLOCKS.register("meat", () ->
            new BaseFluidBlock(MEAT::get, BASE_FLUID_PROPS));

    public static final RegistryObject<PinkSlimeFluid> PINK_SLIME = FLUIDS.register("pink_slime", PinkSlimeFluid.Source::new);
    public static final RegistryObject<PinkSlimeFluid> PINK_SLIME_FLOWING = FLUIDS.register("pink_slime_flowing", PinkSlimeFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> PINK_SLIME_FLUID_BLOCK = BLOCKS.register("pink_slime", () ->
            new PinkSlimeFluidBlock(PINK_SLIME::get, BASE_FLUID_PROPS));

    public static final RegistryObject<EssenceFluid> ESSENCE = FLUIDS.register("essence", EssenceFluid.Source::new);
    public static final RegistryObject<EssenceFluid> ESSENCE_FLOWING = FLUIDS.register("essence_flowing", EssenceFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> ESSENCE_FLUID_BLOCK = BLOCKS.register("essence", () ->
            new BaseFluidBlock(ESSENCE::get, BASE_FLUID_PROPS));

    public static final RegistryObject<SewageFluid> SEWAGE = FLUIDS.register("sewage", SewageFluid.Source::new);
    public static final RegistryObject<SewageFluid> SEWAGE_FLOWING = FLUIDS.register("sewage_flowing", SewageFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> SEWAGE_FLUID_BLOCK = BLOCKS.register("sewage", () ->
            new BaseFluidBlock(SEWAGE::get, BASE_FLUID_PROPS));
}
