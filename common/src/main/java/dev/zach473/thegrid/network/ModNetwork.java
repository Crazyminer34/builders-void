package dev.zach473.thegrid.network;

import dev.zach473.thegrid.ModConfig;
import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.balm.api.network.SyncConfigMessage;

public class ModNetwork {
    public static void initialize(BalmNetworking networking) {
        SyncConfigMessage.register(
            ClientConfigMessage.TYPE,
            ClientConfigMessage.class,
            ClientConfigMessage::new,
            ModConfig.class,
            ModConfig::new
        );
    }
}
