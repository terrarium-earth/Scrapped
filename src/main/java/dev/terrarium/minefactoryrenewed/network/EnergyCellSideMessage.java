package dev.terrarium.minefactoryrenewed.network;

import dev.terrarium.minefactoryrenewed.blockentity.power.EnergyCellBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record EnergyCellSideMessage(BlockPos machinePos, Direction direction) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos());
        buffer.writeEnum(direction);
    }

    public static EnergyCellSideMessage decode(FriendlyByteBuf buffer) {
        return new EnergyCellSideMessage(buffer.readBlockPos(), buffer.readEnum(Direction.class));
    }

    public static void handleMessage(EnergyCellSideMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getSender() == null) return;

            Level level = ctx.getSender().level;
            if (level.isLoaded(message.machinePos()) &&
                    level.getBlockEntity(message.machinePos()) instanceof EnergyCellBlockEntity energyCell) {
                energyCell.cycleConfig(message.direction());
            }
        });

        ctx.setPacketHandled(true);
    }
}
