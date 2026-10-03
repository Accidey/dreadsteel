package com.xulai.dreadsteel.message;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.item.weapon.DreadsteelScythe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MessageSwingArm implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageSwingArm> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Dreadsteel.MOD_ID, "swing_arm"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSwingArm> STREAM_CODEC =
            CustomPacketPayload.codec(MessageSwingArm::encode, MessageSwingArm::new);

    public MessageSwingArm() {
    }

    public MessageSwingArm(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static void handle(MessageSwingArm packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                DreadsteelScythe.onLeftClick(player, player.getItemInHand(InteractionHand.MAIN_HAND));
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
