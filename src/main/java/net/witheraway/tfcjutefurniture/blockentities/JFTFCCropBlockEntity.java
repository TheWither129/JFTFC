package net.witheraway.tfcjutefurniture.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.crop.DoubleCropBlock;
import net.dries007.tfc.common.blocks.crop.ICropBlock;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.dries007.tfc.common.blockentities.CropBlockEntity;

public class JFTFCCropBlockEntity extends CropBlockEntity implements ICalendarTickable
{
    public static void serverTick(Level level, BlockPos pos, BlockState state, CropBlockEntity crop)
    {
        crop.checkForCalendarUpdate();
    }

    public static void serverTickBottomPartOnly(Level level, BlockPos pos, BlockState state, CropBlockEntity crop)
    {
        if (state.getValue(DoubleCropBlock.PART) == DoubleCropBlock.Part.BOTTOM)
        {
            crop.checkForCalendarUpdate();
        }
    }

    private float growth;
    private float yield;
    private float expiry;

    private long lastUpdateTick;
    private long lastGrowthTick;

    public JFTFCCropBlockEntity(BlockPos pos, BlockState state)
    {
        this(JFTFCBlockEntities.CROP.get(), pos, state);
    }

    public JFTFCCropBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);

        lastUpdateTick = Integer.MIN_VALUE;
        lastGrowthTick = Calendars.SERVER.getTicks();
    }

    @Override
    public void onCalendarUpdate(long ticks)
    {
        assert level != null;
        final BlockState state = level.getBlockState(worldPosition);
        if (state.getBlock() instanceof ICropBlock crop)
        {
            crop.growthTick(level, worldPosition, state, this);
        }
    }

    public float getGrowth()
    {
        return growth;
    }

    public float getYield()
    {
        return yield;
    }

    public float getExpiry()
    {
        return expiry;
    }

    public void setGrowth(float growth)
    {
        this.growth = growth;
        markForSync();
    }

    public void setYield(float yield)
    {
        this.yield = yield;
        markForSync();
    }

    public void setExpiry(float expiry)
    {
        this.expiry = expiry;
        markForSync();
    }

    public long getLastGrowthTick()
    {
        return lastGrowthTick;
    }

    public void setLastGrowthTick(long lastGrowthTick)
    {
        this.lastGrowthTick = lastGrowthTick;
        markForSync();
    }

    @Override
    @Deprecated
    public long getLastCalendarUpdateTick()
    {
        return lastUpdateTick;
    }

    @Override
    @Deprecated
    public void setLastCalendarUpdateTick(long tick)
    {
        lastUpdateTick = tick;
    }

    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider)
    {
        growth = nbt.getFloat("growth");
        yield = nbt.getFloat("yield");
        expiry = nbt.getFloat("expiry");
        lastUpdateTick = nbt.getLong("tick");
        lastGrowthTick = nbt.getLong("lastGrowthTick");
        super.loadAdditional(nbt);
    }

    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider)
    {
        nbt.putFloat("growth", growth);
        nbt.putFloat("yield", yield);
        nbt.putFloat("expiry", expiry);
        nbt.putLong("tick", lastUpdateTick);
        nbt.putLong("lastGrowthTick", lastGrowthTick);
        super.saveAdditional(nbt);
    }
}