package com.xulai.dreadsteel.item.weapon;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.config.DreadsteelCommonConfig;
import com.xulai.dreadsteel.entity.EntityScytheProjectileBlack;
import com.xulai.dreadsteel.entity.EntityScytheProjectileBronze;
import com.xulai.dreadsteel.entity.EntityScytheProjectileDefault;
import com.xulai.dreadsteel.entity.EntityScytheProjectileWhite;
import com.xulai.dreadsteel.message.MessageSwingArm;
import com.xulai.dreadsteel.registries.DreadsteelEntities;
import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

@EventBusSubscriber(modid = Dreadsteel.MOD_ID)
public class DreadsteelScythe extends SwordItem {

    public DreadsteelScythe(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @SubscribeEvent
    public static void dreadsteelScytheAttackAttributeEvent(ItemAttributeModifierEvent event) {
        if (event.getItemStack().getItem() != DreadsteelItems.DREADSTEEL_SCYTHE.get()) return;
        event.removeModifier(Attributes.ATTACK_DAMAGE, Item.BASE_ATTACK_DAMAGE_ID);
        event.removeModifier(Attributes.ATTACK_SPEED, Item.BASE_ATTACK_SPEED_ID);
        event.addModifier(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, DreadsteelCommonConfig.SCYTHE_DAMAGE.get(), AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND);
        event.addModifier(Attributes.ATTACK_SPEED,
                new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, DreadsteelCommonConfig.SCYTHE_SPEED.get() - 4.0D, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.dreadsteel.dreadsteel_scythe"));
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() != DreadsteelItems.DREADSTEEL_SCYTHE.get()) return;
        Dreadsteel.sendMSGToServer(new MessageSwingArm());
    }

    public static void onLeftClick(final Player player, final ItemStack stack) {
        if (stack.getItem() == DreadsteelItems.DREADSTEEL_SCYTHE.get()) spawnProjectile(stack, player);
    }

    public static void spawnProjectile(ItemStack stack, Player player) {
        if (player.swingTime > 0.5F) return;
        if (player.getItemInHand(InteractionHand.MAIN_HAND) != stack) return;

        double totalDamage = 0.0D;
        for (ItemAttributeModifiers.Entry entry : stack.getAttributeModifiers().modifiers()) {
            if (entry.attribute().value() == Attributes.ATTACK_DAMAGE.value() && entry.slot().test(EquipmentSlot.MAINHAND)) {
                totalDamage += entry.modifier().amount();
            }
        }

        CustomModelData modelData = stack.get(DataComponents.CUSTOM_MODEL_DATA);
        int color = modelData == null ? 0 : modelData.value();

        AbstractArrow shot;
        if (color == 1) {
            shot = new EntityScytheProjectileWhite(DreadsteelEntities.SCYTHE_PROJECTILE_WHITE.get(), player.level(), player, totalDamage);
        } else if (color == 2) {
            shot = new EntityScytheProjectileBlack(DreadsteelEntities.SCYTHE_PROJECTILE_BLACK.get(), player.level(), player, totalDamage);
        } else if (color == 3) {
            shot = new EntityScytheProjectileBronze(DreadsteelEntities.SCYTHE_PROJECTILE_BRONZE.get(), player.level(), player, totalDamage);
        } else {
            shot = new EntityScytheProjectileDefault(DreadsteelEntities.SCYTHE_PROJECTILE_DEFAULT.get(), player.level(), player, totalDamage);
        }

        shot.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 0.0F);
        player.level().addFreshEntity(shot);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 0.75F, 0.75F);
    }
}
