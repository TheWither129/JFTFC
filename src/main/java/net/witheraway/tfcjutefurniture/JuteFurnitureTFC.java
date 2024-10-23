package net.witheraway.tfcjutefurniture;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.witheraway.tfcjutefurniture.block.JFTFCBlocks;
import net.witheraway.tfcjutefurniture.block.JFTFCFluids;
import net.witheraway.tfcjutefurniture.blockentities.JFTFCBlockEntities;
import net.witheraway.tfcjutefurniture.client.JFTFCClientEvents;
import net.witheraway.tfcjutefurniture.entity.JFTFCEntities;
import net.witheraway.tfcjutefurniture.item.JFTFCItems;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JuteFurnitureTFC.MODID)
public class JuteFurnitureTFC
{

    public static final String MODID = "jftfc";

    private static final Logger LOGGER = LogUtils.getLogger();

    public JuteFurnitureTFC()
    {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            JFTFCClientEvents.init();
        }

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        JFTFCItems.register(modEventBus);
        JFTFCBlocks.register(modEventBus);
        JFTFCBlockEntities.register(modEventBus);
        JFTFCEntities.register(modEventBus);
        JFTFCFluids.FLUIDS.register(modEventBus);
        JFTFCFluids.FLUID_TYPES.register(modEventBus);

        CreativeTabs.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    public static void buildTabContentsModded(BuildCreativeModeTabContentsEvent tabData) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
    public static VoxelShape calculateShapes(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};
        int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;

        for(int i = 0; i < times; ++i) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = Shapes.or(buffer[1], Shapes.box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX));
            });
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
