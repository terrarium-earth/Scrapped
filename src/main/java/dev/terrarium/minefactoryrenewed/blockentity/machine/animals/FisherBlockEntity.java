package dev.terrarium.minefactoryrenewed.blockentity.machine.animals;

import dev.terrarium.minefactoryrenewed.blockentity.container.animals.FisherContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FisherBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final ItemStack FISHING_ROD = new ItemStack(Items.FISHING_ROD);

    public FisherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FISHER.get(), pos, state);
        this.createInventory(new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                FisherBlockEntity.this.updateFishingRod();
                FisherBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof FishingRodItem;
            }
        });
        this.createEnergy(16000, 40);
        this.setMaxWorkTime(45 * 20);
        this.setMaxIdleTime(40);

        this.createMachineArea(pos, Direction.UP);
        this.getMachineArea().setOriginOffset(0, -1, 0);
    }

    @Override
    public boolean run() {
        if (level == null) return false;

        if (hasWater()) {
            ServerLevel serverLevel = (ServerLevel) level;

            List<ItemStack> items = getFish(serverLevel);
            if (!items.isEmpty()) {
                insertOrDropItems(items);
                useEnergy();
                return true;
            }
        }

        return false;
    }

    private List<ItemStack> getFish(ServerLevel serverLevel) {
        LootTable lootTable = serverLevel.getServer().getLootTables().get(BuiltInLootTables.FISHING);
        ItemStack stack = getInventory().getStackInSlot(0);
        if (stack.isEmpty())
            stack = FISHING_ROD;

        int luck = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FISHING_LUCK, stack);
        LootContext context = new LootContext.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN,
                        new Vec3(getBlockPos().getX() + 0.5, getBlockPos().getY(), getBlockPos().getZ() + 0.5))
                .withParameter(LootContextParams.TOOL, FISHING_ROD)
                .withParameter(LootContextParams.BLOCK_ENTITY, this)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, null)
                .withOptionalParameter(LootContextParams.KILLER_ENTITY, null)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, null)
                .withRandom(serverLevel.random)
                .withLuck(luck)
                .create(LootContextParamSets.FISHING);

        return lootTable.getRandomItems(context);
    }

    private boolean hasWater() {
        if (level == null) return false;

        for (BlockPos pos : getMachineArea().getArea()) {
            if (!level.isWaterAt(pos)) return false;
        }

        return true;
    }

    private void updateFishingRod() {
        ItemStack stack = getInventory().getStackInSlot(0);
        if (!stack.isEmpty()) {
            int speed = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FISHING_SPEED, stack);

            this.setMaxWorkTime((45 * 20) / (speed + 1));
            this.setMaxIdleTime(40 / (speed + 1));
            return;
        }

        this.setMaxWorkTime(45 * 20);
        this.setMaxIdleTime(40);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.fisher");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FisherContainer(id, inventory, this);
    }
}
