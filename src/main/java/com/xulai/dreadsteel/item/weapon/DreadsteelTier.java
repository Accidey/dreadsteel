package com.xulai.dreadsteel.item.weapon;

import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum DreadsteelTier implements Tier {

    DREADSTEEL(7.0F, 22, 9.0F, () -> Ingredient.of(DreadsteelItems.DREADSTEEL_INGOT.get()));

    private final float damage;
    private final int enchantmentValue;
    private final float speed;
    private final Supplier<Ingredient> repairMaterial;

    DreadsteelTier(float damage, int enchantmentValue, float speed, Supplier<Ingredient> repairMaterial) {
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.speed = speed;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
