package dev.onyxstudios.minefactoryrenewed.blockentity.machine.power;

import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeEnergyBlockEntity extends MachineBlockEntity {

    public CreativeEnergyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CREATIVE_ENERGY.get(), pos, state);
        this.createEnergy(Integer.MAX_VALUE, 0);
        this.getEnergy().setInfinite(true);
    }

    @Override
    public boolean run() {
        return false;
    }

    @Override
    protected void tick() {
        super.tick();
        transferEnergy(1000);
    }
}
