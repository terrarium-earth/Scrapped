package dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.enchantment.AutoEnchanterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AutoEnchanterBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int ESSENCE_COST = 200;
    private static final Random RANDOM = new Random();

    private int enchantLevel = 1;

    //Rendering book stuff
    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    public float rot;
    public float oRot;
    public float tRot;

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
    protected void tick() {
        super.tick();
        if (level == null) return;

        oOpen = open;
        oRot = rot;
        Player player = level.getNearestPlayer((double) getBlockPos().getX() + 0.5D, (double) getBlockPos().getY() + 0.5D, (double) getBlockPos().getZ() + 0.5D, 3.0D, false);
        if (player != null) {
            double d0 = player.getX() - ((double) getBlockPos().getX() + 0.5D);
            double d1 = player.getZ() - ((double) getBlockPos().getZ() + 0.5D);
            tRot = (float) Mth.atan2(d1, d0);
            open += 0.1F;
            if (open < 0.5F || RANDOM.nextInt(40) == 0) {
                float f1 = flipT;

                do {
                    flipT += (float) (RANDOM.nextInt(4) - RANDOM.nextInt(4));
                } while (f1 == flipT);
            }
        } else {
            tRot += 0.02F;
            open -= 0.1F;
        }

        while (rot >= (float) Math.PI) {
            rot -= ((float) Math.PI * 2F);
        }

        while (rot < -(float) Math.PI) {
            rot += ((float) Math.PI * 2F);
        }

        while (tRot >= (float) Math.PI) {
            tRot -= ((float) Math.PI * 2F);
        }

        while (tRot < -(float) Math.PI) {
            tRot += ((float) Math.PI * 2F);
        }

        float f2;
        for (f2 = tRot - rot; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        rot += f2 * 0.4F;
        open = Mth.clamp(open, 0.0F, 1.0F);
        ++time;
        oFlip = flip;
        float f = (flipT - flip) * 0.4F;
        float f3 = 0.2F;
        f = Mth.clamp(f, -0.2F, 0.2F);
        flipA += (f - flipA) * 0.9F;
        flip += flipA;
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

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AutoEnchanterContainer(id, inventory, this);
    }
}
