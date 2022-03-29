package dev.onyxstudios.minefactoryrenewed.api.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IWrenchableMachine {

    void onWrenched(Level level, Player player, BlockPos pos);
}
