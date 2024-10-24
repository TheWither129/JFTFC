package net.witheraway.tfcjutefurniture;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.blocks.DecorationBlockRegistryObject;
import net.dries007.tfc.util.SelfTests;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.witheraway.tfcjutefurniture.block.JFTFCBlocks;
import net.witheraway.tfcjutefurniture.item.JFTFCItems;

public final class CreativeTabs {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JuteFurnitureTFC.MODID);

  public static final TFCCreativeTabs.CreativeTabHolder JFTFCITEMS = register("jftfcitems", () -> new ItemStack(JFTFCItems.WICKER.get()), CreativeTabs::fillJFTFCitemsTab);
  public static final TFCCreativeTabs.CreativeTabHolder JFTFCBLOCKS = register("jftfcblocks", () -> new ItemStack(JFTFCBlocks.WICKER_SOFA.get()), CreativeTabs::fillJFTFCblocksTab);

    public static void fillJFTFCitemsTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        accept(out, JFTFCItems.WICKER);
        accept(out, JFTFCItems.WICKER_BUNDLE);
        accept(out, JFTFCItems.FLAX);
        JFTFCItems.SEEDS.values().forEach(seed -> accept(out, seed));
        accept(out, JFTFCItems.LINSEED_PASTE);
        JFTFCItems.FLUID_BUCKETS.values().forEach(bucket -> accept(out, bucket));
    }
    public static void fillJFTFCblocksTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        accept(out, JFTFCBlocks.WICKER_BLOCK);
        accept(out, JFTFCBlocks.COREWOOD_LOGS);
        accept(out, JFTFCBlocks.FINEWOOD_PLANKS);
        accept(out, JFTFCBlocks.DARKWOOD_PLANKS);
        accept(out, JFTFCBlocks.WICKER_OTTOMAN);
        accept(out, JFTFCBlocks.WICKER_SOFA);
        accept(out, JFTFCBlocks.WICKER_END_TABLE);
        JFTFCBlocks.WILD_CROPS.values().forEach(wild_crop ->accept(out, wild_crop));
    }

    private static TFCCreativeTabs.CreativeTabHolder register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("tfc.creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new TFCCreativeTabs.CreativeTabHolder(reg, displayItems);
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
            TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            SelfTests.reportExternalError();
            return;
        }
        out.accept(reg.get());
    }

    private static void accept(CreativeModeTab.Output out, DecorationBlockRegistryObject decoration)
    {
        out.accept(decoration.stair().get());
        out.accept(decoration.slab().get());
        out.accept(decoration.wall().get());
    }

    private static <T> void consumeOurs(Registry<T> registry, Consumer<T> consumer)
    {
        for (T value : registry)
        {
            if (Objects.requireNonNull(registry.getKey(value)).getNamespace().equals(TerraFirmaCraft.MOD_ID))
            {
                consumer.accept(value);
            }
        }
    }

    public record CreativeTabHolder(RegistryObject<CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {}

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}
