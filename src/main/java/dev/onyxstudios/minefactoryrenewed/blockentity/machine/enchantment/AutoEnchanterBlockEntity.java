package dev.onyxstudios.minefactoryrenewed.blockentity.machine.enchantment;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.enchantment.AutoEnchanterContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class AutoEnchanterBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int ESSENCE_COST = 200;
    private int enchantLevel = 1;

    public AutoEnchanterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_ENCHANTER.get(), pos, state);
        this.createInventory(2, false);
        this.createEnergy(16000, 160);
        this.createFluid(4000, new FluidStack(ModBlocks.ESSENCE.get(), 1000));
        this.setMaxIdleTime(20);
        this.setMaxWorkTime(100);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("enchantLevel", enchantLevel);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        enchantLevel = tag.getInt("enchantLevel");
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        ItemStack toolSlot = getInventory().getStackInSlot(0);
        if (toolSlot.is(Items.GLASS_BOTTLE)) {
            getInventory().extractItem(0, 1, false);
            getInventory().insertItem(1, new ItemStack(Items.EXPERIENCE_BOTTLE), false);

            getTank().drain(ESSENCE_COST, IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
            return true;
        } else if (toolSlot.is(Items.BOOK)) {
            getInventory().setStackInSlot(1, EnchantmentHelper.enchantItem(level.random, toolSlot, enchantLevel, false));
            getInventory().extractItem(0, 1, false);

            getTank().drain(ESSENCE_COST, IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
            return true;
        } else if (toolSlot.isEnchantable()) {
            EnchantmentHelper.enchantItem(level.random, toolSlot, enchantLevel, false);
            getInventory().setStackInSlot(1, toolSlot.copy());
            getInventory().extractItem(0, 1, false);

            getTank().drain(ESSENCE_COST, IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
            return true;
        }

        return false;
    }

    @Override
    public boolean canRun() {
        return getTank().getFluidAmount() >= ESSENCE_COST &&
                getInventory().getStackInSlot(1).isEmpty() &&
                super.canRun();
    }

    public void increaseLevel() {
        setEnchantLevel(Math.min(30, enchantLevel + 1));
    }

    public void decreaseLevel() {
        setEnchantLevel(Math.max(1, enchantLevel - 1));
    }

    public void setEnchantLevel(int enchantLevel) {
        this.enchantLevel = enchantLevel;
        setMaxWorkTime(enchantLevel * 10);
        setChanged();
    }

    public int getEnchantLevel() {
        return enchantLevel;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.auto_enchanter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AutoEnchanterContainer(id, inventory, this);
    }
}
