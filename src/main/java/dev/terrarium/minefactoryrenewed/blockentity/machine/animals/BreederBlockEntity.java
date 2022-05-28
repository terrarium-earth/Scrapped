package dev.terrarium.minefactoryrenewed.blockentity.machine.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.animals.BreederContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BreederBlockEntity extends MachineBlockEntity implements MenuProvider {

    public BreederBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREEDER.get(), pos, state);
        this.createInventory(9, false);
        this.createEnergy(32000, 230);
        this.setMaxWorkTime(5);
        this.setMaxIdleTime(100);

        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setUpgradeRadius(1);
        this.getMachineArea().setVerticalRange(1);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ENTITY_PREDICATE);

        for (Entity entity : entities) {
            if (!(entity instanceof Animal animal) || !animal.canFallInLove() || animal.getAge() != 0) continue;

            for (int i = 0; i < getInventory().getSlots(); i++) {
                ItemStack stack = getInventory().getStackInSlot(i);
                if (animal.isFood(stack)) {
                    getInventory().extractItem(i, 1, false);
                    animal.setInLove(null);
                    useEnergy();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.breeder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BreederContainer(id, inventory, this);
    }
}
