package dev.terrarium.minefactoryrenewed.api.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.EnergyStorage;

public class MFREnergyStorage extends EnergyStorage {

    private boolean isInfinite = false;

    public MFREnergyStorage(int capacity) {
        super(capacity);
    }

    public MFREnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public MFREnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public MFREnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return super.receiveEnergy(maxReceive, simulate);
    }

    public int receiveInternal(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, maxReceive);
        if (!simulate)
            energy += energyReceived;

        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (isInfinite) {
            return maxExtract;
        }

        return super.extractEnergy(maxExtract, simulate);
    }

    public void setInfinite(boolean infinite) {
        this.isInfinite = infinite;
        this.energy = capacity;
    }

    public int getMaxExtract() {
        return this.maxExtract;
    }

    public int getMaxReceive() {
        return this.maxReceive;
    }

    public boolean isInfinite() {
        return isInfinite;
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("capacity", this.capacity);
        tag.putInt("energy", this.energy);
        tag.putInt("maxExtract", this.maxExtract);
        tag.putInt("maxReceive", this.maxReceive);
        tag.putBoolean("isInfinite", isInfinite);

        return tag;
    }

    @Override
    public void deserializeNBT(Tag tag) {
        CompoundTag compoundTag = (CompoundTag) tag;
        this.capacity = compoundTag.getInt("capacity");
        this.energy = compoundTag.getInt("energy");
        this.maxExtract = compoundTag.getInt("maxExtract");
        this.maxReceive = compoundTag.getInt("maxReceive");
        this.isInfinite = compoundTag.getBoolean("isInfinite");
    }
}
