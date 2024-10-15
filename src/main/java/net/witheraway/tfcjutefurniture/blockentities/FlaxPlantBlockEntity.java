package net.witheraway.tfcjutefurniture.blockentities;

import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FlaxPlantBlockEntity extends CropBlockEntity
{
    public FlaxPlantBlockEntity(BlockPos pos, BlockState state)
    {
        this(JFTFCBlockEntities.FLAX_PLANT.get(), pos, state);
    }

    public FlaxPlantBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }
}
