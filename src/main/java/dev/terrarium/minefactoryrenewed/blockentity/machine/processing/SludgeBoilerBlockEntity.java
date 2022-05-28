package dev.terrarium.minefactoryrenewed.blockentity.machine.processing;

import dev.terrarium.minefactoryrenewed.blockentity.container.processing.SludgeBoilerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import dev.terrarium.minefactoryrenewed.registry.ModTags;
import dev.terrarium.minefactoryrenewed.util.RandomMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class SludgeBoilerBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int SLUDGE_COST = 20;
    private final RandomMap<Item> drops = new RandomMap<>();

    public SludgeBoilerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SLUDGE_BOILER.get(), pos, state);
        this.createEnergy(16000, 80);
        this.createFluid(4000, new FluidStack(ModBlocks.SLUDGE.get(), 1000));
        this.setMaxWorkTime(160);
        this.setMaxIdleTime(2);

        this.createMachineArea(pos, Direction.UP);
        this.getMachineArea().setVerticalRange(2);
        this.getMachineArea().setUpgradeRadius(2);
        this.getMachineArea().setOriginOffset(0, -1, 0);

        Registry.ITEM.getTagOrEmpty(ModTags.SLUDGE_DROP).forEach(itemHolder -> drops.add(15, itemHolder.value()));
    }

    @Override
    public boolean run() {
        if (level == null || drops.isEmpty() || getTank().getFluidAmount() < SLUDGE_COST) return false;

        Item drop = drops.randomEntry();
        if (drop != null) {
            insertOrDropItems(List.of(new ItemStack(drop)));
            getTank().drain(SLUDGE_COST, IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
            return true;
        }

        return false;
    }

    @Override
    protected void tickWork() {
        super.tickWork();
        if (level == null) return;

        if (!level.isClientSide() && getWorkTime() % 5 == 0) {
            List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ALL_ENTITY_PREDICATE);
            if (!entities.isEmpty()) {
                for (Entity entity : entities) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    Map<MobEffect, MobEffectInstance> map = livingEntity.getActiveEffectsMap();
                    if (!map.containsKey(MobEffects.HUNGER))
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 220, 0));

                    if (!map.containsKey(MobEffects.POISON))
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 0));
                }
            }
        }
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SludgeBoilerContainer(id, inventory, this);
    }
}
