package net.witheraway.tfcjutefurniture.client;

import net.dries007.tfc.client.IGhostBlockHandler;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.witheraway.tfcjutefurniture.block.JFTFCBlocks;

import java.util.function.Predicate;

public final class JFTFCClientEvents {

    public static void init()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(JFTFCClientEvents::setup);
    }


    public static void setup(FMLClientSetupEvent event) {


        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();
        final Predicate<RenderType> ghostBlock = rt -> rt == cutoutMipped || rt == Sheets.translucentCullBlockSheet();

        JFTFCBlocks.CROPS.values().forEach(crop -> ItemBlockRenderTypes.setRenderLayer(crop.get(), cutout));
        //JFTFCBlocks.DEAD_CROPS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        //JFTFCBlocks.WILD_CROPS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));

    }

}
