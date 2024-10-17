package net.witheraway.tfcjutefurniture.blockentities;

import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class JFTFCCropBlockEntity extends CropBlockEntity
{
    public JFTFCCropBlockEntity(BlockPos pos, BlockState state)
    {
        this(JFTFCBlockEntities.CROP.get(), pos, state);
    }

    public JFTFCCropBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }
}
