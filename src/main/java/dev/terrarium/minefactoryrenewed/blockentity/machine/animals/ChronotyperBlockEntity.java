package dev.terrarium.minefactoryrenewed.blockentity.machine.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.animals.ChronotyperContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChronotyperBlockEntity extends MachineBlockEntity implements MenuProvider {

    private boolean movingBabies = true;

    public ChronotyperBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHRONOTYPER.get(), pos, state);
        this.createEnergy(16000, 1280);
        this.setMaxWorkTime(4);
        this.setMaxIdleTime(140);
        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setUpgradeRadius(1);
        this.getMachineArea().setVerticalRange(1);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("movingBabies", movingBabies);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        movingBabies = tag.getBoolean("movingBabies");
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ANIMAL_PREDICATE);
        if (!entities.isEmpty()) {
            Direction direction = getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            BlockPos destination = getBlockPos().relative(direction.getOpposite());

            for (Entity entity : entities) {
                Animal animal = (Animal) entity;

                if (animal.isBaby() == movingBabies) {
                    entity.setPos(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5);
                    useEnergy();
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isMovingBabies() {
        return movingBabies;
    }

    public void setMovingBabies(boolean movingBabies) {
        this.movingBabies = movingBabies;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.chronotyper");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ChronotyperContainer(id, inventory, this);
    }
}
