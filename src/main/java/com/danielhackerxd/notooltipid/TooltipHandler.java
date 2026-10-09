package com.danielhackerxd.notooltipid;

import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;


@Mod.EventBusSubscriber(modid = NoTooltipId.MODID, value = Side.CLIENT)
public final class TooltipHandler {


    private static final Pattern FALLBACK = Pattern.compile(" ?\\(#\\d{4,}(?:/\\d+)?\\)$");

    private TooltipHandler() {
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!event.getFlags().isAdvanced()) {
            return;
        }

        ItemStack stack = event.getItemStack();
        List<String> tooltip = event.getToolTip();
        if (stack.isEmpty() || tooltip.isEmpty()) {
            return;
        }

        String first = tooltip.get(0);
        String cleaned = stripId(first, stack);
        if (!cleaned.equals(first)) {
            tooltip.set(0, cleaned);
        }
    }

    private static String stripId(String line, ItemStack stack) {
        Item item = stack.getItem();
        int id = Item.getIdFromItem(item);


        String idText = item.getHasSubtypes()
                ? String.format("#%04d/%d", id, stack.getItemDamage())
                : String.format("#%04d", id);
        String suffix = " (" + idText + ")";

        if (line.endsWith(suffix)) {
            return line.substring(0, line.length() - suffix.length());
        }
        return FALLBACK.matcher(line).replaceFirst("");
    }
}
