package dev.terrarium.minefactoryrenewed.api.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MachineArea {

    private final List<BlockPos> area = new ArrayList<>();
    private BlockPos originOffset = new BlockPos(0, 0, 0);
    private AABB aabb;

    private BlockPos firstCorner = new BlockPos(0, 0, 0);
    private BlockPos secondCorner = new BlockPos(0, 0, 0);

    private final BlockPos origin;
    private Direction facing;

    private final int radius;
    private int verticalRange;
    private int upgradeRadius;
    private int currentBlock = 0;

    private boolean isOneBlock = false;

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
        tag.putInt("verticalRange", verticalRange);
        tag.putInt("upgradeRadius", upgradeRadius);
        tag.put("firstCorner", NbtUtils.writeBlockPos(firstCorner));
        tag.put("secondCorner", NbtUtils.writeBlockPos(secondCorner));

        tag.put("aabbMin", NbtUtils.writeBlockPos(new BlockPos(aabb.minX, aabb.minY, aabb.minZ)));
        tag.put("aabbMax", NbtUtils.writeBlockPos(new BlockPos(aabb.maxX, aabb.maxX, aabb.maxX)));

        return tag;
    }

    public void load(CompoundTag tag) {
        Direction direction = Direction.values()[tag.getInt("facing")];
        verticalRange = tag.getInt("verticalRange");
        upgradeRadius = tag.getInt("upgradeRadius");
        firstCorner = NbtUtils.readBlockPos(tag.getCompound("firstCorner"));
        secondCorner = NbtUtils.readBlockPos(tag.getCompound("secondCorner"));

        BlockPos aabbMin = NbtUtils.readBlockPos(tag.getCompound("aabbMin"));
        BlockPos aabbMax = NbtUtils.readBlockPos(tag.getCompound("aabbMax"));
        aabb = new AABB(aabbMin, aabbMax);

        if (direction != facing) {
            facing = direction;
            calculateArea();
        }
    }

    public void calculateArea() {
        area.clear();
        currentBlock = 0;

        int finalRadius = radius + upgradeRadius;
        BlockPos offset = origin.offset(originOffset);

        BlockPos first;
        BlockPos second;

        if (facing == Direction.UP || facing == Direction.DOWN) {
            first = new BlockPos(offset.getX() - finalRadius, offset.getY(), offset.getZ() - finalRadius);
            second = new BlockPos(offset.getX() + finalRadius, offset.getY() + verticalRange, offset.getZ() + finalRadius);
            secondCorner = second.offset(1, 1, 1);
        } else {
            BlockPos frontPos = offset.relative(facing);
            Direction left = facing.getCounterClockWise();
            Direction right = facing.getClockWise();

            first = frontPos.relative(left, finalRadius);
            //Allow for proper radius in front
            second = isOneBlock ? frontPos :
                    frontPos.relative(right, finalRadius).relative(facing, (radius + 1) + (upgradeRadius * 2)).above(verticalRange);
            secondCorner = second.relative(right).relative(facing).above(1);
        }

        firstCorner = first;
        aabb = new AABB(first, second).inflate(1);
        BlockPos.betweenClosedStream(first, second).map(BlockPos::immutable).collect(Collectors.toCollection(() -> area));
    }

    public void setOriginOffset(int x, int y, int z) {
        this.setOriginOffset(new BlockPos(x, y, z));
    }

    public void setOriginOffset(BlockPos originOffset) {
        this.originOffset = originOffset;
        this.calculateArea();
    }

    public void setUpgradeRadius(int upgradeRadius) {
        this.upgradeRadius = upgradeRadius;
        this.calculateArea();
    }

    public void setVerticalRange(int verticalRange) {
        this.verticalRange = verticalRange;
        this.calculateArea();
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

    public void setOneBlock(boolean oneBlock) {
        this.isOneBlock = oneBlock;
    }

    public List<BlockPos> getArea() {
        return area;
    }

    public AABB getAabb() {
        return aabb;
    }

    public BlockPos getFirstCorner() {
        return firstCorner;
    }

    public BlockPos getSecondCorner() {
        return secondCorner;
    }

    public int getRadius() {
        return radius + upgradeRadius;
    }

    public boolean isOneBlock() {
        return isOneBlock;
    }
}
