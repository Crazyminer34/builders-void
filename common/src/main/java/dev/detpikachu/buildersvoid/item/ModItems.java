package dev.detpikachu.buildersvoid.item;

import dev.detpikachu.buildersvoid.ModConstants;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.item.BalmItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static dev.detpikachu.buildersvoid.ModConstants.id;
import static net.blay09.mods.balm.api.item.BalmItems.itemProperties;

public class ModItems {
    public static DeferredObject<CreativeModeTab> TAB;

    public static Item VOID_PEARL;
    public static Item LINKED_VOID_PEARL;

    public static void initialize(BalmItems items) {
        items.registerItem((identifier) -> VOID_PEARL = new VoidPearl(itemProperties(identifier)), id("void_pearl"));
        items.registerItem((identifier) -> LINKED_VOID_PEARL = new LinkedVoidPearl(itemProperties(identifier)), id("linked_void_pearl"));

        TAB = items.registerCreativeModeTab(() -> new ItemStack(VOID_PEARL), id(ModConstants.MOD_ID));
    }
}
