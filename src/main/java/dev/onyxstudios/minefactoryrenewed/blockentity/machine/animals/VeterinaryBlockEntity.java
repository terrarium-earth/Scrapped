package dev.onyxstudios.minefactoryrenewed.blockentity.machine.animals;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.animals.VeterinaryContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.item.syringe.SyringeItem;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VeterinaryBlockEntity extends MachineBlockEntity implements MenuProvider {

    public VeterinaryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VETERINARY.get(), pos, state);
        this.createInventory(9, false);
        this.createInventory(new ItemStackHandler(9) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                VeterinaryBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return !stack.isEmpty() &&
                        (stack.getItem() instanceof SyringeItem || stack.is(ModItems.EMPTY_SYRINGE.get()));
            }
        });
        this.createEnergy(32000, 320);
        this.setMaxWorkTime(8);
        this.setMaxIdleTime(180);

        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setVerticalRange(1);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ALL_ENTITY_PREDICATE);

        if (!entities.isEmpty()) {
            for (int i = 0; i < getInventory().getSlots(); i++) {
                ItemStack stack = getInventory().getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() instanceof SyringeItem syringe) {

                    for (Entity entity : entities) {
                        if (!(entity instanceof LivingEntity livingEntity) || entity instanceof Player) continue;

                        if (syringe.canInject(livingEntity)) {
                            syringe.inject(level, livingEntity);
                            getInventory().setStackInSlot(i, new ItemStack(ModItems.EMPTY_SYRINGE.get()));
                            useEnergy();
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.veterinary");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new VeterinaryContainer(id, inventory, this);
    }
}
