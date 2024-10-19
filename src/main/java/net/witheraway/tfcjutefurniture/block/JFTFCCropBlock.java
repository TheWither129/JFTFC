package net.witheraway.tfcjutefurniture.block;

import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blockentities.IFarmland;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.crop.CropBlock;
import net.dries007.tfc.common.blocks.crop.CropHelpers;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.witheraway.tfcjutefurniture.blockentities.JFTFCCropBlockEntity;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class JFTFCCropBlock extends CropBlock {

    protected final FarmlandBlockEntity.NutrientType primaryNutrient;
    protected final Supplier<? extends Block> dead;
    protected final Supplier<? extends Item> seeds;
    protected final Supplier<ClimateRange> climateRange;
    protected final int maxAge;
    private final ExtendedProperties extendedProperties;

    protected JFTFCCropBlock(ExtendedProperties properties, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, FarmlandBlockEntity.NutrientType primaryNutrient, Supplier<ClimateRange> climateRange)
    {
        super(properties, maxAge, dead, seeds, primaryNutrient, climateRange);

        this.extendedProperties = properties;
        this.maxAge = maxAge;
        this.dead = dead;
        this.seeds = seeds;
        this.primaryNutrient = primaryNutrient;
        this.climateRange = climateRange;

        registerDefaultState(defaultBlockState().setValue(getAgeProperty(), 0));
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return extendedProperties;
    }

    @Override
    public abstract IntegerProperty getAgeProperty();

    @Override
    public int getMaxAge()
    {
        return maxAge;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        tick(state, level, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : state;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(getAgeProperty());
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        if (!level.isClientSide())
        {
            if (canSurvive(state, level, pos))
            {
                if (level.getBlockEntity(pos) instanceof JFTFCCropBlockEntity crop)
                {
                    growthTick(level, pos, state, crop);
                }
            }
            else
            {
                // Cannot survive here (e.g. no farmland below)
                level.destroyBlock(pos, true);
            }
        }
    }

    public void addHoeOverlayInfo(Level level, BlockPos pos, BlockState state, Consumer<Component> text, boolean isDebug)
    {
        final ClimateRange range = climateRange.get();
        final BlockPos sourcePos = pos.below();

        text.accept(FarmlandBlock.getTemperatureTooltip(level, pos, range, false));
        text.accept(FarmlandBlock.getHydrationTooltip(level, sourcePos, range, false));

        IFarmland farmland = null;
        if (level.getBlockEntity(sourcePos) instanceof IFarmland found)
        {
            farmland = found;
        }
        else if (level.getBlockEntity(sourcePos.below()) instanceof IFarmland found)
        {
            farmland = found;
        }
        if (farmland != null)
        {
            farmland.addTooltipInfo((List<Component>) text);
        }

        if (level.getBlockEntity(pos) instanceof JFTFCCropBlockEntity crop)
        {
            if (isDebug)
            {
                text.accept(Component.literal(String.format("[Debug] Growth = %.4f Yield = %.4f Expiry = %.4f Last Tick = %d Delta = %d", crop.getGrowth(), crop.getYield(), crop.getExpiry(), crop.getLastGrowthTick(), Calendars.get(level).getTicks() - crop.getLastGrowthTick())));
            }
            if (crop.getGrowth() >= 1)
            {
                text.accept(Component.translatable("tfc.tooltip.farmland.mature"));
            }
        }
    }

    public void growthTick(Level level, BlockPos pos, BlockState state, JFTFCCropBlockEntity crop)
    {
        if (!level.isClientSide() && CropHelpers.growthTick(level, pos, state, crop))
        {
            postGrowthTick(level, pos, state, crop);
        }
    }

    @Override
    public ClimateRange getClimateRange()
    {
        return climateRange.get();
    }

    @Override
    public FarmlandBlockEntity.NutrientType getPrimaryNutrient()
    {
        return primaryNutrient;
    }

    protected abstract void postGrowthTick(Level level, BlockPos pos, BlockState state, JFTFCCropBlockEntity crop);

}
