package net.witheraway.tfcjutefurniture.block;

import net.dries007.tfc.common.fluids.TFCFluids;
import net.minecraft.world.level.material.Fluid;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record JFTFCFluidID(String name, OptionalInt color, Supplier<? extends Fluid> fluid)
{
    public static final JFTFCFluidID LINSEED_OIL = new JFTFCFluidID("linseed_oil", OptionalInt.empty(), JFTFCFluids.LINSEED_OIL.source());
    public static final JFTFCFluidID TAR = new JFTFCFluidID("tar", OptionalInt.empty(), JFTFCFluids.TAR.source());

    private static final Map<Enum<?>, JFTFCFluidID> IDENTITY = new HashMap<>();
    private static final List<JFTFCFluidID> VALUES = Stream.of(
                    Stream.of(LINSEED_OIL, TAR)
            )
            .flatMap(Function.identity())
            .toList();

    public static <R> Map<JFTFCFluidID, R> mapOf(Function<? super JFTFCFluidID, ? extends R> map)
    {
        return VALUES.stream().collect(Collectors.toMap(Function.identity(), map));
    }

    public static JFTFCFluidID asType(Enum<?> identity)
    {
        return IDENTITY.get(identity);
    }

    private static JFTFCFluidID fromEnum(Enum<?> identity, int color, String name, Supplier<? extends Fluid> fluid)
    {
        final JFTFCFluidID type = new JFTFCFluidID(name, OptionalInt.of(TFCFluids.ALPHA_MASK | color), fluid);
        IDENTITY.put(identity, type);
        return type;
    }
}