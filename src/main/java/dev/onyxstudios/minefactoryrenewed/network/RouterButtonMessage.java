package dev.onyxstudios.minefactoryrenewed.network;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.MobRouterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record RouterButtonMessage(BlockPos machinePos,
                                  boolean whitelist,
                                  boolean allowBabies) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos());
        buffer.writeBoolean(whitelist());
        buffer.writeBoolean(allowBabies());
    }

    public static RouterButtonMessage decode(FriendlyByteBuf buffer) {
        return new RouterButtonMessage(buffer.readBlockPos(), buffer.readBoolean(), buffer.readBoolean());
    }

    public static void handleMessage(RouterButtonMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            if (ctx.getSender() == null) return;

            Level level = ctx.getSender().level;
            if (level.isLoaded(message.machinePos()) &&
                    level.getBlockEntity(message.machinePos()) instanceof MobRouterBlockEntity mobRouter) {
                mobRouter.setWhitelist(message.whitelist());
                mobRouter.setAllowBabies(message.allowBabies());
            }
        });

        ctx.setPacketHandled(true);
    }
}
