package dev.terrarium.minefactoryrenewed.mixin.client;

import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.client.blockentity.MachineAreaRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

    private static final MachineAreaRenderer machineRenderer = new MachineAreaRenderer();

    @Inject(method = "getRenderer", at = @At("HEAD"), cancellable = true)
    public <E extends BlockEntity> void getRenderer(E blockEntity, CallbackInfoReturnable<BlockEntityRenderer<E>> cir) {
        if (blockEntity instanceof MachineBlockEntity machine &&
                machine.getMachineArea() != null &&
                !machine.hasCustomRenderer())
            cir.setReturnValue((BlockEntityRenderer<E>) machineRenderer);
    }
}
