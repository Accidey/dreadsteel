package com.xulai.dreadsteel;

import com.xulai.dreadsteel.config.DreadsteelCommonConfig;
import com.xulai.dreadsteel.item.armor.DreadsteelArmor;
import com.xulai.dreadsteel.registries.DreadsteelEntities;
import com.xulai.dreadsteel.registries.DreadsteelItems;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@Mod(Dreadsteel.MOD_ID)
public class Dreadsteel {

    public static final String MOD_ID = "dreadsteel";

    public Dreadsteel(IEventBus modBus, ModContainer modContainer) {
        DreadsteelItems.ITEMS.register(modBus);
        DreadsteelEntities.ENTITIES.register(modBus);
        DreadsteelArmor.ARMOR_MATERIALS.register(modBus);
        modBus.addListener(Dreadsteel::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, DreadsteelCommonConfig.SPEC);
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(DreadsteelItems.DREADSTEEL_HELMET);
            event.accept(DreadsteelItems.DREADSTEEL_CHESTPLATE);
            event.accept(DreadsteelItems.DREADSTEEL_LEGGINGS);
            event.accept(DreadsteelItems.DREADSTEEL_BOOTS);
            event.accept(DreadsteelItems.DREADSTEEL_SCYTHE);
            event.accept(DreadsteelItems.DREADSTEEL_SHIELD);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(DreadsteelItems.DREADSTEEL_INGOT);
            event.accept(DreadsteelItems.DEFAULT_KIT);
            event.accept(DreadsteelItems.WHITE_KIT);
            event.accept(DreadsteelItems.BLACK_KIT);
            event.accept(DreadsteelItems.BRONZE_KIT);
        }
    }

    public static void sendMSGToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }
}
