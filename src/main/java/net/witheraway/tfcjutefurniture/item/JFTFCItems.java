package net.witheraway.tfcjutefurniture.item;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.SelfTests;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.witheraway.tfcjutefurniture.JuteFurnitureTFC;
import net.witheraway.tfcjutefurniture.block.JFTFCBlocks;
import net.witheraway.tfcjutefurniture.block.JFTFCCrop;
import net.witheraway.tfcjutefurniture.block.JFTFCFluidID;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class JFTFCItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JuteFurnitureTFC.MODID);

    public static final RegistryObject<Item> WICKER = ITEMS.register("wicker",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WICKER_BUNDLE = ITEMS.register("wicker_bundle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LINSEED_PASTE = ITEMS.register("linseed_paste",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLAX = ITEMS.register("flax",
            () -> new Item(new Item.Properties()));

    public static final Map<JFTFCCrop, RegistryObject<Item>> SEEDS = Helpers.mapOfKeys(JFTFCCrop.class, crop ->
            register("seeds/" + crop.name(), () -> new ItemNameBlockItem(JFTFCBlocks.CROPS.get(crop).get(), new Item.Properties()))
    );
    public static final Map<JFTFCFluidID, RegistryObject<BucketItem>> FLUID_BUCKETS = JFTFCFluidID.mapOf(fluid ->
            register("bucket/" + fluid.name(), () -> new BucketItem(fluid.fluid(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
    );



    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg.get().toString());
            SelfTests.reportExternalError();
            return;
        }
        out.accept(reg.get());
    }
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
