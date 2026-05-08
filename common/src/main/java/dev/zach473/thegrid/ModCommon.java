package dev.zach473.thegrid;

import dev.zach473.thegrid.command.ModCommands;
import dev.zach473.thegrid.data.ModDataComponents;
import dev.zach473.thegrid.item.ModItems;
import dev.zach473.thegrid.network.ModNetwork;
import net.blay09.mods.balm.api.Balm;

public class ModCommon {
    public static void initialize() {
        ModConfig.initialize(Balm.getConfig());
        ModNetwork.initialize(Balm.getNetworking());
        ModDataComponents.initialize(Balm.getComponents());
        ModItems.initialize(Balm.getItems());
        ModCommands.initialize(Balm.getCommands());
    }
}
