package net.witheraway.tfcjutefurniture.block;

import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.DeadCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.witheraway.tfcjutefurniture.item.JFTFCItems;
import net.witheraway.tfcjutefurniture.util.JFTFCClimateRanges;

import java.util.function.Supplier;

public abstract class JFTFCDefaultCropBlock extends JFTFCCropBlock {

    public static JFTFCDefaultCropBlock create(ExtendedProperties properties, int stages, JFTFCCrop crop) {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(stages - 1);
        return new JFTFCDefaultCropBlock(properties, stages - 1, JFTFCBlocks.DEAD_CROPS.get(crop), JFTFCItems.SEEDS.get(crop), crop.getPrimaryNutrient(), JFTFCClimateRanges.CROPS.get(crop))
        {
            @Override
            public IntegerProperty getAgeProperty()
            {
                return property;
            }
        };
    }

    protected JFTFCDefaultCropBlock(ExtendedProperties properties, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, FarmlandBlockEntity.NutrientType primaryNutrient, Supplier<ClimateRange> climateRange)
    {
        super(properties, maxAge, dead, seeds, primaryNutrient, climateRange);
    }

    @Override
    public void die(Level level, BlockPos pos, BlockState state, boolean fullyGrown)
    {
        final BlockState deadState = dead.get().defaultBlockState().setValue(DeadCropBlock.MATURE, fullyGrown);
        level.setBlockAndUpdate(pos, deadState);
    }

    @Override
    protected void postGrowthTick(Level level, BlockPos pos, BlockState state, CropBlockEntity crop)
    {
        final int age = crop.getGrowth() == 1 ? getMaxAge() : (int) (crop.getGrowth() * getMaxAge());
        level.setBlockAndUpdate(pos, state.setValue(getAgeProperty(), age));
    }
}