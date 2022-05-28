package dev.terrarium.minefactoryrenewed.blockentity.machine.enchantment;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.enchantment.AutoAnvilContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AutoAnvilBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int ESSENCE_COST = 50;
    private AnvilResult result = null;

    public AutoAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_ANVIL.get(), pos, state);
        this.createInventory(new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                AutoAnvilBlockEntity.this.updateResult();
                AutoAnvilBlockEntity.this.setChanged();
            }
        });
        this.createEnergy(16000, 1600);
        this.createFluid(4000, new FluidStack(ModBlocks.ESSENCE.get(), 1000));
        this.setMaxIdleTime(20);
        this.setMaxWorkTime(300);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        updateResult();
    }

    @Override
    public boolean run() {
        if (level == null || !isValid() || result == null) return false;

        if (!result.isEmpty()) {
            ItemStack resultItem = result.resultItem();
            int itemCost = result.itemCost;

            getInventory().extractItem(0, 1, false);
            getInventory().extractItem(1, Math.max(itemCost, 1), false);
            getInventory().setStackInSlot(2, resultItem);

            getTank().drain(ESSENCE_COST, IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
            return true;
        }

        return false;
    }

    @Override
    protected void tickWork() {
        super.tickWork();
        if (result == null)
            updateResult();

        if (getWorkTime() % 10 == 0 && isValid()) {
            getTank().drain(ESSENCE_COST, IFluidHandler.FluidAction.EXECUTE);
            useEnergy(getEnergyCost() / 10);
        }
    }

    public void updateResult() {
        result = createResult();
    }

    //Copied from AnvilMenu and modified
    //Probably buggy, needs testing
    private AnvilResult createResult() {
        if (getInventory().getStackInSlot(0).isEmpty())
            return new AnvilResult(ItemStack.EMPTY);

        int i = 0;
        int itemRepairCost = 0;
        ItemStack itemstack = getInventory().getStackInSlot(0).copy();
        ItemStack itemstack2 = getInventory().getStackInSlot(1);

        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(itemstack);
        boolean hasBookEnchants = false;

        if (!itemstack2.isEmpty()) {
            hasBookEnchants = itemstack2.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(itemstack2).isEmpty();
            if (itemstack.isDamageableItem() && itemstack.getItem().isValidRepairItem(itemstack, itemstack2)) {
                int l2 = Math.min(itemstack.getDamageValue(), itemstack.getMaxDamage() / 4);
                if (l2 <= 0) return new AnvilResult(ItemStack.EMPTY);

                int i3;
                for (i3 = 0; l2 > 0 && i3 < itemstack2.getCount(); i3++) {
                    int j3 = itemstack.getDamageValue() - l2;
                    itemstack.setDamageValue(j3);
                    i++;
                    l2 = Math.min(itemstack.getDamageValue(), itemstack.getMaxDamage() / 4);
                }

                itemRepairCost = i3;
            } else {
                if (!hasBookEnchants && (!itemstack.is(itemstack2.getItem()) || !itemstack.isDamageableItem())) {
                    return new AnvilResult(ItemStack.EMPTY);
                }

                if (itemstack.isDamageableItem() && !hasBookEnchants) {
                    int durability = itemstack.getMaxDamage() - itemstack.getDamageValue();
                    int durability2 = itemstack2.getMaxDamage() - itemstack2.getDamageValue();
                    int j1 = durability2 + itemstack.getMaxDamage() * 12 / 100;
                    int k1 = durability + j1;
                    int l1 = Math.max(0, itemstack.getMaxDamage() - k1);

                    if (l1 < itemstack.getDamageValue()) {
                        itemstack.setDamageValue(l1);
                        i += 2;
                    }
                }

                Map<Enchantment, Integer> enchants2 = EnchantmentHelper.getEnchantments(itemstack2);
                boolean flag2 = false;
                boolean flag3 = false;

                for (Enchantment enchantment1 : enchants2.keySet()) {
                    if (enchantment1 != null) {
                        int enchantLevel = enchants.getOrDefault(enchantment1, 0);
                        int enchantLevel2 = enchants2.get(enchantment1);
                        enchantLevel2 = enchantLevel == enchantLevel2 ? enchantLevel2 + 1 : Math.max(enchantLevel2, enchantLevel);
                        boolean canEnchantItem = enchantment1.canEnchant(itemstack) || itemstack.is(Items.ENCHANTED_BOOK);
                        for (Enchantment enchantment : enchants.keySet()) {
                            if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
                                canEnchantItem = false;
                                i++;
                            }
                        }

                        if (!canEnchantItem) {
                            flag3 = true;
                        } else {
                            flag2 = true;
                            if (enchantLevel2 > enchantment1.getMaxLevel()) {
                                enchantLevel2 = enchantment1.getMaxLevel();
                            }

                            enchants.put(enchantment1, enchantLevel2);
                            int k3 = switch (enchantment1.getRarity()) {
                                case COMMON -> 1;
                                case UNCOMMON -> 2;
                                case RARE -> 4;
                                case VERY_RARE -> 8;
                            };

                            if (hasBookEnchants) {
                                k3 = Math.max(1, k3 / 2);
                            }

                            i += k3 * enchantLevel2;
                            if (itemstack.getCount() > 1) {
                                i = 40;
                            }
                        }
                    }
                }

                if (flag3 && !flag2) return new AnvilResult(ItemStack.EMPTY);
            }
        }

        if (i <= 0 || hasBookEnchants && !itemstack.isBookEnchantable(itemstack2))
            return new AnvilResult(ItemStack.EMPTY);

        if (!itemstack.isEmpty()) {
            int k2 = itemstack.getBaseRepairCost();
            if (!itemstack2.isEmpty() && k2 < itemstack2.getBaseRepairCost()) {
                k2 = itemstack2.getBaseRepairCost();
            }

            itemstack.setRepairCost(k2);
            EnchantmentHelper.setEnchantments(enchants, itemstack);
        }

        return new AnvilResult(itemstack, itemRepairCost);
    }

    private boolean isValid() {
        ItemStack slot0 = getInventory().getStackInSlot(0);
        ItemStack slot1 = getInventory().getStackInSlot(1);
        if (!getInventory().getStackInSlot(2).isEmpty() || result == null || result.isEmpty() ||
                (result.itemCost > 0 && slot1.getCount() < result.itemCost)) return false;

        return (slot0.isDamageableItem() && slot0.getItem().isValidRepairItem(slot0, slot1)) ||
                (slot1.is(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantments(slot1).isEmpty());
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AutoAnvilContainer(id, inventory, this);
    }

    private record AnvilResult(ItemStack resultItem, int itemCost) {

        public AnvilResult(ItemStack resultItem) {
            this(resultItem, 0);
        }

        public boolean isEmpty() {
            return resultItem.isEmpty();
        }
    }
}
