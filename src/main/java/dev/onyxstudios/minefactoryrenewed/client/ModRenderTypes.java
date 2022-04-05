package dev.onyxstudios.minefactoryrenewed.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class ModRenderTypes extends RenderType {

    private static final ShaderStateShard RAINBOW_SHADER_STATE = new ShaderStateShard(() -> ModClient.RAINBOW_SHADER);
    public static final Function<ResourceLocation, RenderType> RAINBOW = Util.memoize(ModRenderTypes::rainbow);

    public ModRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode,
                          int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload,
                          Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    private static RenderType rainbow(ResourceLocation texture) {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RAINBOW_SHADER_STATE)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderType.NO_CULL)
                .setLightmapState(RenderType.LIGHTMAP)
                .setOverlayState(RenderType.OVERLAY)
                .createCompositeState(false);

        return create("mfr_position_color",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                true,
                true,
                compositeState
        );
    }
}
