package net.witheraway.tfcjutefurniture.block;

import net.dries007.tfc.common.fluids.ExtendedFluidType;
import net.dries007.tfc.common.fluids.FluidRegistryObject;
import net.dries007.tfc.common.fluids.FluidTypeClientProperties;
import net.dries007.tfc.common.fluids.MixingFluid;
import net.dries007.tfc.util.Helpers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.witheraway.tfcjutefurniture.item.JFTFCItems;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.dries007.tfc.common.fluids.TFCFluids.*;
import static net.witheraway.tfcjutefurniture.JuteFurnitureTFC.MODID;

public class JFTFCFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

    public static final FluidRegistryObject<ForgeFlowingFluid> LINSEED_OIL = register(
            "linseed_oil",
            properties -> properties
                    .block(JFTFCBlocks.LINSEED_OIL)
                    .bucket(JFTFCItems.FLUID_BUCKETS.get(JFTFCFluidID.LINSEED_OIL)),
            waterLike()
                    .descriptionId("fluid.jftfc.linseed_oil"),
            new FluidTypeClientProperties(ALPHA_MASK | 0xffba5e, WATER_STILL, WATER_FLOW, WATER_OVERLAY, UNDERWATER_LOCATION),
            MixingFluid.Source::new,
            MixingFluid.Flowing::new
    );
    public static final FluidRegistryObject<ForgeFlowingFluid> TAR = register(
            "tar",
            properties -> properties
                    .block(JFTFCBlocks.TAR)
                    .bucket(JFTFCItems.FLUID_BUCKETS.get(JFTFCFluidID.TAR)),
            tar()
                    .descriptionId("fluid.jftfc.tar"),
            new FluidTypeClientProperties(ALPHA_MASK | 0x201404, WATER_STILL, WATER_FLOW, WATER_OVERLAY, UNDERWATER_LOCATION),
            MixingFluid.Source::new,
            MixingFluid.Flowing::new
    );

    private static FluidType.Properties waterLike()
    {
        return FluidType.Properties.create()
                .adjacentPathType(BlockPathTypes.WATER)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .canConvertToSource(true)
                .canDrown(true)
                .canExtinguish(true)
                .canHydrate(true)
                .canPushEntity(true)
                .canSwim(true)
                .supportsBoating(true);
    }
    private static FluidType.Properties tar()
    {
        return FluidType.Properties.create()
                .adjacentPathType(BlockPathTypes.LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .lightLevel(0)
                .density(6000)
                .viscosity(9000)
                .canConvertToSource(true)
                .canDrown(true)
                .canExtinguish(false)
                .canHydrate(false)
                .canPushEntity(false)
                .canSwim(false)
                .supportsBoating(false);
    }

    private static <F extends FlowingFluid> FluidRegistryObject<F> register(String name, Consumer<ForgeFlowingFluid.Properties> builder, FluidType.Properties typeProperties, FluidTypeClientProperties clientProperties, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        final int index = name.lastIndexOf('/');
        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);

        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder, () -> new ExtendedFluidType(typeProperties, clientProperties), sourceFactory, flowingFactory);
    }


}
