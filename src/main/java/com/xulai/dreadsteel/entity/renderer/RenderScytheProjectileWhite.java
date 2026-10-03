package com.xulai.dreadsteel.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.xulai.dreadsteel.entity.EntityScytheProjectileWhite;
import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderScytheProjectileWhite extends EntityRenderer<EntityScytheProjectileWhite> {

    private final ItemStack projectile = new ItemStack(DreadsteelItems.SCYTHE_PROJECTILE_WHITE.get());

    public RenderScytheProjectileWhite(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityScytheProjectileWhite entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void render(EntityScytheProjectileWhite entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        poseStack.translate(0.0D, 0.5D, 0.0D);
        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.translate(0.0D, -0.15D, 0.0D);
        Minecraft.getInstance().getItemRenderer().renderStatic(this.projectile, ItemDisplayContext.GROUND, 240, 0, poseStack, buffer, entity.level(), 0);
        poseStack.popPose();
    }
}
