package dev.terrarium.minefactoryrenewed.api.energy;

import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class StrictEnergyStorage implements IEnergyStorage {

    private final EnergyStorage reference;
    private int maxReceive;
    private int maxExtract;

    public StrictEnergyStorage(EnergyStorage reference, int maxReceive, int maxExtract) {
        this.reference = reference;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        return reference.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        return reference.extractEnergy(maxExtract, simulate);
    }

    public void setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
    }

    public void setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
    }

    @Override
    public int getEnergyStored() {
        return this.reference.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return this.reference.getMaxEnergyStored();
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }
}
