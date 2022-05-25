package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.blockentity.container.generator.PotionGenContainer;
import dev.terrarium.minefactoryrenewed.client.gui.generator.PotionGenScreen;
import dev.terrarium.minefactoryrenewed.data.PinkManager;
import dev.terrarium.minefactoryrenewed.data.PotionManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PotionGenBlockEntity extends GeneratorBlockEntity {

    private int burnTime;
    private int maxBurnTime;

    public PotionGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POTION_GENERATOR.get(), pos, state, 100000, 40, 100);
        this.createInventory(new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                PotionGenBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return PotionManager.getInstance().isPotionBurnable(stack);
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
        if (burnTime > 0) {
            burnTime--;

            if (canGenerate())
                generateEnergy(getEnergyGen());
        } else {
            burnTime = 0;
            maxBurnTime = 0;

            ItemStack stack = getInventory().getStackInSlot(0);
            if (!stack.isEmpty() && PotionManager.getInstance().isPotionBurnable(stack)) {
                setEnergyGen(PotionManager.getInstance().getEnergyGen(stack));
                burnTime = PotionManager.getInstance().getBurnTime(stack);
                maxBurnTime = burnTime;
                stack.shrink(1);
            }
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.potion_generator");
    }

    @Override
    public Component getDisplayText() {
        return new TranslatableComponent("tooltip.generator." + (burnTime > 0 ? "generating" : "idle"), String.valueOf(getEnergyGen()));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new PotionGenContainer(id, inventory, this);
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }
}
