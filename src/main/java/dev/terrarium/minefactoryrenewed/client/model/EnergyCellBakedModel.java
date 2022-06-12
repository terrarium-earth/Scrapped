package dev.terrarium.minefactoryrenewed.client.model;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.api.machine.MachineConfigType;
import dev.terrarium.minefactoryrenewed.client.model.data.ModelDataTypes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnergyCellBakedModel implements IDynamicBakedModel {

    public static final ResourceLocation INPUT_OVERLAY = new ResourceLocation(MinefactoryRenewed.MODID, "block/energy_cell/input");
    public static final ResourceLocation OUTPUT_OVERLAY = new ResourceLocation(MinefactoryRenewed.MODID, "block/energy_cell/output");
    public static final ResourceLocation INPUT_OUTPUT_OVERLAY = new ResourceLocation(MinefactoryRenewed.MODID, "block/energy_cell/input_output");
    public static BakedModel INPUT_MODEL;
    public static BakedModel OUTPUT_MODEL;
    public static BakedModel INPUT_OUTPUT_MODEL;

    private final BakedModel originModel;

    public EnergyCellBakedModel(BakedModel originModel) {
        this.originModel = originModel;
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        List<BakedQuad> quads = new ArrayList<>(originModel.getQuads(state, null, rand, extraData));
        quads.addAll(originModel.getQuads(state, side, rand, extraData));
        if (side == null) return quads;

        MachineConfigType[] types = extraData.getData(ModelDataTypes.MACHINE_CONFIG_PROPERTY);
        if (types != null) {
            MachineConfigType type = types[side.ordinal()];
            quads.addAll(createConfigQuads(side, type, rand));
        }

        return quads;
    }

    public List<BakedQuad> createConfigQuads(Direction direction, MachineConfigType type, Random random) {
        return switch (type) {
            case NONE -> new ArrayList<>();
            case INPUT -> INPUT_MODEL.getQuads(null, direction, random, EmptyModelData.INSTANCE);
            case EXTRACT -> OUTPUT_MODEL.getQuads(null, direction, random, EmptyModelData.INSTANCE);
            case INPUT_EXTRACT -> INPUT_OUTPUT_MODEL.getQuads(null, direction, random, EmptyModelData.INSTANCE);
        };
    }

    @Override
    public boolean useAmbientOcclusion() {
        return originModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return originModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return originModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return originModel.isCustomRenderer();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return originModel.getParticleIcon();
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return originModel.getOverrides();
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return originModel.getTransforms();
    }
}
