package com.xulai.dreadsteel.item.weapon;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

@EventBusSubscriber(modid = Dreadsteel.MOD_ID)
public class DreadsteelShield extends ShieldItem {

    public DreadsteelShield(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.dreadsteel.dreadsteel_shield"));
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @SubscribeEvent
    public static void onArrowHit(final LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.isBlocking() || player.getUseItem().getItem() != DreadsteelItems.DREADSTEEL_SHIELD.get()) return;

        Entity directEntity = event.getSource().getDirectEntity();

        if (directEntity instanceof AbstractArrow attacker) {
            Level level = player.level();
            if (level.isClientSide) return;
            attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
            attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.IRON_GOLEM_HURT, SoundSource.PLAYERS, 0.5F, 1.0F);
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 8; ++i) {
                    serverLevel.sendParticles(ParticleTypes.FLAME, attacker.getX(), attacker.getY(), attacker.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.5F);
                }
            }
            attacker.discard();
            event.setCanceled(true);
        }

        if (directEntity instanceof LivingEntity attacker) {
            attacker.igniteForSeconds(5.0F);
        }
    }
}
