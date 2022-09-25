package earth.terrarium.minefactoryrenewed.common.block;

import earth.terrarium.botarium.api.energy.BlockEnergyContainer;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.UpdatingEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BaseMachineBlockEntity extends BlockEntity implements EnergyBlock {
    private UpdatingEnergyContainer energyContainer;
    public BaseMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public int getMaxEnergy() {
        return 100000;
    }

    @Override
    public UpdatingEnergyContainer getEnergyStorage() {
        if (energyContainer == null) {
            this.energyContainer = new BlockEnergyContainer(this, getMaxEnergy());
        }
        return energyContainer;
    }
}
