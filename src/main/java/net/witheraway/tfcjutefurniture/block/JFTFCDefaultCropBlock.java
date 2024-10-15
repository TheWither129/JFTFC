package net.witheraway.tfcjutefurniture.block;

import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.CropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.climate.ClimateRanges;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.witheraway.tfcjutefurniture.item.JFTFCItems;

import java.util.function.Supplier;

public abstract class JFTFCDefaultCropBlock extends CropBlock {

    public static JFTFCDefaultCropBlock create(ExtendedProperties properties, int stages, JFTFCCrop crop) {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(stages - 1);
        return new JFTFCDefaultCropBlock(properties, stages - 1, JFTFCBlocks.DEAD_FLAX, JFTFCItems.SEEDS.get(crop), crop.getNutrient(), ClimateRanges.CROPS.get(crop))
        {
            @Override
            public void die(Level level, BlockPos blockPos, BlockState blockState, boolean b) {

            }

            @Override
            public IntegerProperty getAgeProperty() {
                return property;
            }

            @Override
            protected void postGrowthTick(Level level, BlockPos blockPos, BlockState blockState, CropBlockEntity cropBlockEntity) {

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
        level.setBlockAndUpdate(pos, state.setValue(getAgeProperty(), 0));
        if (level.getBlockEntity(pos) instanceof CropBlockEntity crop)
        {
            crop.setGrowth(0f);
            crop.setYield(0f);
            crop.setExpiry(0f);
        }
    }

    @Override
    protected void postGrowthTick(Level level, BlockPos pos, BlockState state, CropBlockEntity crop)
    {
        final int age = crop.getGrowth() == 1 ? getMaxAge() : (int) (crop.getGrowth() * getMaxAge());
        level.setBlockAndUpdate(pos, state.setValue(getAgeProperty(), age));
    }
}