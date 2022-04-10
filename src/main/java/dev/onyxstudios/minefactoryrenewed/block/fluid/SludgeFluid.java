package dev.onyxstudios.minefactoryrenewed.block.fluid;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
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

public abstract class SludgeFluid extends WaterFluid {

    @Override
    public Fluid getFlowing() {
        return ModBlocks.SLUDGE_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return ModBlocks.SLUDGE.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.SLUDGE_BUCKET.get();
    }

    @Override
    public BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.SLUDGE_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(
                        new ResourceLocation(MinefactoryRenewed.MODID, "block/sludge_still"),
                        new ResourceLocation(MinefactoryRenewed.MODID, "block/sludge_flow")
                ).translationKey("block.minefactoryrenewed.sludge")
                .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
                .density(2000).viscosity(8000).build(ModBlocks.SLUDGE.get());
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == ModBlocks.SLUDGE.get() || fluid == ModBlocks.SLUDGE_FLOWING.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    public static class Flowing extends SludgeFluid {

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

    public static class Source extends SludgeFluid {

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
