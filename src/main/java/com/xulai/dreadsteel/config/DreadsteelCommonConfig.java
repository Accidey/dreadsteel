package com.xulai.dreadsteel.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class DreadsteelCommonConfig {

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Integer> HELMET_ARMOR;
    public static final ModConfigSpec.ConfigValue<Integer> CHESTPLATE_ARMOR;
    public static final ModConfigSpec.ConfigValue<Integer> LEGGINGS_ARMOR;
    public static final ModConfigSpec.ConfigValue<Integer> BOOTS_ARMOR;
    public static final ModConfigSpec.ConfigValue<Integer> ARMOR_TOUGHNESS;
    public static final ModConfigSpec.ConfigValue<Double> ARMOR_KNOCKBACK_RESISTANCE;
    public static final ModConfigSpec.ConfigValue<Integer> SCYTHE_DAMAGE;
    public static final ModConfigSpec.ConfigValue<Double> SCYTHE_SPEED;

    private static boolean pendingSeparator = false;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("Dreadsteel");

        HELMET_ARMOR = intOption(builder, "HelmetArmor", "头盔护甲值", "Helmet armor value", 10, 0, 100);
        CHESTPLATE_ARMOR = intOption(builder, "ChestplateArmor", "胸甲护甲值", "Chestplate armor value", 15, 0, 100);
        LEGGINGS_ARMOR = intOption(builder, "LeggingsArmor", "护腿护甲值", "Leggings armor value", 12, 0, 100);
        BOOTS_ARMOR = intOption(builder, "BootsArmor", "靴子护甲值", "Boots armor value", 9, 0, 100);
        ARMOR_TOUGHNESS = intOption(builder, "ArmorToughness", "护甲韧性", "Armor toughness", 8, 0, 100);
        ARMOR_KNOCKBACK_RESISTANCE = doubleOption(builder, "ArmorKnockbackResistance", "护甲击退抗性", "Armor knockback resistance", 0.25D, 0.0D, 1.0D);
        SCYTHE_DAMAGE = intOption(builder, "ScytheDamage",
                "巨镰攻击伤害（手上还会提供 +1 基础伤害，因此 49 在提示栏中显示为 50）",
                "Scythe attack damage (+1 base damage from your hand, so 49 shows as 50 on the tooltip)", 49, 0, 10000);
        SCYTHE_SPEED = doubleOption(builder, "ScytheSpeed", "巨镰攻击速度", "Scythe attack speed", 1.6D, 0.1D, 100.0D);

        builder.pop();
        SPEC = builder.build();
    }

    private static ModConfigSpec.ConfigValue<Integer> intOption(ModConfigSpec.Builder builder, String key, String chinese, String english, int defaultValue, int min, int max) {
        ModConfigSpec.ConfigValue<Integer> value = describe(builder, chinese, english)
                .defineInRange(key, defaultValue, min, max, Integer.class);
        pendingSeparator = true;
        return value;
    }

    private static ModConfigSpec.ConfigValue<Double> doubleOption(ModConfigSpec.Builder builder, String key, String chinese, String english, double defaultValue, double min, double max) {
        ModConfigSpec.ConfigValue<Double> value = describe(builder, chinese, english)
                .defineInRange(key, defaultValue, min, max, Double.class);
        pendingSeparator = true;
        return value;
    }

    private static ModConfigSpec.Builder describe(ModConfigSpec.Builder builder, String chinese, String english) {
        if (pendingSeparator) {
            builder.comment("");
        }
        return builder.comment(" " + chinese, " " + english);
    }
}
