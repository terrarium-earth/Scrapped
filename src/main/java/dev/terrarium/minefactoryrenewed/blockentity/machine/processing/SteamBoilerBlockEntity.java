package dev.terrarium.minefactoryrenewed.blockentity.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.processing.SteamBoilerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//Accepts water from top or bottom side, handles steam from horizontal sides
public class SteamBoilerBlockEntity extends MachineBlockEntity implements MenuProvider {

    public static final int MAX_TEMPERATURE = 750;

    //Produce 400mb of steam for every 100mb of water
    private static final int WATER_COST = 100;
    private static final int STEAM_OUTPUT = 400;

    private final FluidTank steamTank;
    private final LazyOptional<FluidTank> steamTankHandler;

    private int temperature;

    private int burnTime;
    private int maxBurnTime;

    private int tempCooldown;

    public SteamBoilerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEAM_BOILER.get(), pos, state);
        this.createFluid(32000, new FluidStack(Fluids.WATER.getSource(), 1000));
        this.createInventory(1, false);
        this.setMaxIdleTime(20);
        this.setMaxWorkTime(4);

        steamTank = new FluidTank(16000, new FluidStack(ModBlocks.STEAM.get(), 1000)::isFluidEqual);
        steamTankHandler = LazyOptional.of(() -> steamTank);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("steamFluid", steamTank.writeToNBT(new CompoundTag()));
        tag.putInt("temperature", temperature);
        tag.putInt("burnTime", burnTime);
        tag.putInt("maxBurnTime", maxBurnTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        steamTank.readFromNBT(tag.getCompound("steamFluid"));
        temperature = tag.getInt("temperature");
        burnTime = tag.getInt("burnTime");
        maxBurnTime = tag.getInt("maxBurnTime");
    }

    @Override
    public boolean run() {
        if (level == null || getTank().getFluidAmount() < WATER_COST || steamTank.getSpace() < STEAM_OUTPUT)
            return false;

        if (burnTime <= 0) {
            ItemStack fuel = getInventory().getStackInSlot(0);
            this.burnTime = ForgeHooks.getBurnTime(fuel, null);
            this.maxBurnTime = burnTime;
            if (burnTime <= 0) return false;

            getInventory().extractItem(0, 1, false);
        }

        if (temperature >= 100) {
            getSteamTank().fill(new FluidStack(ModBlocks.STEAM.get(), STEAM_OUTPUT), IFluidHandler.FluidAction.EXECUTE);
            getTank().drain(WATER_COST, IFluidHandler.FluidAction.EXECUTE);
            return true;
        }

        return false;
    }

    @Override
    protected void tick() {
        super.tick();
        tempCooldown++;
        if (tempCooldown >= 20) {
            if (burnTime > 0)
                temperature = Math.min(temperature + 1, MAX_TEMPERATURE);
            else if (temperature > 0)
                temperature = Math.max(0, temperature - 1);

            tempCooldown = 0;
        }

        if (burnTime > 0) {
            burnTime--;
        }

        transferFluid(100, getSteamTank());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return side == Direction.UP || side == Direction.DOWN ?
                    super.getCapability(cap, side) : steamTankHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public FluidTank getSteamTank() {
        return steamTank;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SteamBoilerContainer(id, inventory, this);
    }
}
