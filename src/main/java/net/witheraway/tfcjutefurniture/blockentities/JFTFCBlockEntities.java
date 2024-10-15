package net.witheraway.tfcjutefurniture.blockentities;

import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.witheraway.tfcjutefurniture.JuteFurnitureTFC;
import net.witheraway.tfcjutefurniture.block.JFTFCBlocks;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class JFTFCBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, JuteFurnitureTFC.MODID);

    public static final RegistryObject<BlockEntityType<FlaxPlantBlockEntity>> FLAX_PLANT = register("flax_plant", FlaxPlantBlockEntity::new, JFTFCBlocks.CROPS.values().stream());



    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
