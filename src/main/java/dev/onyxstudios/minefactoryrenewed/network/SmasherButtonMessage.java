package dev.onyxstudios.minefactoryrenewed.network;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.blocks.BlockSmasherBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SmasherButtonMessage(BlockPos machinePos,
                                   int fortune) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos());
        buffer.writeInt(fortune());
    }

    public static SmasherButtonMessage decode(FriendlyByteBuf buffer) {
        return new SmasherButtonMessage(buffer.readBlockPos(), buffer.readInt());
    }

    public static void handleMessage(SmasherButtonMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            if (ctx.getSender() == null) return;

            Level level = ctx.getSender().level;
            if (level.isLoaded(message.machinePos()) &&
                    level.getBlockEntity(message.machinePos()) instanceof BlockSmasherBlockEntity blockSmasher) {
                blockSmasher.setFortune(message.fortune());
            }
        });

        ctx.setPacketHandled(true);
    }
}
