package net.witheraway.tfcjutefurniture.block.furniture.wicker.sofa;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.witheraway.tfcjutefurniture.block.furniture.SeatBlock;
import net.witheraway.tfcjutefurniture.entity.SeatEntity;

public class Ottoman extends SeatBlock {
    protected static final VoxelShape BASE = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), new VoxelShape[0]);

    public Ottoman(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)this.stateDefinition.any());
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult hitResult) {
        return SeatEntity.create(level, pos, 0.15, playerEntity);
    }

    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        return BASE;
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
}
