package dev.terrarium.minefactoryrenewed.block.fluid;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraftforge.fluids.FluidAttributes;

public abstract class PinkSlimeFluid extends WaterFluid {

    @Override
    public Fluid getFlowing() {
        return ModBlocks.PINK_SLIME_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return ModBlocks.PINK_SLIME.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.PINK_SLIME_BUCKET.get();
    }

    @Override
    public BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.PINK_SLIME_FLUID_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(
                        new ResourceLocation(MinefactoryRenewed.MODID, "block/pink_slime_still"),
                        new ResourceLocation(MinefactoryRenewed.MODID, "block/pink_slime_flow")
                ).translationKey("block.minefactoryrenewed.pink_slime")
                .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
                .density(1000).viscosity(1000).build(ModBlocks.PINK_SLIME.get());
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == ModBlocks.PINK_SLIME.get() || fluid == ModBlocks.PINK_SLIME_FLOWING.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    public static class Flowing extends PinkSlimeFluid {

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends PinkSlimeFluid {

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
