package com.xulai.dreadsteel.item.armor;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.config.DreadsteelCommonConfig;
import com.xulai.dreadsteel.item.CosmeticKit;
import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Dreadsteel.MOD_ID)
public class ArmorEvents {

    private static ResourceLocation modifierId(Item item, String kind) {
        return ResourceLocation.fromNamespaceAndPath(Dreadsteel.MOD_ID, BuiltInRegistries.ITEM.getKey(item).getPath() + "_" + kind);
    }

    @SubscribeEvent
    public static void dreadsteelSetDefence(final LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != DreadsteelItems.DREADSTEEL_HELMET.get()) return;
        if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() != DreadsteelItems.DREADSTEEL_CHESTPLATE.get()) return;
        if (entity.getItemBySlot(EquipmentSlot.LEGS).getItem() != DreadsteelItems.DREADSTEEL_LEGGINGS.get()) return;
        if (entity.getItemBySlot(EquipmentSlot.FEET).getItem() != DreadsteelItems.DREADSTEEL_BOOTS.get()) return;

        DamageSource source = event.getSource();
        if (source.is(DamageTypes.LIGHTNING_BOLT) || source.is(DamageTypes.IN_FIRE)
                || source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.CACTUS)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void dreadsteelAttributeEvent(ItemAttributeModifierEvent event) {
        Item item = event.getItemStack().getItem();
        Integer armorValue = armorValueOf(item);
        if (armorValue == null) return;

        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(((ArmorItem) item).getEquipmentSlot());
        event.addModifier(Attributes.ARMOR,
                new AttributeModifier(modifierId(item, "armor"), armorValue, AttributeModifier.Operation.ADD_VALUE), slot);
        event.addModifier(Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(modifierId(item, "toughness"), DreadsteelCommonConfig.ARMOR_TOUGHNESS.get(), AttributeModifier.Operation.ADD_VALUE), slot);
        event.addModifier(Attributes.KNOCKBACK_RESISTANCE,
                new AttributeModifier(modifierId(item, "knockback_resistance"), DreadsteelCommonConfig.ARMOR_KNOCKBACK_RESISTANCE.get(), AttributeModifier.Operation.ADD_VALUE), slot);
    }

    private static Integer armorValueOf(Item item) {
        if (item == DreadsteelItems.DREADSTEEL_HELMET.get()) return DreadsteelCommonConfig.HELMET_ARMOR.get();
        if (item == DreadsteelItems.DREADSTEEL_CHESTPLATE.get()) return DreadsteelCommonConfig.CHESTPLATE_ARMOR.get();
        if (item == DreadsteelItems.DREADSTEEL_LEGGINGS.get()) return DreadsteelCommonConfig.LEGGINGS_ARMOR.get();
        if (item == DreadsteelItems.DREADSTEEL_BOOTS.get()) return DreadsteelCommonConfig.BOOTS_ARMOR.get();
        return null;
    }

    @SubscribeEvent
    public static void onAnvilDyeEvent(final AnvilUpdateEvent event) {
        ItemStack leftStack = event.getLeft();
        Item rightItem = event.getRight().getItem();
        if (!isDyeableDreadsteelItem(leftStack.getItem())) return;
        if (!(rightItem instanceof CosmeticKit)) return;

        ItemStack result = leftStack.copy();
        if (rightItem == DreadsteelItems.DEFAULT_KIT.get()) {
            result.remove(DataComponents.CUSTOM_MODEL_DATA);
        } else if (rightItem == DreadsteelItems.WHITE_KIT.get()) {
            result.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1));
        } else if (rightItem == DreadsteelItems.BLACK_KIT.get()) {
            result.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(2));
        } else if (rightItem == DreadsteelItems.BRONZE_KIT.get()) {
            result.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(3));
        } else {
            return;
        }

        long xpCost = 1L;
        String name = event.getName();
        if (name != null && !name.isBlank()) {
            if (!name.equals(leftStack.getHoverName().getString())) {
                result.set(DataComponents.CUSTOM_NAME, Component.literal(name));
                xpCost += 1L;
            }
        } else if (leftStack.has(DataComponents.CUSTOM_NAME)) {
            result.remove(DataComponents.CUSTOM_NAME);
        }

        event.setMaterialCost(1);
        event.setOutput(result);
        event.setCost(xpCost);
    }

    private static boolean isDyeableDreadsteelItem(Item item) {
        return item == DreadsteelItems.DREADSTEEL_HELMET.get()
                || item == DreadsteelItems.DREADSTEEL_CHESTPLATE.get()
                || item == DreadsteelItems.DREADSTEEL_LEGGINGS.get()
                || item == DreadsteelItems.DREADSTEEL_BOOTS.get()
                || item == DreadsteelItems.DREADSTEEL_SCYTHE.get()
                || item == DreadsteelItems.DREADSTEEL_SHIELD.get();
    }
}
