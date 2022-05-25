package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LunarGenBlockEntity extends GeneratorBlockEntity {

    public LunarGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LUNAR_GENERATOR.get(), pos, state, 100000, 4, 20);
    }

    @Override
    protected void tick() {
        super.tick();
        if(level == null) return;
        if(level.isNight() && level.canSeeSky(getBlockPos())) {
            this.generateEnergy();
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.solar_generator");
    }

    @Override
    public Component getDisplayText() {
        return level != null && level.isNight() && level.canSeeSky(getBlockPos()) && canGenerate() ?
                new TranslatableComponent("tooltip.generator.generating", String.valueOf(getEnergyGen())) :
                new TranslatableComponent("tooltip.generator.idle");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return null;
    }
}