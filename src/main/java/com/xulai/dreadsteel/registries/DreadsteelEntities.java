package com.xulai.dreadsteel.registries;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.entity.EntityScytheProjectileBlack;
import com.xulai.dreadsteel.entity.EntityScytheProjectileBronze;
import com.xulai.dreadsteel.entity.EntityScytheProjectileDefault;
import com.xulai.dreadsteel.entity.EntityScytheProjectileWhite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DreadsteelEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Dreadsteel.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntityScytheProjectileDefault>> SCYTHE_PROJECTILE_DEFAULT
            = registerEntity(EntityType.Builder.<EntityScytheProjectileDefault>of(EntityScytheProjectileDefault::new, MobCategory.MISC).sized(0.75F, 1.0F), "scythe_projectile_default");

    public static final DeferredHolder<EntityType<?>, EntityType<EntityScytheProjectileBlack>> SCYTHE_PROJECTILE_BLACK
            = registerEntity(EntityType.Builder.<EntityScytheProjectileBlack>of(EntityScytheProjectileBlack::new, MobCategory.MISC).sized(0.75F, 1.0F), "scythe_projectile_black");

    public static final DeferredHolder<EntityType<?>, EntityType<EntityScytheProjectileBronze>> SCYTHE_PROJECTILE_BRONZE
            = registerEntity(EntityType.Builder.<EntityScytheProjectileBronze>of(EntityScytheProjectileBronze::new, MobCategory.MISC).sized(0.75F, 1.0F), "scythe_projectile_bronze");

    public static final DeferredHolder<EntityType<?>, EntityType<EntityScytheProjectileWhite>> SCYTHE_PROJECTILE_WHITE
            = registerEntity(EntityType.Builder.<EntityScytheProjectileWhite>of(EntityScytheProjectileWhite::new, MobCategory.MISC).sized(0.75F, 1.0F), "scythe_projectile_white");

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerEntity(EntityType.Builder<T> builder, String entityName) {
        return ENTITIES.register(entityName, () -> builder.build(entityName));
    }
}
