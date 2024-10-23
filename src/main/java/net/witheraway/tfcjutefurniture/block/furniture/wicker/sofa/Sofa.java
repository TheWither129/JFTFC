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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.witheraway.tfcjutefurniture.JuteFurnitureTFC;
import net.witheraway.tfcjutefurniture.block.furniture.SeatBlock;
import net.witheraway.tfcjutefurniture.entity.SeatEntity;

import java.util.EnumMap;
import java.util.Map;

public class Sofa extends SeatBlock {
    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap(Direction.class);
    public static final DirectionProperty FACING;
    private static final VoxelShape SHAPE;

    protected void runCalculation(VoxelShape shape) {
        Direction[] var2 = Direction.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Direction direction = var2[var4];
            SHAPES.put(direction, JuteFurnitureTFC.calculateShapes(direction, shape));
        }

    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    public Sofa(Properties properties) {
        super(properties);
        this.registerDefaultState((this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
        this.runCalculation(SHAPE);
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        SHAPE = Shapes.or(Block.box(3.0, 0.0, 0.0, 14.0, 6.0, 13.0), //Base
                new VoxelShape[]{Block.box(0.0, 0.0, 0.0, 3.0, 9.0, 16.0), //Arm west
                        Block.box(13.0, 0.0, 0.0, 16.0, 9.0, 16.0), //Arm east
                        Block.box(1.0, 0.0, 11.0, 15.0, 16.0, 15.0), //Back cushion
                        Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0) //Back
        }
        );
    }
}
