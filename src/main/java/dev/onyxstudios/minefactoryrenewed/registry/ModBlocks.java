package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.block.ConveyorBeltBlock;
import dev.onyxstudios.minefactoryrenewed.block.fluid.BaseFluidBlock;
import dev.onyxstudios.minefactoryrenewed.block.fluid.SludgeFluid;
import dev.onyxstudios.minefactoryrenewed.block.machine.farming.FarmerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.farming.FertilizerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.farming.FruitPickerBlock;
import dev.onyxstudios.minefactoryrenewed.block.machine.farming.PlanterBlock;
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

    private static final BlockBehaviour.Properties SLUDGE_FLUID_PROPS = BlockBehaviour.Properties.of(Material.WATER)
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
    public static final RegistryObject<BlockItem> FERTILIZER_BLOCK = ITEMS.register("fertilizer", () ->
            new BlockItem(FERTILIZER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<Block> FRUIT_PICKER = BLOCKS.register("fruit_picker", FruitPickerBlock::new);
    public static final RegistryObject<BlockItem> FRUIT_PICKER_ITEM = ITEMS.register("fruit_picker", () ->
            new BlockItem(FRUIT_PICKER.get(), ModItems.PROPERTIES));

    public static final RegistryObject<SludgeFluid> SLUDGE = FLUIDS.register("sludge", SludgeFluid.Source::new);
    public static final RegistryObject<SludgeFluid> SLUDGE_FLOWING = FLUIDS.register("sludge_flowing", SludgeFluid.Flowing::new);
    public static final RegistryObject<BaseFluidBlock> SLUDGE_BLOCK = BLOCKS.register("sludge", () ->
            new BaseFluidBlock(SLUDGE::get, SLUDGE_FLUID_PROPS));
}
