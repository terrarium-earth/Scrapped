package dev.onyxstudios.minefactoryrenewed.blockentity.machine.processing;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.processing.LaserDrillContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.item.FocusItem;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModTags;
import dev.onyxstudios.minefactoryrenewed.util.RandomMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LaserDrillBlockEntity extends MachineBlockEntity implements MenuProvider {

    private final RandomMap<Item> drops = new RandomMap<>();
    private boolean validBedrock = false;
    private BlockPos bedrockPos;
    private int checkTime = 0;

    private boolean updateDrops = false;

    public LaserDrillBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LASER_DRILL.get(), pos, state);
        this.createInventory(new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                LaserDrillBlockEntity.this.updateDrops = true;
                LaserDrillBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof FocusItem;
            }
        });
        this.createEnergy(1000000, 5000);
        this.setMaxIdleTime(20);
        this.setMaxWorkTime(200);
        checkForBedrock();
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("validBedrock", validBedrock);
        if (bedrockPos != null)
            tag.put("bedrockPos", NbtUtils.writeBlockPos(bedrockPos));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        validBedrock = tag.getBoolean("validBedrock");
        if (tag.contains("bedrockPos"))
            bedrockPos = NbtUtils.readBlockPos(tag.getCompound("bedrockPos"));
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        if (drops.isEmpty() || updateDrops) createDrops();

        Item drop = drops.randomEntry();
        if (drop != null) {
            insertOrDropItems(List.of(new ItemStack(drop)));
            useEnergy();
            return true;
        }

        return false;
    }

    @Override
    protected void tick() {
        super.tick();
        //Check valid bedrock every 5 secs
        checkTime++;
        if (checkTime > (5 * 20)) {
            checkForBedrock();
            checkTime = 0;
        }
    }

    @Override
    protected void tickWork() {
        super.tickWork();
        useEnergy(50);
    }

    private void createDrops() {
        drops.clear();
        Registry.ITEM.getTagOrEmpty(ModTags.LASER_ORE)
                .forEach(itemHolder -> drops.add(10, itemHolder.value()));

        for (int i = 0; i < getInventory().getSlots(); i++) {
            ItemStack stack = getInventory().getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof FocusItem focus) {
                Registry.ITEM.getTagOrEmpty(focus.getTag())
                        .forEach(itemHolder -> drops.add(45, itemHolder.value()));
            }
        }

        updateDrops = false;
    }

    private void checkForBedrock() {
        if (level == null) return;

        for (int y = getBlockPos().getY() - 1; y > level.getMinBuildHeight(); y--) {
            BlockPos pos = new BlockPos(getBlockPos().getX(), y, getBlockPos().getZ());
            BlockState state = level.getBlockState(pos);

            if (!state.isAir()) {
                setMaxWorkTime(200);
                setEnergyCost(5000);

                if (state.is(Blocks.BEDROCK)) {
                    validBedrock = true;
                    bedrockPos = pos;

                    int height = getBlockPos().getY() - pos.getY();
                    if (height <= 18) {
                        setMaxWorkTime(Math.max(0, 200 - ((16 - height) * 12)));
                        setEnergyCost(5000 + (16 - height) * 500);
                    }
                } else {
                    validBedrock = false;
                    bedrockPos = null;
                }

                setChanged();
                break;
            }
        }
    }

    public boolean isValidBedrock() {
        return validBedrock && bedrockPos != null;
    }

    public BlockPos getBedrockPos() {
        return bedrockPos;
    }

    @Override
    public AABB getRenderBoundingBox() {
        if (isValidBedrock()) {
            return new AABB(getBlockPos(), bedrockPos);
        }

        return super.getRenderBoundingBox();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        //If energy, return an empty optional
        //We don't want other mods to be able to transfer energy, only prechargers
        if (cap == CapabilityEnergy.ENERGY)
            return LazyOptional.empty();

        return super.getCapability(cap, side);
    }

    @Override
    public boolean hasCustomRenderer() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.laser_drill");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new LaserDrillContainer(id, inventory, this);
    }
}
