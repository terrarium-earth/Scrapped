package dev.onyxstudios.minefactoryrenewed.api.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MachineArea {

    private final List<BlockPos> area = new ArrayList<>();
    private BlockPos originOffset = new BlockPos(0, 0, 0);
    private AABB aabb;

    private final BlockPos origin;
    private Direction facing;

    private final int radius;
    private int verticalRange;
    private int upgradeRadius;
    private int currentBlock = 0;

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

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("facing", facing.ordinal());
        tag.putInt("verticalRadius", verticalRange);
        tag.putInt("upgradeRadius", upgradeRadius);

        return tag;
    }

    public void load(CompoundTag tag) {
        Direction direction = Direction.values()[tag.getInt("facing")];
        if (direction != facing) {
            facing = direction;
            calculateArea();
        }

        verticalRange = tag.getInt("verticalRange");
        upgradeRadius = tag.getInt("upgradeRadius");
    }

    public void calculateArea() {
        area.clear();
        currentBlock = 0;

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
            //Allow for proper radius in front
            secondCorner = frontPos.relative(right, finalRadius).relative(facing, (radius + 1) + (upgradeRadius * 2)).above(verticalRange);
        }

        BlockPos.betweenClosedStream(firstCorner, secondCorner).map(BlockPos::immutable).collect(Collectors.toCollection(() -> area));
        aabb = new AABB(firstCorner, secondCorner).inflate(1);
    }

    public void setOriginOffset(int x, int y, int z) {
        this.setOriginOffset(new BlockPos(x, y, z));
        this.calculateArea();
    }

    public void setOriginOffset(BlockPos originOffset) {
        this.originOffset = originOffset;
    }

    public void setUpgradeRadius(int upgradeRadius) {
        this.upgradeRadius = upgradeRadius;
        this.calculateArea();
    }

    public void setVerticalRange(int verticalRange) {
        this.verticalRange = verticalRange;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
        this.calculateArea();
    }

    public BlockPos getNextBlock() {
        BlockPos pos = area.get(currentBlock);
        currentBlock++;
        if (currentBlock >= area.size())
            currentBlock = 0;

        return pos;
    }

    public List<BlockPos> getArea() {
        return area;
    }

    public AABB getAabb() {
        return aabb;
    }

    public int getRadius() {
        return radius + upgradeRadius;
    }
}
