package dev.terrarium.minefactoryrenewed.block.power;

import dev.terrarium.minefactoryrenewed.api.machine.IWrenchableMachine;
import dev.terrarium.minefactoryrenewed.blockentity.power.EnergyCellBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

public class EnergyCellBlock extends BaseEntityBlock implements IWrenchableMachine {

    public static final VoxelShape BASIC_SHAPE = Stream.of(
            Block.box(0, 11, 0, 5, 16, 5),
            Block.box(0, 11, 11, 5, 16, 16),
            Block.box(11, 11, 11, 16, 16, 16),
            Block.box(11, 11, 0, 16, 16, 5),
            Block.box(11, 0, 0, 16, 5, 5),
            Block.box(0, 0, 0, 5, 5, 5),
            Block.box(0, 0, 11, 5, 5, 16),
            Block.box(11, 0, 11, 16, 5, 16),
            Block.box(5, 11, 1, 11, 15, 5),
            Block.box(5, 1, 11, 11, 5, 15),
            Block.box(5, 11, 11, 11, 15, 15),
            Block.box(5, 1, 1, 11, 5, 5),
            Block.box(1, 5, 11, 5, 11, 15),
            Block.box(1, 5, 1, 5, 11, 5),
            Block.box(11, 5, 11, 15, 11, 15),
            Block.box(11, 5, 1, 15, 11, 5),
            Block.box(1, 11, 5, 5, 15, 11),
            Block.box(2, 2, 2, 14, 14, 14),
            Block.box(11, 11, 5, 15, 15, 11),
            Block.box(11, 1, 5, 15, 5, 11),
            Block.box(1, 1, 5, 5, 5, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape ADVANCED_SHAPE = Stream.of(
            Block.box(0, 11, 0, 5, 16, 5),
            Block.box(0, 11, 11, 5, 16, 16),
            Block.box(11, 11, 11, 16, 16, 16),
            Block.box(11, 11, 0, 16, 16, 5),
            Block.box(-0.01, 6, 0.01, 4.99, 10, 5.01),
            Block.box(-0.01, 6, 10.99, 4.99, 10, 15.99),
            Block.box(11.01, 6, 10.99, 16.01, 10, 15.99),
            Block.box(11.01, 6, 0.01, 16.01, 10, 5.01),
            Block.box(-0.01, 10.99, 6, 4.99, 15.99, 10),
            Block.box(-0.01, 0.01, 6, 4.99, 5.01, 10),
            Block.box(11.01, 10.99, 6, 16.01, 15.99, 10),
            Block.box(11.01, 0.01, 6, 16.01, 5.01, 10),
            Block.box(6, 10.99, 10.99, 10, 15.99, 15.99),
            Block.box(6, 10.99, 0.01, 10, 15.99, 5.01),
            Block.box(6, 0.01, 10.99, 10, 5.01, 15.99),
            Block.box(6, 0.01, 0.01, 10, 5.01, 5.01),
            Block.box(11, 0, 0, 16, 5, 5),
            Block.box(0, 0, 0, 5, 5, 5),
            Block.box(0, 0, 11, 5, 5, 16),
            Block.box(11, 0, 11, 16, 5, 16),
            Block.box(5, 11, 1, 11, 15, 5),
            Block.box(5, 1, 11, 11, 5, 15),
            Block.box(5, 11, 11, 11, 15, 15),
            Block.box(5, 1, 1, 11, 5, 5),
            Block.box(1, 5, 11, 5, 11, 15),
            Block.box(1, 5, 1, 5, 11, 5),
            Block.box(11, 5, 11, 15, 11, 15),
            Block.box(11, 5, 1, 15, 11, 5),
            Block.box(1, 11, 5, 5, 15, 11),
            Block.box(1.25, 2.25, 1.25, 14.75, 14.75, 14.75),
            Block.box(11, 11, 5, 15, 15, 11),
            Block.box(11, 1, 5, 15, 5, 11),
            Block.box(1, 1, 5, 5, 5, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape ELITE_SHAPE = Stream.of(
            Block.box(0, 11, 0, 5, 16, 5),
            Block.box(0, 11, 11, 5, 16, 16),
            Block.box(11, 11, 11, 16, 16, 16),
            Block.box(11, 11, 0, 16, 16, 5),
            Block.box(-0.01, 5, 0.01, 4.99, 11, 5.01),
            Block.box(-0.01, 5, 10.99, 4.99, 11, 15.99),
            Block.box(11.01, 5, 10.99, 16.01, 11, 15.99),
            Block.box(11.01, 5, 0.01, 16.01, 11, 5.01),
            Block.box(5, -0.01, 0.01, 11, 4.99, 5.01),
            Block.box(5, 11, 0.01, 11, 16, 5.01),
            Block.box(5, -0.01, 10.99, 11, 4.99, 15.99),
            Block.box(5, 11, 10.99, 11, 16, 15.99),
            Block.box(-0.01, 11, 5, 4.99, 16, 11),
            Block.box(-0.01, 0.01, 5, 4.99, 5.01, 11),
            Block.box(11.01, 10.99, 5, 16.01, 15.99, 11),
            Block.box(11.01, 0.01, 5, 16.01, 5.01, 11),
            Block.box(11, 0, 0, 16, 5, 5),
            Block.box(0, 0, 0, 5, 5, 5),
            Block.box(0, 0, 11, 5, 5, 16),
            Block.box(11, 0, 11, 16, 5, 16),
            Block.box(5, 11, 1, 11, 15, 5),
            Block.box(5, 1, 11, 11, 5, 15),
            Block.box(5, 11, 11, 11, 15, 15),
            Block.box(5, 1, 1, 11, 5, 5),
            Block.box(1, 5, 11, 5, 11, 15),
            Block.box(1, 5, 1, 5, 11, 5),
            Block.box(11, 5, 11, 15, 11, 15),
            Block.box(11, 5, 1, 15, 11, 5),
            Block.box(1.25, 2.25, 1.25, 14.75, 14.75, 14.75),
            Block.box(11, 1, 5, 15, 5, 11),
            Block.box(1, 1, 5, 5, 5, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private final int capacity;
    private final int maxTransfer;
    private final VoxelShape shape;

    public EnergyCellBlock(int capacity, int maxTransfer, VoxelShape shape) {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).noOcclusion().requiresCorrectToolForDrops());
        this.capacity = capacity;
        this.maxTransfer = maxTransfer;
        this.shape = shape;
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (player.getMainHandItem().getItem() == ModItems.WRENCH.get())
            return super.use(state, level, pos, player, hand, hit);

        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof EnergyCellBlockEntity energyCell) {
            NetworkHooks.openGui((ServerPlayer) player, energyCell, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void onWrenched(Level level, Player player, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof EnergyCellBlockEntity energyCell) {
            ItemStack drop = new ItemStack(this);
            CompoundTag dropTag = new CompoundTag();
            CompoundTag blockEntityTag = new CompoundTag();

            blockEntityTag.put("energy", energyCell.getEnergyStorage().serializeNBT());
            dropTag.put("BlockEntityTag", blockEntityTag);
            drop.setTag(dropTag);

            level.destroyBlock(pos, false, player);
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), drop);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (this.getRegistryName() != null) {
            String tooltipText = I18n.get("tooltip.machine." + this.getRegistryName().getPath());
            String[] lines = tooltipText.split("<br>");
            for (String line : lines) {
                Component text = new TextComponent(line);
                tooltip.add(text);
            }
        }

        CompoundTag tag = stack.getOrCreateTagElement("BlockEntityTag");
        if (tag.contains("energy")) {
            CompoundTag energyTag = tag.getCompound("energy");
            int energy = energyTag.getInt("energy");
            int capacity = energyTag.getInt("capacity");

            String percent = DecimalFormat.getPercentInstance().format(energy / (float) capacity);
            tooltip.add(new TextComponent(percent + " Full").withStyle(ChatFormatting.AQUA));
        }
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape;
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new EnergyCellBlockEntity(pos, state, this);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ENERGY_CELL.get(), EnergyCellBlockEntity::tick);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxTransfer() {
        return maxTransfer;
    }
}
