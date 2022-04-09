package dev.onyxstudios.minefactoryrenewed.blockentity.machine.mobs;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.mobs.AutoSpawnerContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.item.SafariNetItem;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoSpawnerBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int ESSENCE_COST = 50;
    private static final int SPAWN_RANGE = 4;

    private EntityType<?> entityType;
    private LivingEntity spawnEntity;
    private CompoundTag exactTag;
    private boolean spawnExact = false;

    public AutoSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_SPAWNER_BLOCK_ENTITY.get(), pos, state);
        this.createInventory(new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                AutoSpawnerBlockEntity.this.updateSafariNet();
                AutoSpawnerBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof SafariNetItem;
            }
        });

        this.createFluid(10000, new FluidStack(ModBlocks.ESSENCE.get(), 1000));
        this.createEnergy(32000, 1200);
        this.setMaxIdleTime(140);
        this.setMaxWorkTime(25);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("spawnExact", spawnExact);

        if (entityType != null && entityType.getRegistryName() != null) {
            tag.putString("entityType", entityType.getRegistryName().toString());

            if (exactTag != null)
                tag.put("exactTag", exactTag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if (tag.contains("entityType")) {
            ResourceLocation id = new ResourceLocation(tag.getString("entityType"));
            this.entityType = ForgeRegistries.ENTITIES.getValue(id);
            if (tag.contains("exactTag"))
                this.exactTag = tag.getCompound("exactTag");
        }
    }

    @Override
    public boolean run() {
        if (level == null || getTank().getFluidAmount() < ESSENCE_COST ||
                (spawnEntity == null && !createEntity(entityType, exactTag))) return false;

        int cost = getSpawnCost();
        if (hasEnoughPower(cost)) {
            double x = getBlockPos().getX() + (level.getRandom().nextDouble() - level.getRandom().nextDouble()) * SPAWN_RANGE;
            double y = getBlockPos().getY() + level.getRandom().nextInt(3) - 1;
            double z = getBlockPos().getZ() + (level.getRandom().nextDouble() - level.getRandom().nextDouble()) * SPAWN_RANGE;
            //Keep running until a valid spawn pos is found
            if (!level.noCollision(entityType.getAABB(x, y, z))) return true;

            Entity entity = entityType.create(level);
            if (entity != null) {
                if (spawnExact && exactTag != null) {
                    entity.load(exactTag);
                }

                entity.moveTo(x, y, z);
                level.addFreshEntity(entity);
                getTank().drain(ESSENCE_COST, IFluidHandler.FluidAction.EXECUTE);
                useEnergy(cost);
                return true;
            }
        }

        return false;
    }

    private int getSpawnCost() {
        int cost = getEnergyCost();
        if (spawnExact && spawnEntity != null) {
            //400 FE extra for every armor piece
            for (ItemStack armorSlot : spawnEntity.getArmorSlots()) {
                if (!armorSlot.isEmpty())
                    cost += 400;
            }

            //200 FE extra for every held item
            for (ItemStack handSlot : spawnEntity.getHandSlots()) {
                if (!handSlot.isEmpty())
                    cost += 200;
            }
        }

        return cost;
    }

    private boolean createEntity(EntityType<?> entityType, CompoundTag tag) {
        if (level != null && entityType != null) {
            spawnEntity = (LivingEntity) entityType.create(level);

            if (spawnEntity != null && tag != null)
                spawnEntity.load(tag);

            return true;
        }

        return false;
    }

    private void updateSafariNet() {
        ItemStack stack = getInventory().getStackInSlot(0);
        spawnEntity = null;
        entityType = null;
        exactTag = null;

        if (stack.isEmpty() || level == null)
            return;

        if (SafariNetItem.hasEntity(stack)) {
            CompoundTag tag = stack.getOrCreateTag();

            ResourceLocation entityTypeId = new ResourceLocation(tag.getString(SafariNetItem.ENTITY_ID_KEY));
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(entityTypeId);
            CompoundTag entityTag = tag.getCompound(SafariNetItem.ENTITY_KEY);

            if (entityType != null) {
                LivingEntity entity = (LivingEntity) entityType.create(level);

                if (entity != null) {
                    entity.load(entityTag);

                    this.entityType = entityType;
                    this.exactTag = entityTag;
                    this.exactTag.remove("UUID");
                    this.spawnEntity = entity;
                }
            }
        }

        this.setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public boolean spawnExact() {
        return spawnExact;
    }

    public void setSpawnExact(boolean spawnExact) {
        this.spawnExact = spawnExact;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.auto_spawner");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AutoSpawnerContainer(id, inventory, this);
    }
}
