package net.witheraway.tfcjutefurniture.block.furniture;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.witheraway.tfcjutefurniture.block.properties.CurtainHorizontalParts;
import net.witheraway.tfcjutefurniture.block.properties.JFBlockStateProperties;

import javax.annotation.Nullable;

public class Curtain extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<CurtainHorizontalParts> HORIZONTAL_PART = JFBlockStateProperties.CURTAIN_HORIZONTAL_PARTS;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final DirectionProperty VERTICAL_PART = JFBlockStateProperties.CURTAIN_VERTICAL_PARTS_UP_DOWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape NORTH = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 15.0D);
    protected static final VoxelShape EAST = Block.box(1.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 16.0D, 2.0D);
    protected static final VoxelShape WEST = Block.box(14.0D, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D);



    public Curtain(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HORIZONTAL_PART, CurtainHorizontalParts.SINGLE)
                .setValue(VERTICAL_PART, Direction.UP)
                .setValue(OPEN, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        CurtainHorizontalParts type = state.getValue(HORIZONTAL_PART);
        if (state.getValue(OPEN) && (type == CurtainHorizontalParts.SINGLE || type == CurtainHorizontalParts.MIDDLE)
                && state.getValue(VERTICAL_PART) == Direction.DOWN) return Shapes.empty();
        return switch (state.getValue(FACING)) {
            default -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(OPEN) ? Shapes.empty() : super.getCollisionShape(state, level, pos, context);
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


        BlockState stateL = level.getBlockState(pos.relative(facing.getClockWise()));
        BlockState stateR = level.getBlockState(pos.relative(facing.getCounterClockWise()));
        boolean sideL = (stateL.getBlock() instanceof Curtain && stateL.getValue(VERTICAL_PART) == Direction.UP && (stateL.getValue(FACING) == facing));
        boolean sideR = (stateR.getBlock() instanceof Curtain && stateR.getValue(VERTICAL_PART) == Direction.UP && (stateR.getValue(FACING) == facing));

        boolean open = sideL && sideR ? stateL.getValue(OPEN) || stateR.getValue(OPEN) : (sideR ? stateR.getValue(OPEN) : (sideL ? stateL.getValue(OPEN) : true));

        return this.defaultBlockState().setValue(FACING, facing).setValue(OPEN, open).setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide) return;

        BlockPos blockPos = pos.below();

        level.setBlock(blockPos, state.setValue(VERTICAL_PART, Direction.DOWN).setValue(WATERLOGGED, level.getFluidState(blockPos).getType() == Fluids.WATER), 3);
        level.blockUpdated(pos, Blocks.AIR);
        state.updateNeighbourShapes(level, pos, 3);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        Direction facing = state.getValue(FACING);
        Direction facing_vertical = state.getValue(VERTICAL_PART);
        if (direction.getAxis().isVertical()) {
            BlockState stateOpposite = level.getBlockState(currentPos.relative(facing_vertical.getOpposite()));
            if (!(stateOpposite.getBlock() instanceof Curtain) || stateOpposite.getValue(VERTICAL_PART) == facing_vertical) return Blocks.AIR.defaultBlockState();
        }
        if (direction != facing.getClockWise() && direction != facing.getCounterClockWise()) return state;

        BlockState stateL = level.getBlockState(currentPos.relative(facing.getClockWise()));
        BlockState stateR = level.getBlockState(currentPos.relative(facing.getCounterClockWise()));
        boolean sideL = (stateL.getBlock() instanceof Curtain && stateL.getValue(VERTICAL_PART) == facing_vertical && (stateL.getValue(FACING) == facing));
        boolean sideR = (stateR.getBlock() instanceof Curtain && stateR.getValue(VERTICAL_PART) == facing_vertical && (stateR.getValue(FACING) == facing));
        CurtainHorizontalParts type = sideL && sideR ? CurtainHorizontalParts.MIDDLE : (sideR ? CurtainHorizontalParts.LEFT : (sideL ? CurtainHorizontalParts.RIGHT : CurtainHorizontalParts.SINGLE));

        return state.setValue(HORIZONTAL_PART, type);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        if (state.getValue(VERTICAL_PART) == Direction.UP) toggleFromTop(state, level, pos);
        else toggleFromTop(level.getBlockState(pos.above()), level, pos.above());

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public void toggleFromTop(BlockState state, Level level, BlockPos pos) {
        boolean open = !state.getValue(OPEN);
        Direction facing = state.getValue(FACING);
        Direction relativeLeft = facing.getClockWise();
        Direction relativeRight = facing.getCounterClockWise();

        toggleVertical(state, level, pos, open);
        toggleHorizontal(state, level, pos, open, facing, relativeLeft);
        toggleHorizontal(state, level, pos, open, facing, relativeRight);
    }

    public void toggleHorizontal(BlockState state, Level level, BlockPos pos, boolean open, Direction facing, Direction dir) {
        BlockPos facingHorizontalPos = pos.relative(dir);
        BlockState facingHorizontalState = level.getBlockState(facingHorizontalPos);

        if (facingHorizontalState.getBlock() instanceof Curtain && facingHorizontalState.getValue(FACING) == facing &&
                state.getValue(VERTICAL_PART) == facingHorizontalState.getValue(VERTICAL_PART)) {
            toggleVertical(facingHorizontalState, level, facingHorizontalPos, open);
            toggleHorizontal(facingHorizontalState, level, facingHorizontalPos, open, facing, dir);
        }
    }

    public void toggleVertical(BlockState state, Level level, BlockPos pos, boolean open) {
        Direction facingVertical = state.getValue(VERTICAL_PART);
        BlockPos facingVerticalPos = pos.relative(facingVertical.getOpposite());
        BlockState facingVerticalState = level.getBlockState(facingVerticalPos);
        level.setBlockAndUpdate(pos, state.setValue(OPEN, open));

        if (facingVerticalState.getBlock() instanceof Curtain && facingVertical == facingVerticalState.getValue(VERTICAL_PART).getOpposite())
            level.setBlockAndUpdate(facingVerticalPos, facingVerticalState.setValue(OPEN, open));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HORIZONTAL_PART, OPEN, VERTICAL_PART, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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