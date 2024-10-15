package net.witheraway.tfcjutefurniture.util;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.RegisteredDataManager;
import net.dries007.tfc.util.climate.ClimateRange;

import java.util.Locale;
import java.util.function.Supplier;

public class JFTFCClimateRanges {
    public static final Supplier<ClimateRange> FLAX = register("flax");

    private static RegisteredDataManager.Entry<ClimateRange> register(String name)
    {
        return ClimateRange.MANAGER.register(Helpers.identifier(name.toLowerCase(Locale.ROOT)));
    }

}
