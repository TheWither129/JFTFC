package net.witheraway.tfcjutefurniture.block.furniture;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.witheraway.tfcjutefurniture.block.properties.JFBlockStateProperties;

import javax.annotation.Nullable;

public class Curtain extends FurnitureBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public DirectionProperty CURTAIN_VERTICAL_PARTS = JFBlockStateProperties.CURTAIN_VERTICAL_PARTS_UP_DOWN;

    protected static final VoxelShape NORTH = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
    protected static final VoxelShape WEST = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public Curtain(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(CURTAIN_VERTICAL_PARTS, Direction.UP)
        );
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(CURTAIN_VERTICAL_PARTS) == Direction.DOWN) return Shapes.empty();
        return switch (state.getValue(FACING)) {
            default -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
        };
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.getBlockState(pos.below()).canBeReplaced(context) || level.isOutsideBuildHeight(pos.below())) return null;

        Direction facing = context.getClickedFace();

        BlockPos clickedPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockPos clickedFacingPos = clickedPos.relative(clickedFace);
        BlockState clickedFacingState = level.getBlockState(clickedFacingPos);

        if (clickedFacingState.getBlock() instanceof Curtain) {
            Direction clickedFacingFace = clickedFacingState.getValue(FACING);
            if (clickedFacingFace != clickedFace && clickedFacingFace.getOpposite() != clickedFace) facing = clickedFacingFace;
        }
        if (facing.getAxis().isVertical()) facing = context.getHorizontalDirection().getOpposite();

        return this.defaultBlockState().setValue(FACING, facing);
    }
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide) return;

        BlockPos blockPos = pos.below();

        level.setBlock(blockPos, state.setValue(CURTAIN_VERTICAL_PARTS, Direction.DOWN), 3);
        level.blockUpdated(pos, Blocks.AIR);
        state.updateNeighbourShapes(level, pos, 3);
    }
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        Direction facing = state.getValue(FACING);
        Direction facing_vertical = state.getValue(CURTAIN_VERTICAL_PARTS);
        if (direction.getAxis().isVertical()) {
            BlockState stateOpposite = level.getBlockState(currentPos.relative(facing_vertical.getOpposite()));
            if (!(stateOpposite.getBlock() instanceof Curtain) || stateOpposite.getValue(CURTAIN_VERTICAL_PARTS) == facing_vertical) return Blocks.AIR.defaultBlockState();
        }
        if (direction != facing.getClockWise() && direction != facing.getCounterClockWise()) return state;

        return state.setValue(CURTAIN_VERTICAL_PARTS, facing_vertical);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CURTAIN_VERTICAL_PARTS);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
