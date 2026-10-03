package com.xulai.dreadsteel.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CosmeticKit extends Item {

    public CosmeticKit(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.dreadsteel.cosmetic_kit"));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
