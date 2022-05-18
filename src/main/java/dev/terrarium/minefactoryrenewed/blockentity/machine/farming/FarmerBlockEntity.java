package dev.terrarium.minefactoryrenewed.blockentity.machine.farming;

import dev.terrarium.minefactoryrenewed.blockentity.container.farming.FarmerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FarmerBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int SLUDGE_AMOUNT = 50;

    private boolean shearLeaves = false;
    private boolean smallShrooms = false;
    private boolean jungleWood = false;

    private final List<BlockPos> currentTreeParts = new ArrayList<>();
    private int partIndex = 0;

    public FarmerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FARMER.get(), pos, state);
        this.createInventory();
        this.createEnergy(16000, 240);
        this.createFluid(FluidAttributes.BUCKET_VOLUME * 10, new FluidStack(ModBlocks.SLUDGE.get(), 1000));
        this.setMaxWorkTime(2);
        this.setMaxIdleTime(5);

        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setVerticalRange(3);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("shearLeaves", shearLeaves);
        tag.putBoolean("smallShrooms", smallShrooms);
        tag.putBoolean("jungleWood", jungleWood);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        shearLeaves = tag.getBoolean("shearLeaves");
        smallShrooms = tag.getBoolean("smallShrooms");
        jungleWood = tag.getBoolean("jungleWood");
    }

    @Override
    public boolean run() {
        BlockPos workPos;
        boolean fromTree = false;

        if (currentTreeParts.size() > 0) {
            if (partIndex < currentTreeParts.size() - 1) {
                workPos = currentTreeParts.get(partIndex);
                partIndex++;
                fromTree = true;
            } else {
                currentTreeParts.clear();
                partIndex = 0;
                workPos = getMachineArea().getNextBlock();
            }
        } else {
            workPos = getMachineArea().getNextBlock();
        }

        if (level == null || level.isEmptyBlock(workPos))
            return workPos.getY() > getBlockPos().getY();

        ServerLevel serverLevel = (ServerLevel) level;
        BlockState state = level.getBlockState(workPos);
        if (state.getBlock() instanceof SaplingBlock)
            return false;

        boolean run = false;
        boolean dropItems = true;

        if (state.is(BlockTags.LOGS)) {
            if (state.is(Blocks.JUNGLE_LOG) && !jungleWood) return false;
            if (!fromTree) {
                currentTreeParts.clear();
                findTreeParts(serverLevel, workPos, null);
                currentTreeParts.sort(Comparator.comparingInt(Vec3i::getY));
            }

            run = true;
        } else if (((state.getBlock() instanceof MushroomBlock || state.getBlock() instanceof FungusBlock)
                && smallShrooms) || state.getBlock() instanceof HugeMushroomBlock) {
            run = true;
        } else if (state.getBlock() instanceof IForgeShearable) {
            dropItems = shearLeaves;
            if (!fromTree) {
                currentTreeParts.clear();
                findTreeParts(serverLevel, workPos, null);
                currentTreeParts.sort(Comparator.comparingInt(Vec3i::getY));
            }

            run = true;
        } else if (state.getBlock() instanceof IPlantable) {
            if (state.is(Blocks.CACTUS) || state.is(Blocks.SUGAR_CANE)) {
                //TODO: Possibly handle cactus and sugar cane like logs?
                if (canRemoveTallPlant(level, state, workPos)) {
                    run = true;
                }
            } else if (state.getBlock() instanceof CropBlock crop) {
                if (crop.isMaxAge(state)) {
                    run = true;
                }
            }
        }

        if (run) {
            harvestBlock(serverLevel, state, workPos, dropItems);
            return true;
        }

        return false;
    }

    private boolean canRemoveTallPlant(Level level, BlockState state, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());
        return (above.isAir() || !above.is(state.getBlock())) && !below.isAir() && below.is(state.getBlock());
    }

    private void findTreeParts(ServerLevel serverLevel, BlockPos pos, Direction parent) {
        currentTreeParts.add(pos);

        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.relative(direction);
            BlockState state = serverLevel.getBlockState(neighbor);
            if (direction == parent || currentTreeParts.contains(neighbor)) continue;

            if (state.is(BlockTags.LOGS) || state.getBlock() instanceof IForgeShearable) {
                findTreeParts(serverLevel, neighbor, direction.getOpposite());
            }
        }
    }

    private void harvestBlock(ServerLevel serverLevel, BlockState state, BlockPos pos, boolean dropItems) {
        LootContext.Builder builder = new LootContext.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.BLOCK_ENTITY, this)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, null)
                .withOptionalParameter(LootContextParams.TOOL, Items.SHEARS.getDefaultInstance());
        List<ItemStack> drops = state.getDrops(builder);
        serverLevel.destroyBlock(pos, false);

        getTank().fill(new FluidStack(ModBlocks.SLUDGE.get(), SLUDGE_AMOUNT), IFluidHandler.FluidAction.EXECUTE);
        if (dropItems)
            insertOrDropItems(drops);
    }

    @Override
    public boolean canRun() {
        return super.canRun() && getTank().getSpace() >= SLUDGE_AMOUNT;
    }

    public void setShearLeaves(boolean shearLeaves) {
        this.shearLeaves = shearLeaves;
    }

    public void setHarvestSmallShrooms(boolean smallShrooms) {
        this.smallShrooms = smallShrooms;
    }

    public void setHarvestJungleWood(boolean jungleWood) {
        this.jungleWood = jungleWood;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.farmer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FarmerContainer(id, inventory, this);
    }

    public boolean shearLeaves() {
        return shearLeaves;
    }

    public boolean harvestSmallShrooms() {
        return smallShrooms;
    }

    public boolean harvestJungleWood() {
        return jungleWood;
    }
}
