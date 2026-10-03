package com.xulai.dreadsteel.client.models.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmorModel extends HumanoidModel<LivingEntity> {

    public final ArmorItem.Type slot;
    protected final ModelPart modelHead;
    protected final ModelPart modelBody;
    protected final ModelPart modelBelt;
    protected final ModelPart modelLeft_arm;
    protected final ModelPart modelRight_arm;
    protected final ModelPart modelLeft_leg;
    protected final ModelPart modelRight_leg;
    protected final ModelPart modelLeft_foot;
    protected final ModelPart modelRight_foot;

    public ArmorModel(ModelPart root, ArmorItem.Type slot) {
        super(root);
        this.slot = slot;
        this.modelBelt = root.getChild("Belt");
        this.modelBody = root.getChild("Body");
        this.modelRight_foot = root.getChild("RightBoot");
        this.modelLeft_foot = root.getChild("LeftBoot");
        this.modelLeft_arm = root.getChild("LeftArm");
        this.modelRight_arm = root.getChild("RightArm");
        this.modelRight_leg = root.getChild("LeftLeg");
        this.modelLeft_leg = root.getChild("RightLeg");
        this.modelHead = root.getChild("Head");
    }

    public static PartDefinition createHumanoidAlias(MeshDefinition mesh) {
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("Belt", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("LeftBoot", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("RightBoot", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("LeftArm", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("RightArm", CubeListBuilder.create(), PartPose.ZERO);
        return root;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return this.slot == ArmorItem.Type.HELMET ? ImmutableList.of(this.modelHead) : ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        poseStack.pushPose();
        if (this.slot == ArmorItem.Type.HELMET) {
            this.modelHead.copyFrom(this.head);
            this.modelHead.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
        else if (this.slot == ArmorItem.Type.CHESTPLATE) {
            this.modelBody.copyFrom(this.body);
            this.modelBody.render(poseStack, buffer, packedLight, packedOverlay, color);
            this.modelRight_arm.copyFrom(this.rightArm);
            this.modelRight_arm.render(poseStack, buffer, packedLight, packedOverlay, color);
            this.modelLeft_arm.copyFrom(this.leftArm);
            this.modelLeft_arm.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
        else if (this.slot == ArmorItem.Type.LEGGINGS) {
            this.modelBelt.copyFrom(this.body);
            this.modelBelt.render(poseStack, buffer, packedLight, packedOverlay, color);
            this.modelRight_leg.copyFrom(this.rightLeg);
            this.modelRight_leg.render(poseStack, buffer, packedLight, packedOverlay, color);
            this.modelLeft_leg.copyFrom(this.leftLeg);
            this.modelLeft_leg.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
        else if (this.slot == ArmorItem.Type.BOOTS) {
            this.modelRight_foot.copyFrom(this.rightLeg);
            this.modelRight_foot.render(poseStack, buffer, packedLight, packedOverlay, color);
            this.modelLeft_foot.copyFrom(this.leftLeg);
            this.modelLeft_foot.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
        poseStack.popPose();
    }
}
