package com.xulai.dreadsteel.item.armor;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class DreadsteelArmor extends ArmorItem {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, Dreadsteel.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DREADSTEEL_MATERIAL = ARMOR_MATERIALS.register("dreadsteel", DreadsteelArmor::createArmorMaterial);

    public DreadsteelArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    private static ArmorMaterial createArmorMaterial() {
        return new ArmorMaterial(
                Map.of(
                        ArmorItem.Type.HELMET, 0,
                        ArmorItem.Type.CHESTPLATE, 0,
                        ArmorItem.Type.LEGGINGS, 0,
                        ArmorItem.Type.BOOTS, 0,
                        ArmorItem.Type.BODY, 0),
                25,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(DreadsteelItems.DREADSTEEL_INGOT.get()),
                List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Dreadsteel.MOD_ID, "dreadsteel_armor_model"))),
                0.0F,
                0.0F);
    }

    public static ResourceLocation textureFor(ItemStack stack) {
        CustomModelData data = stack.get(DataComponents.CUSTOM_MODEL_DATA);
        String color = switch (data == null ? 0 : data.value()) {
            case 1 -> "white";
            case 2 -> "black";
            case 3 -> "bronze";
            default -> "default";
        };
        return ResourceLocation.fromNamespaceAndPath(Dreadsteel.MOD_ID, "textures/item/dreadsteel_armor_model_" + color + ".png");
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.dreadsteel.dreadsteel_setbonus"));
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return textureFor(stack);
    }
}
