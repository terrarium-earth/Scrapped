package dev.terrarium.minefactoryrenewed.network;

import dev.terrarium.minefactoryrenewed.blockentity.machine.animals.ChronotyperBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ChronotyperButtonMessage(BlockPos machinePos,
                                       boolean movingBabies) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos());
        buffer.writeBoolean(movingBabies());
    }

    public static ChronotyperButtonMessage decode(FriendlyByteBuf buffer) {
        return new ChronotyperButtonMessage(buffer.readBlockPos(), buffer.readBoolean());
    }

    public static void handleMessage(ChronotyperButtonMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            if (ctx.getSender() == null) return;

            Level level = ctx.getSender().level;
            if (level.isLoaded(message.machinePos()) &&
                    level.getBlockEntity(message.machinePos()) instanceof ChronotyperBlockEntity chronotyper) {
                chronotyper.setMovingBabies(message.movingBabies());
            }
        });

        ctx.setPacketHandled(true);
    }
}
