package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.api.machine.IRotatableMachine;
import dev.onyxstudios.minefactoryrenewed.api.machine.IWrenchableMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WrenchItem extends BaseItem {

    public WrenchItem() {
        super(new Item.Properties()
                .tab(MinefactoryRenewed.TAB)
                .stacksTo(1)
                .fireResistant()
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (player == null || level.isClientSide()) return InteractionResult.PASS;

        if (player.isShiftKeyDown()) {
            if (state.getBlock() instanceof IWrenchableMachine machine)
                machine.onWrenched(level, player, pos);
        } else {
            if (state.getBlock() instanceof IRotatableMachine machine) {
                Direction direction = state.getValue(HorizontalDirectionalBlock.FACING).getClockWise();
                level.setBlockAndUpdate(pos, state.setValue(HorizontalDirectionalBlock.FACING, direction));
                machine.onMachineRotated(level, pos, direction);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
