package net.witheraway.tfcjutefurniture.block;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.witheraway.tfcjutefurniture.JuteFurnitureTFC;
import net.witheraway.tfcjutefurniture.item.JFTFCItems;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class JFTFCBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, JuteFurnitureTFC.MODID);



    public static final RegistryObject<Block> WICKER_BLOCK = registerBlock("wicker_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> FINEWOOD_PLANKS = registerBlock("finewood_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BIRCH_PLANKS)));
    public static final RegistryObject<Block> DARKWOOD_PLANKS = registerBlock("darkwood_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryObject<Block> COREWOOD_LOGS = registerBlock("corewood_logs",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)));

    public static final Map<JFTFCCrop, RegistryObject<Block>> CROPS = Helpers.mapOfKeys(JFTFCCrop.class, crop -> registerNoItem("crop/" + crop.name(), crop::create));
    public static final Map<JFTFCCrop, RegistryObject<Block>> DEAD_CROPS = Helpers.mapOfKeys(JFTFCCrop.class, crop -> registerNoItem("dead_crop/" + crop.name(), crop::createDead));
    public static final Map<JFTFCCrop, RegistryObject<Block>> WILD_CROPS = Helpers.mapOfKeys(JFTFCCrop.class, crop ->
            register("wild_crop/" + crop.name(), crop::createWild)
    );

    public static final RegistryObject<LiquidBlock> LINSEED_OIL = registerNoItem("fluid/linseed_oil", () -> new LiquidBlock(JFTFCFluids.LINSEED_OIL.source(), BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> TAR = registerNoItem("fluid/tar", () -> new LiquidBlock(JFTFCFluids.TAR.source(), BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));


    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return JFTFCItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, b -> new BlockItem(b, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(BLOCKS, JFTFCItems.ITEMS, name, blockSupplier, blockItemFactory);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
