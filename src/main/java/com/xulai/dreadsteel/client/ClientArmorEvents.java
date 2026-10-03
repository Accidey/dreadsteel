package com.xulai.dreadsteel.client;

import com.xulai.dreadsteel.Dreadsteel;
import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(modid = Dreadsteel.MOD_ID, value = Dist.CLIENT)
public class ClientArmorEvents {

    @SubscribeEvent
    public static void noHat(final RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DreadsteelItems.DREADSTEEL_HELMET.get()) {
            event.getRenderer().getModel().hat.visible = false;
        }
    }
}
