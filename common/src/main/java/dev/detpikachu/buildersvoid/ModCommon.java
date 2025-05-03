package dev.detpikachu.buildersvoid;

import dev.detpikachu.buildersvoid.command.ModCommands;
import dev.detpikachu.buildersvoid.item.ModItems;
import dev.detpikachu.buildersvoid.network.ModNetwork;
import net.blay09.mods.balm.api.Balm;

public class ModCommon {
    public static void initialize() {
        ModConfig.initialize(Balm.getConfig());
        ModNetwork.initialize(Balm.getNetworking());
        ModItems.initialize(Balm.getItems());
        ModCommands.initialize(Balm.getCommands());
    }
}
