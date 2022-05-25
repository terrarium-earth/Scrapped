package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.blockentity.container.generator.CulinaryGenContainer;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CulinaryGenBlockEntity extends GeneratorBlockEntity {

    private static final int BASE_ENERGY_GEN = 4;

    private int burnTime;
    private int maxBurnTime;

    public CulinaryGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CULINARY_GENERATOR.get(), pos, state, 100000, 4, 100);
        this.createInventory(new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                CulinaryGenBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.isEdible();
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
                generateEnergy();
        } else {
            burnTime = 0;
            maxBurnTime = 0;

            ItemStack stack = getInventory().getStackInSlot(0);
            if (canGenerate() && !stack.isEmpty()) {
                FoodProperties food = stack.getFoodProperties(null);

                if (food != null) {
                    setEnergyGen(food.getNutrition() * BASE_ENERGY_GEN);
                    burnTime = (int) (food.getSaturationModifier() / food.getNutrition() * (45 * 20));
                    maxBurnTime = burnTime;
                    stack.shrink(1);
                }
            }
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.culinary_generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new CulinaryGenContainer(id, inventory, this);
    }

    @Override
    public Component getDisplayText() {
        return new TranslatableComponent("tooltip.generator." + (burnTime > 0 && canGenerate() ? "generating" : "idle"), String.valueOf(getEnergyGen()));
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }
}
