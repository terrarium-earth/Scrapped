package dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.enchantment.AutoDisenchanterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AutoDisenchanterBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int ESSENCE_COST = 50;

    public AutoDisenchanterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_DISENCHANTER.get(), pos, state);
        this.createInventory(3, false);
        this.createEnergy(16000, 320);
        this.createFluid(2000, new FluidStack(ModBlocks.ESSENCE.get(), 1000));
        this.setMaxIdleTime(20);
        this.setMaxWorkTime(300);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        if (hasCorrectItems()) {
            ItemStack toolStack = getInventory().getStackInSlot(1);
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(toolStack);

            if (!enchantments.isEmpty()) {
                ItemStack resultBook = new ItemStack(Items.ENCHANTED_BOOK);
                ItemStack resultTool = toolStack;
                if (toolStack.is(Items.ENCHANTED_BOOK))
                    resultTool = enchantments.size() > 1 ? new ItemStack(Items.ENCHANTED_BOOK) : new ItemStack(Items.BOOK);

                Enchantment enchantment = enchantments.keySet().stream().findFirst().get();
                EnchantedBookItem.addEnchantment(resultBook, new EnchantmentInstance(enchantment, enchantments.get(enchantment)));
                enchantments.remove(enchantment);

                EnchantmentHelper.setEnchantments(enchantments, resultTool);
                getInventory().extractItem(0, 1, false);
                getInventory().setStackInSlot(1, resultTool);
                getInventory().setStackInSlot(2, resultBook);

                getTank().drain(ESSENCE_COST, IFluidHandler.FluidAction.EXECUTE);
                useEnergy();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canRun() {
        return getInventory().getStackInSlot(2).isEmpty() &&
                getTank().getFluidAmount() >= ESSENCE_COST &&
                super.canRun();
    }

    private boolean hasCorrectItems() {
        ItemStack bookStack = getInventory().getStackInSlot(0);
        ItemStack toolStack = getInventory().getStackInSlot(1);
        ItemStack outputStack = getInventory().getStackInSlot(2);

        return !bookStack.isEmpty() && bookStack.is(Items.BOOK) && !toolStack.isEmpty() && outputStack.isEmpty();
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AutoDisenchanterContainer(id, inventory, this);
    }
}
