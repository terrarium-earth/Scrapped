package dev.onyxstudios.minefactoryrenewed.blockentity.machine.animals;

import dev.onyxstudios.minefactoryrenewed.api.item.Ranchable;
import dev.onyxstudios.minefactoryrenewed.blockentity.container.animals.RancherContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.data.RanchManager;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RancherBlockEntity extends MachineBlockEntity implements MenuProvider {

    public RancherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RANCHER.get(), pos, state);
        this.createInventory(9, false);
        this.createEnergy(32000, 230);
        this.setMaxWorkTime(8);
        this.setMaxIdleTime(80);

        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setUpgradeRadius(1);
        this.getMachineArea().setVerticalRange(1);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ENTITY_PREDICATE);

        for (Entity entity : entities) {
            LivingEntity livingEntity = (LivingEntity) entity;
            Ranchable ranchable = RanchManager.getInstance().getRanchable(livingEntity);
            if (ranchable == null || !ranchable.entityPredicate().test(livingEntity)) return false;

            if (ranchable.tool() != null) {
                int toolSlot = findToolSlot(ranchable.tool());
                if (toolSlot < 0) continue;

                if (ranchable.consumeTool())
                    getInventory().extractItem(toolSlot, 1, false);
            }

            ItemStack result = ranchable.result(livingEntity);
            ranchable.consumer().accept(livingEntity);
            insertOrDropItems(List.of(result));
            useEnergy();
            return true;
        }

        return false;
    }

    private int findToolSlot(Item tool) {
        if (tool == null) return -1;

        for (int i = 0; i < getInventory().getSlots(); i++) {
            ItemStack stack = getInventory().getStackInSlot(i);

            if (!stack.isEmpty() && stack.is(tool))
                return i;
        }

        return -1;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.rancher");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RancherContainer(id, inventory, this);
    }
}
