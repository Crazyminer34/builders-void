package dev.detpikachu.buildersvoid.network;

import dev.detpikachu.buildersvoid.ModConfig;
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
