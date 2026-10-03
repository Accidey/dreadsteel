package com.xulai.dreadsteel.entity;

import net.mindoth.shadowizardlib.event.ShadowEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityScytheProjectileDefault extends AbstractArrow {

    public EntityScytheProjectileDefault(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public EntityScytheProjectileDefault(EntityType<? extends AbstractArrow> type, Level level, LivingEntity shooter, double damage) {
        super(type, shooter, level, ItemStack.EMPTY, null);
        this.setBaseDamage(damage);
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount == 80) {
            spawnParticles();
        } else if (this.tickCount > 80) {
            this.playSound(SoundEvents.ILLUSIONER_MIRROR_MOVE, 1.0F, 1.0F);
            this.discard();
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity && entity != this.getOwner()) {
            entity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), (float) this.getBaseDamage());
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (this.level().isClientSide) return;
        spawnParticles();
        this.playSound(SoundEvents.ILLUSIONER_MIRROR_MOVE, 1.0F, 1.0F);
        this.discard();
    }

    private void spawnParticles() {
        if (this.level().isClientSide) return;
        Vec3 center = ShadowEvents.getEntityCenter(this);
        if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 8; ++i) {
                serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH, center.x, center.y, center.z, 1, 0.0D, 0.0D, 0.0D, 1.0D);
            }
        }
    }

    @Override
    protected float getWaterInertia() {
        return 0.0F;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }
}
