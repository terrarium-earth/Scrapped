package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergyBlockEntity extends GeneratorBlockEntity {

    public CreativeEnergyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CREATIVE_ENERGY.get(), pos, state, Integer.MAX_VALUE, 1000, 1000000);
        this.getEnergy().setInfinite(true);
    }

    @Override
    protected void tick() {
        super.tick();
        if(canGenerate()) generateEnergy();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("tooltip." + MinefactoryRenewed.MODID + ".generator.creative_energy");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return null;
    }

    @Override
    public boolean hasMenu() {
        return false;
    }

    @Override
    public Component getDisplayText() {
        return canGenerate() ?
                new TranslatableComponent("tooltip.generator." + MinefactoryRenewed.MODID + ".generating", String.valueOf(getEnergyGen())) :
                new TranslatableComponent("tooltip.generator." + MinefactoryRenewed.MODID + ".idle");
    }
}
