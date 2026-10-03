package com.xulai.dreadsteel.network;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.message.MessageSwingArm;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Dreadsteel.MOD_ID)
public class DreadsteelNetwork {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Dreadsteel.MOD_ID).versioned("1.0.0").optional();
        registrar.playToServer(MessageSwingArm.TYPE, MessageSwingArm.STREAM_CODEC, MessageSwingArm::handle);
    }
}
