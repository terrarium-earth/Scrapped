package dev.terrarium.minefactoryrenewed.network;

import dev.terrarium.minefactoryrenewed.blockentity.power.EnergyCellBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record EnergyCellPowerMessage(BlockPos machinePos, int maxReceive, int maxExtract) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos());
        buffer.writeInt(maxReceive());
        buffer.writeInt(maxExtract());
    }

    public static EnergyCellPowerMessage decode(FriendlyByteBuf buffer) {
        return new EnergyCellPowerMessage(buffer.readBlockPos(), buffer.readInt(), buffer.readInt());
    }

    public static void handleMessage(EnergyCellPowerMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getSender() == null) return;

            Level level = ctx.getSender().level;
            if (level.isLoaded(message.machinePos()) &&
                    level.getBlockEntity(message.machinePos()) instanceof EnergyCellBlockEntity energyCell) {
                energyCell.setMaxReceive(message.maxReceive());
                energyCell.setMaxExtract(message.maxExtract());
            }
        });

        ctx.setPacketHandled(true);
    }
}
