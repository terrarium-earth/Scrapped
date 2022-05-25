package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.blockentity.container.generator.ExplosionGenContainer;
import dev.terrarium.minefactoryrenewed.data.generator.ExplosiveManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExplosionGenBlockEntity extends GeneratorBlockEntity {

    private int burnTime;
    private int maxBurnTime;
    private int cooldown;

    public ExplosionGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXPLOSION_GENERATOR.get(), pos, state, 256000, 0, 100);
        this.createInventory(new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                ExplosionGenBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return ExplosiveManager.getInstance().isExplosive(stack);
            }
        });
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("burnTime", burnTime);
        tag.putInt("maxBurnTime", maxBurnTime);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        burnTime = tag.getInt("burnTime");
        maxBurnTime = tag.getInt("maxBurnTime");
    }

    @Override
    protected void tick() {
        super.tick();
        if (level == null) return;

        if (burnTime > 0) {
            burnTime--;
            cooldown++;

            if (canGenerate())
                generateEnergy();

            if (cooldown >= 40) {
                this.level.explode(null, getBlockPos().getX(), getBlockPos().getY() + 0.0625f, getBlockPos().getZ(), 4.0F, Explosion.BlockInteraction.NONE);
                cooldown = 0;
            }
        } else {
            burnTime = 0;
            maxBurnTime = 0;

            ItemStack stack = getInventory().getStackInSlot(0);
            if (!stack.isEmpty() && ExplosiveManager.getInstance().isExplosive(stack)) {
                setEnergyGen(ExplosiveManager.getInstance().getEnergyGen(stack));
                burnTime = ExplosiveManager.getInstance().getBurnTime(stack);
                maxBurnTime = burnTime;
                stack.shrink(1);
            }
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.explosion_generator");
    }

    @Override
    public Component getDisplayText() {
        return new TranslatableComponent("tooltip.generator." + (burnTime > 0 && canGenerate() ? "generating" : "idle"), String.valueOf(getEnergyGen()));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new ExplosionGenContainer(id, inventory, this);
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }
}
