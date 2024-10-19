package net.witheraway.tfcjutefurniture.util;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.RegisteredDataManager;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.climate.ClimateRanges;
import net.witheraway.tfcjutefurniture.block.JFTFCCrop;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class JFTFCClimateRanges extends ClimateRanges {
    public static final Map<JFTFCCrop, Supplier<ClimateRange>> CROPS = Helpers.mapOfKeys(JFTFCCrop.class, crop -> register("crop/" + crop.getSerializedName()));

    private static RegisteredDataManager.Entry<ClimateRange> register(String name)
    {
        return ClimateRange.MANAGER.register(JFHelpers.identifier(name.toLowerCase(Locale.ROOT)));
    }
}
