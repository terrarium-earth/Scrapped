package dev.onyxstudios.minefactoryrenewed.network;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs.AutoSpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SpawnerButtonMessage(BlockPos machinePos,
                                   boolean spawnExact) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos());
        buffer.writeBoolean(spawnExact());
    }

    public static SpawnerButtonMessage decode(FriendlyByteBuf buffer) {
        return new SpawnerButtonMessage(buffer.readBlockPos(), buffer.readBoolean());
    }

    public static void handleMessage(SpawnerButtonMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            if (ctx.getSender() == null) return;

            Level level = ctx.getSender().level;
            if (level.isLoaded(message.machinePos()) &&
                    level.getBlockEntity(message.machinePos()) instanceof AutoSpawnerBlockEntity autoSpawner) {
                autoSpawner.setSpawnExact(message.spawnExact());
            }
        });

        ctx.setPacketHandled(true);
    }
}
