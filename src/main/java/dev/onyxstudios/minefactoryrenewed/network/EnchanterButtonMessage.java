package dev.onyxstudios.minefactoryrenewed.network;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.enchantment.AutoEnchanterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record EnchanterButtonMessage(BlockPos machinePos,
                                     int enchantLevel) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos());
        buffer.writeInt(enchantLevel());
    }

    public static EnchanterButtonMessage decode(FriendlyByteBuf buffer) {
        return new EnchanterButtonMessage(buffer.readBlockPos(), buffer.readInt());
    }

    public static void handleMessage(EnchanterButtonMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            if (ctx.getSender() == null) return;

            Level level = ctx.getSender().level;
            if (level.isLoaded(message.machinePos()) &&
                    level.getBlockEntity(message.machinePos()) instanceof AutoEnchanterBlockEntity autoEnchanter) {
                autoEnchanter.setEnchantLevel(message.enchantLevel());
            }
        });

        ctx.setPacketHandled(true);
    }
}
