package dev.terrarium.minefactoryrenewed.blockentity.machine.blocks;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.blocks.BlockSmasherContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockSmasherBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int ESSENCE_COST = 50;
    private static final int MAX_FORTUNE = 4;

    private int fortune = 0;

    public BlockSmasherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLOCK_SMASHER.get(), pos, state);
        this.createFluid(4000, new FluidStack(ModBlocks.ESSENCE.get(), 1000));
        this.createInventory(new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockSmasherBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return super.isItemValid(slot, stack);
            }
        });
        this.createEnergy(16000, 400);
        this.setMaxWorkTime(15);
        this.setMaxIdleTime(1);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("fortune", fortune);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fortune = tag.getInt("fortune");
    }

    @Override
    public boolean run() {
        if (level == null || (fortune > 0 && getTank().getFluidAmount() < fortune * ESSENCE_COST)) return false;
        ItemStack stack = getInventory().getStackInSlot(0);
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof BlockItem blockItem &&
                    blockItem.getBlock().builtInRegistryHolder().is(Tags.Blocks.ORES)) {
                BlockState defaultState = blockItem.getBlock().defaultBlockState();
                ServerLevel serverLevel = (ServerLevel) level;
                List<ItemStack> drops = defaultState.getDrops(createBuilder(serverLevel, defaultState));

                if (!drops.isEmpty()) {
                    getInventory().extractItem(0, 1, false);
                    ItemStack leftover = getInventory().insertItem(1, drops.get(0), false);
                    if (!leftover.isEmpty()) {
                        insertOrDropItems(List.of(leftover));
                    }

                    if (drops.size() > 1) {
                        insertOrDropItems(drops.subList(1, drops.size()));
                    }

                    if (fortune > 0)
                        getTank().drain(fortune * ESSENCE_COST, IFluidHandler.FluidAction.EXECUTE);
                    useEnergy();
                    return true;
                }
            } else {
                if (getInventory().getStackInSlot(1).isEmpty()) {
                    getInventory().setStackInSlot(1, stack);
                    getInventory().setStackInSlot(0, ItemStack.EMPTY);
                }
                return false;
            }
        }

        return false;
    }

    public LootContext.Builder createBuilder(ServerLevel serverLevel, BlockState state) {
        ItemStack pickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
        if (this.fortune > 0)
            pickaxe.enchant(Enchantments.BLOCK_FORTUNE, this.fortune);

        return new LootContext.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(getBlockPos()))
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.BLOCK_ENTITY, this)
                .withParameter(LootContextParams.TOOL, pickaxe);
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }
    public void addFortune() {
        setFortune(Math.min(fortune + 1, MAX_FORTUNE));
    }

    public void subtractFortune() {
        setFortune(Math.max(0, fortune - 1));
    }

    public void setFortune(int fortune) {
        this.fortune = fortune;
        this.setChanged();
    }

    public int getFortune() {
        return fortune;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BlockSmasherContainer(id, inventory, this);
    }
}
