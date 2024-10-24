package net.witheraway.tfcjutefurniture.block.furniture.wicker.sofa;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.witheraway.tfcjutefurniture.block.furniture.SeatBlock;
import net.witheraway.tfcjutefurniture.block.properties.JFBlockStateProperties;
import net.witheraway.tfcjutefurniture.block.properties.SofaPart;
import net.witheraway.tfcjutefurniture.entity.SeatEntity;
import net.witheraway.tfcjutefurniture.util.JFShapeHelper;


public class Sofa extends SeatBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<SofaPart> PART = JFBlockStateProperties.SOFA_PART;

    public static final VoxelShape BASE = Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);
    public static final VoxelShape BACK = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    public static final VoxelShape BACK_I_CORNER = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape BACK_CUSHION = Block.box(1.0, 0.0, 11.0, 15.0, 16.0, 15.0);
    public static final VoxelShape BACK_CUSHION_I_CORNER = Block.box(11.0, 0.0, 0.0, 15.0, 16.0, 11.0);
    public static final VoxelShape BACK_CUSHION_O_CORNER = Block.box(11.0, 0.0, 11.0, 16.0, 16.0, 16.0);
    public static final VoxelShape BACK_CUSHION_MIDDLE = Block.box(0.0, 0.0, 11.0, 16.0, 16.0, 15.0);
    public static final VoxelShape BACK_CUSHION_LEFT = Block.box(0.0, 0.0, 11.0, 15.0, 16.0, 15.0);
    public static final VoxelShape BACK_CUSHION_RIGHT = Block.box(1.0, 0.0, 11.0, 16.0, 16.0, 15.0);

    public static final VoxelShape ARM_LEFT = Block.box(0.0, 0.0, 0.0, 3.0, 9.0, 16.0);
    public static final VoxelShape ARM_RIGHT = Block.box(13.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    public static final VoxelShape SINGLE = Shapes.or(BASE, BACK, BACK_CUSHION, ARM_RIGHT, ARM_LEFT);
    public static final VoxelShape SINGLE_EAST = JFShapeHelper.rotateShape(SINGLE, Direction.EAST);
    public static final VoxelShape SINGLE_SOUTH = JFShapeHelper.rotateShape(SINGLE, Direction.SOUTH);
    public static final VoxelShape SINGLE_WEST = JFShapeHelper.rotateShape(SINGLE, Direction.WEST);

    public static final VoxelShape MIDDLE = Shapes.or(BASE, BACK, BACK_CUSHION_MIDDLE);
    public static final VoxelShape MIDDLE_EAST = JFShapeHelper.rotateShape(MIDDLE, Direction.EAST);
    public static final VoxelShape MIDDLE_SOUTH = JFShapeHelper.rotateShape(MIDDLE, Direction.SOUTH);
    public static final VoxelShape MIDDLE_WEST = JFShapeHelper.rotateShape(MIDDLE, Direction.WEST);

    public static final VoxelShape OUTER = Shapes.or(BASE, BACK_CUSHION_O_CORNER);
    public static final VoxelShape OUTER_EAST = JFShapeHelper.rotateShape(OUTER, Direction.EAST);
    public static final VoxelShape OUTER_SOUTH = JFShapeHelper.rotateShape(OUTER, Direction.SOUTH);
    public static final VoxelShape OUTER_WEST = JFShapeHelper.rotateShape(OUTER, Direction.WEST);

    public static final VoxelShape INNER = Shapes.or(BASE, BACK, BACK_I_CORNER, BACK_CUSHION_MIDDLE, BACK_CUSHION_I_CORNER);
    public static final VoxelShape INNER_EAST = JFShapeHelper.rotateShape(INNER, Direction.EAST);
    public static final VoxelShape INNER_SOUTH = JFShapeHelper.rotateShape(INNER, Direction.SOUTH);
    public static final VoxelShape INNER_WEST = JFShapeHelper.rotateShape(INNER, Direction.WEST);

    public static final VoxelShape RIGHT = Shapes.or(BASE, BACK, BACK_CUSHION_RIGHT, ARM_LEFT);
    public static final VoxelShape RIGHT_EAST = JFShapeHelper.rotateShape(RIGHT, Direction.EAST);
    public static final VoxelShape RIGHT_SOUTH = JFShapeHelper.rotateShape(RIGHT, Direction.SOUTH);
    public static final VoxelShape RIGHT_WEST = JFShapeHelper.rotateShape(RIGHT, Direction.WEST);

    public static final VoxelShape LEFT = Shapes.or(BASE, BACK, BACK_CUSHION_LEFT, ARM_RIGHT);
    public static final VoxelShape LEFT_EAST = JFShapeHelper.rotateShape(LEFT, Direction.EAST);
    public static final VoxelShape LEFT_SOUTH = JFShapeHelper.rotateShape(LEFT, Direction.SOUTH);
    public static final VoxelShape LEFT_WEST = JFShapeHelper.rotateShape(LEFT, Direction.WEST);

    public Sofa(Properties properties) {
        super(properties);
        this.registerDefaultState((this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false))
        );
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult hitResult) {
        return SeatEntity.create(level, pos, 0.15, playerEntity);
    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance * 0.5F);
    }

    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        if (pEntity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(pLevel, pEntity);
        } else {
            this.bounceUp(pEntity);
        }

    }

    private void bounceUp(Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        if (vec3.y < 0.0D) {
            double d0 = pEntity instanceof LivingEntity ? 1.0D : 0.8D;
            pEntity.setDeltaMovement(vec3.x, -vec3.y * (double)0.66F * d0, vec3.z);
        }

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(blockPos);
        BlockState blockState = this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        return blockState.setValue(PART, getConnection(blockState, context.getLevel(), blockPos));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return direction.getAxis().isHorizontal() ? state.setValue(PART, getConnection(state, (Level)level, currentPos)) : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        SofaPart shape = state.getValue(PART);
        return switch (shape) {
            case SINGLE -> switch (facing) {
                case EAST -> SINGLE_EAST;
                case SOUTH -> SINGLE_SOUTH;
                case WEST -> SINGLE_WEST;
                default -> SINGLE;
            };
            case MIDDLE -> switch (facing) {
                case EAST -> MIDDLE_EAST;
                case SOUTH -> MIDDLE_SOUTH;
                case WEST -> MIDDLE_WEST;
                default -> MIDDLE;
            };
            case OUTER_LEFT -> switch (facing) {
                case EAST -> OUTER_EAST;
                case SOUTH -> OUTER_SOUTH;
                case WEST -> OUTER_WEST;
                default -> OUTER;
            };
            case OUTER_RIGHT -> switch (facing) {
                case EAST -> OUTER_SOUTH;
                case SOUTH -> OUTER_WEST;
                case WEST -> OUTER;
                default -> OUTER_EAST;
            };
            case LEFT -> switch (facing) {
                case EAST -> LEFT_EAST;
                case SOUTH -> LEFT_SOUTH;
                case WEST -> LEFT_WEST;
                default -> LEFT;
            };
            case RIGHT -> switch (facing) {
                case EAST -> RIGHT_EAST;
                case SOUTH -> RIGHT_SOUTH;
                case WEST -> RIGHT_WEST;
                default -> RIGHT;
            };
            case INNER_RIGHT -> switch (facing) {
                case EAST -> INNER_SOUTH;
                case SOUTH -> INNER_WEST;
                case WEST -> INNER;
                default -> INNER_EAST;
            };
            case INNER_LEFT -> switch (facing) {
                case EAST -> INNER_EAST;
                case SOUTH -> INNER_SOUTH;
                case WEST -> INNER_WEST;
                default -> INNER;
            };
        };
    }
    public static SofaPart getConnection(BlockState state, Level level, BlockPos pos) {
        Direction facing = state.getValue(FACING);

        Direction dir1;
        BlockState state1 = level.getBlockState(pos.relative(facing));
        if (state1.getBlock() instanceof Sofa && (dir1 = state1.getValue(FACING)).getAxis() != state.getValue(FACING).getAxis() && isDifferentOrientation(state, level, pos, dir1.getOpposite())) {
            if (dir1 == facing.getCounterClockWise()) {
                return SofaPart.INNER_LEFT;
            }
            return SofaPart.INNER_RIGHT;
        }

        Direction dir2;
        BlockState state2 = level.getBlockState(pos.relative(facing.getOpposite()));
        if (state2.getBlock() instanceof Sofa && (dir2 = state2.getValue(FACING)).getAxis() != state.getValue(FACING).getAxis() && isDifferentOrientation(state, level, pos, dir2)) {
            if (dir2 == facing.getCounterClockWise()) {
                return SofaPart.OUTER_LEFT;
            }
            return SofaPart.OUTER_RIGHT;
        }

        boolean left = canConnect(level, pos, state.getValue(FACING).getCounterClockWise());
        boolean right = canConnect(level, pos, state.getValue(FACING).getClockWise());
        if (left && right) {
            return SofaPart.MIDDLE;
        }
        else if (left) {
            return SofaPart.LEFT;
        }
        else if (right) {
            return SofaPart.RIGHT;
        }
        return SofaPart.SINGLE;
    }

    public static boolean canConnect(Level level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos.relative(direction));
        return state.getBlock() instanceof Sofa;
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public static boolean isDifferentOrientation(BlockState state, Level level, BlockPos pos, Direction dir) {
        BlockState blockState = level.getBlockState(pos.relative(dir));
        return !(blockState.getBlock() instanceof Sofa) || blockState.getValue(FACING) != state.getValue(FACING);
    }


    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, PART, WATERLOGGED});
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        Direction direction = state.getValue(FACING);
        SofaPart type = state.getValue(PART);
        switch (mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    return switch (type) {
                        case INNER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PART, SofaPart.INNER_RIGHT);
                        case INNER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PART, SofaPart.INNER_LEFT);
                        case OUTER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PART, SofaPart.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PART, SofaPart.OUTER_LEFT);
                        default -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    return switch (type) {
                        case INNER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PART, SofaPart.INNER_LEFT);
                        case INNER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PART, SofaPart.INNER_RIGHT);
                        case OUTER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PART, SofaPart.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(PART, SofaPart.OUTER_LEFT);
                        default -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
        }
        return super.mirror(state, mirror);
    }


    static {
        FACING = HorizontalDirectionalBlock.FACING;
    }

}