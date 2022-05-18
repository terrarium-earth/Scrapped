package dev.terrarium.minefactoryrenewed.api.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public interface IRotatableMachine extends IWrenchableMachine {

    void onMachineRotated(Level level, BlockPos pos, Direction rotatedTo);
}
