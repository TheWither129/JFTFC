package net.witheraway.tfcjutefurniture.block.furniture;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.witheraway.tfcjutefurniture.entity.SeatEntity;

public class SeatBlock extends FurnitureBlock {
    protected static final VoxelShape BASE = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0), new VoxelShape[0]);

    public SeatBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)this.stateDefinition.any());
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult hitResult) {
        return SeatEntity.create(level, pos, 0.15, playerEntity);
    }

    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        return BASE;
    }

}
