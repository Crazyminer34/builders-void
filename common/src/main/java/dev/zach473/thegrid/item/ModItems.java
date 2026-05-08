package dev.zach473.thegrid.item;

import dev.zach473.thegrid.ModConstants;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.item.BalmItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static dev.zach473.thegrid.ModConstants.id;
import static net.blay09.mods.balm.api.item.BalmItems.itemProperties;

public class ModItems {
    public static DeferredObject<CreativeModeTab> TAB;

    public static Item VOIDIUM;
    public static Item LINKED_VOIDIUM;

    public static void initialize(BalmItems items) {
        items.registerItem((identifier) -> VOIDIUM = new Voidium(itemProperties(identifier)), id("voidium"));
        items.registerItem((identifier) -> LINKED_VOIDIUM = new LinkedVoidium(itemProperties(identifier)), id("linked_voidium"));

        TAB = items.registerCreativeModeTab(() -> new ItemStack(VOIDIUM), id(ModConstants.MOD_ID));
    }
}
