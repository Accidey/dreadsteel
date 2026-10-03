package com.xulai.dreadsteel.registries;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.item.CosmeticKit;
import com.xulai.dreadsteel.item.DreadsteelIngot;
import com.xulai.dreadsteel.item.armor.DreadsteelArmor;
import com.xulai.dreadsteel.item.weapon.DreadsteelScythe;
import com.xulai.dreadsteel.item.weapon.DreadsteelShield;
import com.xulai.dreadsteel.item.weapon.DreadsteelTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DreadsteelItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Dreadsteel.MOD_ID);

    public static final DeferredItem<Item> SCYTHE_PROJECTILE_DEFAULT = ITEMS.register("dreadsteel_scythe_projectile_default",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SCYTHE_PROJECTILE_BLACK = ITEMS.register("dreadsteel_scythe_projectile_black",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SCYTHE_PROJECTILE_BRONZE = ITEMS.register("dreadsteel_scythe_projectile_bronze",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SCYTHE_PROJECTILE_WHITE = ITEMS.register("dreadsteel_scythe_projectile_white",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DEFAULT_KIT = ITEMS.register("kit_default",
            () -> new CosmeticKit(new Item.Properties()));
    public static final DeferredItem<Item> WHITE_KIT = ITEMS.register("kit_white",
            () -> new CosmeticKit(new Item.Properties()));
    public static final DeferredItem<Item> BLACK_KIT = ITEMS.register("kit_black",
            () -> new CosmeticKit(new Item.Properties()));
    public static final DeferredItem<Item> BRONZE_KIT = ITEMS.register("kit_bronze",
            () -> new CosmeticKit(new Item.Properties()));

    public static final DeferredItem<Item> DREADSTEEL_INGOT = ITEMS.register("dreadsteel_ingot",
            () -> new DreadsteelIngot(itemBuilder()));

    public static final DeferredItem<Item> DREADSTEEL_HELMET = ITEMS.register("dreadsteel_helmet",
            () -> new DreadsteelArmor(DreadsteelArmor.DREADSTEEL_MATERIAL, ArmorItem.Type.HELMET, armorBuilder()));
    public static final DeferredItem<Item> DREADSTEEL_CHESTPLATE = ITEMS.register("dreadsteel_chestplate",
            () -> new DreadsteelArmor(DreadsteelArmor.DREADSTEEL_MATERIAL, ArmorItem.Type.CHESTPLATE, armorBuilder()));
    public static final DeferredItem<Item> DREADSTEEL_LEGGINGS = ITEMS.register("dreadsteel_leggings",
            () -> new DreadsteelArmor(DreadsteelArmor.DREADSTEEL_MATERIAL, ArmorItem.Type.LEGGINGS, armorBuilder()));
    public static final DeferredItem<Item> DREADSTEEL_BOOTS = ITEMS.register("dreadsteel_boots",
            () -> new DreadsteelArmor(DreadsteelArmor.DREADSTEEL_MATERIAL, ArmorItem.Type.BOOTS, armorBuilder()));

    public static final DeferredItem<Item> DREADSTEEL_SCYTHE = ITEMS.register("dreadsteel_scythe",
            () -> new DreadsteelScythe(DreadsteelTier.DREADSTEEL, itemBuilder()
                    .attributes(SwordItem.createAttributes(DreadsteelTier.DREADSTEEL, 0, -2.4F))
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))));
    public static final DeferredItem<Item> DREADSTEEL_SHIELD = ITEMS.register("dreadsteel_shield",
            () -> new DreadsteelShield(new Item.Properties().fireResistant().stacksTo(1)));

    private static Item.Properties itemBuilder() {
        return new Item.Properties().fireResistant();
    }

    private static Item.Properties armorBuilder() {
        return itemBuilder().stacksTo(1);
    }
}
