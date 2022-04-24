package dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs.GrinderContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrinderBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int ESSENCE_AMOUNT = 25;

    public GrinderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRINDER.get(), pos, state);
        this.createEnergy(32000, 3200);
        this.createFluid(10000, new FluidStack(ModBlocks.ESSENCE.get(), 8000));
        this.setMaxWorkTime(4);
        this.setMaxIdleTime(80);

        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setVerticalRange(3);
        this.getMachineArea().setUpgradeRadius(1);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ENTITY_PREDICATE);
        if (!entities.isEmpty()) {
            ServerLevel serverLevel = (ServerLevel) level;
            LivingEntity entity = (LivingEntity) entities.get(0);

            getTank().fill(new FluidStack(ModBlocks.ESSENCE.get(), ESSENCE_AMOUNT), IFluidHandler.FluidAction.EXECUTE);
            entity.hurt(NO_DROPS, 100);
            dropItems(serverLevel, entity);
            useEnergy();
            return true;
        }

        return false;
    }

    private void dropItems(ServerLevel serverLevel, LivingEntity entity) {
        ResourceLocation id = entity.getLootTable();
        LootContext.Builder builder = new LootContext.Builder(serverLevel)
                .withRandom(serverLevel.getRandom())
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.MAGIC)
                .withOptionalParameter(LootContextParams.KILLER_ENTITY, null)
                .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, null);

        LootTable lootTable = serverLevel.getServer().getLootTables().get(id);
        LootContext ctx = builder.create(LootContextParamSets.ENTITY);
        insertOrDropItems(lootTable.getRandomItems(ctx));
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.grinder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new GrinderContainer(id, inventory, this);
    }
}
