package com.xulai.dreadsteel.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DreadsteelIngot extends Item {

    public DreadsteelIngot(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.dreadsteel.dreadsteel_ingot"));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
