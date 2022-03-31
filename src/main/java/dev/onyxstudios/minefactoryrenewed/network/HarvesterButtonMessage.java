package dev.onyxstudios.minefactoryrenewed.network;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming.FarmerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record HarvesterButtonMessage(BlockPos machinePos,
                                     boolean shearLeaves,
                                     boolean smallShrooms,
                                     boolean jungleWood) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos());
        buffer.writeBoolean(shearLeaves());
        buffer.writeBoolean(smallShrooms());
        buffer.writeBoolean(jungleWood());
    }

    public static HarvesterButtonMessage decode(FriendlyByteBuf buffer) {
        return new HarvesterButtonMessage(buffer.readBlockPos(), buffer.readBoolean(),
                buffer.readBoolean(), buffer.readBoolean());
    }

    public static void handleMessage(HarvesterButtonMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            if (ctx.getSender() == null) return;

            Level level = ctx.getSender().level;
            if (level.isLoaded(message.machinePos) &&
                    level.getBlockEntity(message.machinePos) instanceof FarmerBlockEntity farmer) {
                farmer.setShearLeaves(message.shearLeaves);
                farmer.setHarvestSmallShrooms(message.smallShrooms);
                farmer.setHarvestJungleWood(message.jungleWood);
            }
        });

        ctx.setPacketHandled(true);
    }
}
