package net.witheraway.tfcjutefurniture.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.witheraway.tfcjutefurniture.JuteFurnitureTFC;

public class JFTFCEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JuteFurnitureTFC.MODID);

    public static final RegistryObject<EntityType<Entity>> SEAT_ENTITY =
            ENTITY_TYPES.register("seat", () -> EntityType.Builder.of(SeatEntity::new, MobCategory.MISC)
                    .sized(0.0f, 0.0f).build("seat"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}