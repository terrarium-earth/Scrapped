package dev.terrarium.minefactoryrenewed.blockentity.machine.mobs;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.mobs.MobRouterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.item.SafariNetItem;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MobRouterBlockEntity extends MachineBlockEntity implements MenuProvider {

    private LivingEntity filterEntity;
    private EntityType<?> entityType;
    private CompoundTag entityTag;

    private boolean whitelist = true;
    private boolean allowBabies = false;

    public MobRouterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOB_ROUTER.get(), pos, state);
        this.createInventory(new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                MobRouterBlockEntity.this.updateSafariNet();
                MobRouterBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof SafariNetItem;
            }
        });

        this.createEnergy(16000, 800);
        this.setMaxWorkTime(10);
        this.setMaxIdleTime(20);
        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setVerticalRange(1);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("whitelist", whitelist);
        tag.putBoolean("allowBabies", allowBabies);

        if (entityType != null && entityType.getRegistryName() != null) {
            tag.putString("entityType", entityType.getRegistryName().toString());

            if (entityTag != null)
                tag.put("entityTag", entityTag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        whitelist = tag.getBoolean("whitelist");
        allowBabies = tag.getBoolean("allowBabies");

        if (tag.contains("entityType")) {
            ResourceLocation id = new ResourceLocation(tag.getString("entityType"));
            this.entityType = ForgeRegistries.ENTITIES.getValue(id);
            if (tag.contains("entityTag"))
                this.entityTag = tag.getCompound("entityTag");
        }
    }

    @Override
    public boolean run() {
        if (level == null ||
                (filterEntity == null && entityType != null && !createEntity(entityType, entityTag))) return false;
        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ALL_ENTITY_PREDICATE);
        if (!entities.isEmpty()) {
            Direction direction = getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            BlockPos destination = getBlockPos().relative(direction.getOpposite());

            for (Entity entity : entities) {
                if (entity instanceof Player) continue;
                LivingEntity livingEntity = (LivingEntity) entity;
                if (filterEntity == null) {
                    if (!livingEntity.isBaby() || allowBabies) {
                        entity.setPos(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5);
                        return true;
                    }

                    return false;
                }

                boolean canTransport = ((entity.getType() == filterEntity.getType()) == whitelist) &&
                        (!livingEntity.isBaby() || allowBabies);

                if (canTransport) {
                    entity.setPos(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5);
                    useEnergy();
                    return true;
                }
            }
        }

        return false;
    }

    private boolean createEntity(EntityType<?> entityType, CompoundTag tag) {
        if (level != null && entityType != null) {
            filterEntity = (LivingEntity) entityType.create(level);

            if (filterEntity != null && tag != null)
                filterEntity.load(tag);

            return true;
        }

        return false;
    }

    private void updateSafariNet() {
        ItemStack stack = getInventory().getStackInSlot(0);
        filterEntity = null;
        entityType = null;
        entityTag = null;

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
                    this.filterEntity = entity;
                    this.entityTag = entityTag;
                    this.entityType = entityType;
                }
            }
        }

        this.setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public boolean whitelist() {
        return whitelist;
    }

    public boolean allowBabies() {
        return allowBabies;
    }

    public void setWhitelist(boolean whitelist) {
        this.whitelist = whitelist;
    }

    public void setAllowBabies(boolean allowBabies) {
        this.allowBabies = allowBabies;
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MobRouterContainer(id, inventory, this);
    }
}
