package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@Mod.EventBusSubscriber(modid = MinefactoryRenewed.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModWorldGen {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MinefactoryRenewed.MODID);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MinefactoryRenewed.MODID);

    private static final List<Biome.BiomeCategory> VALID_RUBBER_BIOMES = List.of(
            Biome.BiomeCategory.EXTREME_HILLS,
            Biome.BiomeCategory.PLAINS,
            Biome.BiomeCategory.BEACH,
            Biome.BiomeCategory.FOREST
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> RUBBER_TREE_FEATURE = CONFIGURED_FEATURES.register("rubber_trees", () -> new ConfiguredFeature<>(Feature.TREE, createRubber()));
    public static final RegistryObject<PlacedFeature> RUBBER_TREES_PLACED = PLACED_FEATURES.register("rubber_trees", () ->
            new PlacedFeature(
                    RUBBER_TREE_FEATURE.getHolder().orElseThrow(),
                    List.of(
                            RarityFilter.onAverageOnceEvery(20),
                            InSquarePlacement.spread(),
                            SurfaceWaterDepthFilter.forMaxDepth(0),
                            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                            BlockPredicateFilter.forPredicate(
                                    BlockPredicate.wouldSurvive(ModBlocks.RUBBER_SAPLING.get().defaultBlockState(), BlockPos.ZERO)
                            ),
                            BiomeFilter.biome()
                    )
            )
    );

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        if (VALID_RUBBER_BIOMES.contains(event.getCategory())) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RUBBER_TREES_PLACED.getHolder().orElseThrow());
        }
    }

    private static TreeConfiguration createRubber() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.RUBBER_LOG.get()),
                new StraightTrunkPlacer(5, 2, 0),
                BlockStateProvider.simple(ModBlocks.RUBBER_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().build();
    }

    private static PlacedFeature createPlaced(Holder<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placements) {
        return new PlacedFeature(configuredFeature, List.of(placements));
    }
}
