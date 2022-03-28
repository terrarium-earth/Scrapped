package dev.onyxstudios.minefactoryrenewed.api.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MachineArea {

    private final List<BlockPos> area = new ArrayList<>();
    private BlockPos originOffset = new BlockPos(0, 0, 0);

    private final BlockPos origin;
    private Direction facing;

    private final int radius;
    private final int verticalRange;
    private int upgradeRadius;

    public MachineArea(BlockPos pos, Direction facing, int radius) {
        this(pos, facing, radius, 0);
    }

    public MachineArea(BlockPos pos, Direction facing, int radius, int verticalRange) {
        this(pos, facing, radius, verticalRange, 0);
    }

    public MachineArea(BlockPos pos, Direction facing, int radius, int verticalRange, int upgradeRadius) {
        this.origin = pos;
        this.facing = facing;
        this.radius = radius;
        this.verticalRange = verticalRange;
        this.upgradeRadius = upgradeRadius;
    }

    public void calculateArea() {
        area.clear();

        int finalRadius = radius + upgradeRadius;
        BlockPos offset = origin.offset(originOffset);
        BlockPos firstCorner;
        BlockPos secondCorner;

        if (facing == Direction.UP || facing == Direction.DOWN) {
            firstCorner = new BlockPos(offset.getX() - finalRadius, offset.getY(), offset.getZ() - finalRadius);
            secondCorner = new BlockPos(offset.getX() + finalRadius, offset.getY() + verticalRange, offset.getZ() + finalRadius);
        } else {
            BlockPos frontPos = offset.relative(facing);
            Direction left = facing.getCounterClockWise();
            Direction right = facing.getClockWise();

            firstCorner = frontPos.relative(left, finalRadius);
            secondCorner = frontPos.relative(right, finalRadius).relative(facing, finalRadius).above(verticalRange);
        }

        BlockPos.betweenClosedStream(firstCorner, secondCorner).map(BlockPos::immutable).collect(Collectors.toCollection(() -> area));
    }

    public void setOriginOffset(int x, int y, int z) {
        this.setOriginOffset(new BlockPos(x, y, z));
    }

    public void setOriginOffset(BlockPos originOffset) {
        this.originOffset = originOffset;
    }

    public void setUpgradeRadius(int upgradeRadius) {
        this.upgradeRadius = upgradeRadius;
        this.calculateArea();
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
        this.calculateArea();
    }

    public List<BlockPos> getArea() {
        return area;
    }
}
