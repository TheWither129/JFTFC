package net.witheraway.tfcjutefurniture.client;

import net.dries007.tfc.client.ClientEventHandler;
import net.dries007.tfc.client.ColorMapReloadListener;
import net.dries007.tfc.client.IGhostBlockHandler;
import net.dries007.tfc.client.TFCColors;
import net.dries007.tfc.common.blocks.soil.ConnectedGrassBlock;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
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
        bus.addListener(JFTFCClientEvents::registerColorHandlerBlocks);
        bus.addListener(JFTFCClientEvents::registerColorHandlerItems);
        bus.addListener(JFTFCClientEvents::registerClientReloadListners);
    }


    public static void setup(FMLClientSetupEvent event) {


        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();
        final Predicate<RenderType> ghostBlock = rt -> rt == cutoutMipped || rt == Sheets.translucentCullBlockSheet();

        JFTFCBlocks.CROPS.values().forEach(crop -> ItemBlockRenderTypes.setRenderLayer(crop.get(), cutout));
        JFTFCBlocks.DEAD_CROPS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        JFTFCBlocks.WILD_CROPS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));

    }
    public static void registerColorHandlerBlocks(RegisterColorHandlersEvent.Block event)
    {
        final BlockColor grassColor = (state, level, pos, tintIndex) -> TFCColors.getGrassColor(pos, tintIndex);
        final BlockColor tallGrassColor = (state, level, pos, tintIndex) -> TFCColors.getTallGrassColor(pos, tintIndex);
        final BlockColor foliageColor = (state, level, pos, tintIndex) -> TFCColors.getFoliageColor(pos, tintIndex);
        final BlockColor grassBlockColor = (state, level, pos, tintIndex) -> state.getValue(ConnectedGrassBlock.SNOWY) || tintIndex != 1 ? -1 : grassColor.getColor(state, level, pos, tintIndex);


        JFTFCBlocks.WILD_CROPS.forEach((crop, reg) -> event.register(grassColor, reg.get()));
    }

    private static BlockColor blockColor(int color)
    {
        return (state, level, pos, tintIndex) -> color;
    }
    public static void registerColorHandlerItems(RegisterColorHandlersEvent.Item event) {
        final ItemColor grassColor = (stack, tintIndex) -> TFCColors.getGrassColor(null, tintIndex);
        final ItemColor seasonalFoliageColor = (stack, tintIndex) -> TFCColors.getFoliageColor(null, tintIndex);

        JFTFCBlocks.WILD_CROPS.forEach((key, value) -> event.register(grassColor, value.get().asItem()));
    }

    public static void registerClientReloadListners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new ColorMapReloadListener(TFCColors::setGrassColors, TFCColors.GRASS_COLORS_LOCATION));
    }

}
