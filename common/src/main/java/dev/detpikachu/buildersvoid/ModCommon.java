package dev.detpikachu.buildersvoid;

import dev.detpikachu.buildersvoid.item.ModItems;
import net.blay09.mods.balm.api.Balm;

public class ModCommon {

    public static void initialize() {

        ModItems.initialize(Balm.getItems());
    }
}
