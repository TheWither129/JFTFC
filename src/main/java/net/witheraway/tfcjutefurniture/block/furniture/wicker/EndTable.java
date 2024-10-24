package net.witheraway.tfcjutefurniture.block.furniture.wicker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.witheraway.tfcjutefurniture.block.furniture.FurnitureBlock;

public class EndTable extends FurnitureBlock {
    protected static final VoxelShape BASE = Shapes.or(Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0), new VoxelShape[0]);
    protected static final VoxelShape TOP = Shapes.or(Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 16.0), new VoxelShape[0]);
    public static final VoxelShape TABLE = Shapes.or(BASE, TOP);

    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        return TABLE;
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public EndTable(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)this.stateDefinition.any());
    }
}
