package dev.onyxstudios.minefactoryrenewed.blockentity.machine;

import dev.onyxstudios.minefactoryrenewed.api.energy.MFREnergyStorage;
import dev.onyxstudios.minefactoryrenewed.blockentity.InventoryBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.PlanterContainer;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlanterBlockEntity extends InventoryBlockEntity implements MenuProvider {

    private final MFREnergyStorage energy = new MFREnergyStorage(10000);
    private final LazyOptional<MFREnergyStorage> energyHandler = LazyOptional.of(() -> energy);

    public PlanterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLANTER_BLOCK_ENTITY.get(), pos, state, 26);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("energy", energy.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("energy"))
            energy.deserializeNBT(tag.getCompound("energy"));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PlanterBlockEntity blockEntity) {
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY)
            return energyHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.planter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new PlanterContainer(id, inventory, this);
    }
}
