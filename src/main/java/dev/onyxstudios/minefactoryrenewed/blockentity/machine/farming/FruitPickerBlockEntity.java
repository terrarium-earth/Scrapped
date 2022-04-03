package dev.onyxstudios.minefactoryrenewed.blockentity.machine.farming;

import dev.onyxstudios.minefactoryrenewed.api.item.Pickable;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.farming.FruitPickerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.data.PickableManager;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FruitPickerBlockEntity extends MachineBlockEntity implements MenuProvider {

    public FruitPickerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FRUIT_PICKER_BLOCK_ENTITY.get(), pos, state);
        this.createInventory();
        this.createEnergy(16000, 320);
        this.setMaxWorkTime(2);
        this.setMaxIdleTime(10);

        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setVerticalRange(2);
    }

    @Override
    public boolean run() {
        BlockPos pos = getMachineArea().getNextBlock();
        if (level == null || level.isEmptyBlock(pos))
            return false;

        ServerLevel serverLevel = (ServerLevel) level;
        BlockState state = level.getBlockState(pos);

        if (PickableManager.getInstance().isPickable(state.getBlock())) {
            if (state.getBlock() instanceof BonemealableBlock block &&
                    block.isValidBonemealTarget(level, pos, state, false)) return false;

            Pickable pickable = PickableManager.getInstance().getPickable(state.getBlock());
            BlockState newState = Blocks.AIR.defaultBlockState();

            if (!pickable.breakPlant()) {
                newState = state.getBlock().defaultBlockState();
                if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
                    newState = newState.setValue(HorizontalDirectionalBlock.FACING,
                            state.getValue(HorizontalDirectionalBlock.FACING));
                }
            }

            harvestFruit(serverLevel, state, pos);
            level.setBlockAndUpdate(pos, newState);
            useEnergy();
            return true;
        }

        return false;
    }

    private void harvestFruit(ServerLevel serverLevel, BlockState state, BlockPos pos) {
        LootContext.Builder builder = new LootContext.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.BLOCK_ENTITY, this)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, null)
                .withOptionalParameter(LootContextParams.TOOL, Items.AIR.getDefaultInstance());
        List<ItemStack> drops = state.getDrops(builder);

        insertOrDropItems(drops);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.fruit_picker");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FruitPickerContainer(id, inventory, this);
    }
}
