package dev.onyxstudios.minefactoryrenewed.client;

import dev.onyxstudios.minefactoryrenewed.registry.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ModClient {

    public static void init() {
        EntityRenderers.register(ModEntities.SAFARI_NET.get(), ThrownItemRenderer::new);
    }
}
