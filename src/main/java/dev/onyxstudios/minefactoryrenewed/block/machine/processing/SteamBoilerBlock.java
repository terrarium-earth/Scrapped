package dev.onyxstudios.minefactoryrenewed.block.machine.processing;

import dev.onyxstudios.minefactoryrenewed.block.machine.MachineBlock;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.processing.SteamBoilerBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SteamBoilerBlock extends MachineBlock {

    public SteamBoilerBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        LazyOptional<IFluidHandlerItem> handler = FluidUtil.getFluidHandler(stack);
        Optional<IFluidHandlerItem> optionalHandler = handler.resolve();
        if (optionalHandler.isPresent() && level.getBlockEntity(pos) instanceof SteamBoilerBlockEntity steamBoiler) {
            IFluidHandlerItem fluidHandlerItem = optionalHandler.get();
            FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
            if (FluidUtil.interactWithFluidHandler(player, hand, fluidStack.isEmpty() ?
                    steamBoiler.getSteamTank() : steamBoiler.getTank())) {
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public BlockEntityType<? extends MachineBlockEntity> getBlockEntityType() {
        return ModBlockEntities.STEAM_BOILER.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SteamBoilerBlockEntity(pos, state);
    }
}
