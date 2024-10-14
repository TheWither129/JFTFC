package net.witheraway.tfcjutefurniture.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.witheraway.tfcjutefurniture.JuteFurnitureTFC;

public class JFTFCItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JuteFurnitureTFC.MODID);

    public static final RegistryObject<Item> WICKER = ITEMS.register("wicker",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WICKER_BUNDLE = ITEMS.register("wicker_bundle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LINSEED_PASTE = ITEMS.register("linseed_paste",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLAX = ITEMS.register("FLAX",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
