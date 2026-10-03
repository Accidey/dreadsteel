package com.xulai.dreadsteel;

import com.xulai.dreadsteel.client.ModelHandler;
import com.xulai.dreadsteel.client.models.armor.DreadsteelModel;
import com.xulai.dreadsteel.entity.renderer.RenderScytheProjectileBlack;
import com.xulai.dreadsteel.entity.renderer.RenderScytheProjectileBronze;
import com.xulai.dreadsteel.entity.renderer.RenderScytheProjectileDefault;
import com.xulai.dreadsteel.entity.renderer.RenderScytheProjectileWhite;
import com.xulai.dreadsteel.registries.DreadsteelEntities;
import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.util.EnumMap;
import java.util.Map;

@EventBusSubscriber(modid = Dreadsteel.MOD_ID, value = Dist.CLIENT)
public class DreadsteelClient {

    public static final ModelLayerLocation DREADSTEEL_ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Dreadsteel.MOD_ID, "main"), "dreadsteel_armor");

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ModelHandler.addCustomItemProperties();
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DREADSTEEL_ARMOR, DreadsteelModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DreadsteelEntities.SCYTHE_PROJECTILE_DEFAULT.get(), RenderScytheProjectileDefault::new);
        event.registerEntityRenderer(DreadsteelEntities.SCYTHE_PROJECTILE_BLACK.get(), RenderScytheProjectileBlack::new);
        event.registerEntityRenderer(DreadsteelEntities.SCYTHE_PROJECTILE_BRONZE.get(), RenderScytheProjectileBronze::new);
        event.registerEntityRenderer(DreadsteelEntities.SCYTHE_PROJECTILE_WHITE.get(), RenderScytheProjectileWhite::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {

            private final Map<ArmorItem.Type, HumanoidModel<?>> models = new EnumMap<>(ArmorItem.Type.class);

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                ArmorItem armorItem = (ArmorItem) itemStack.getItem();
                return this.models.computeIfAbsent(armorItem.getType(),
                        type -> new DreadsteelModel(Minecraft.getInstance().getEntityModels().bakeLayer(DreadsteelClient.DREADSTEEL_ARMOR), type));
            }
        }, DreadsteelItems.DREADSTEEL_HELMET.get(), DreadsteelItems.DREADSTEEL_CHESTPLATE.get(),
                DreadsteelItems.DREADSTEEL_LEGGINGS.get(), DreadsteelItems.DREADSTEEL_BOOTS.get());
    }
}
