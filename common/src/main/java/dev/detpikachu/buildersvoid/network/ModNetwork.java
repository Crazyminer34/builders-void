package dev.detpikachu.buildersvoid.network;

import dev.detpikachu.buildersvoid.ModConfig;
import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.balm.api.network.SyncConfigMessage;

import static dev.detpikachu.buildersvoid.ModConstants.id;

public class ModNetwork {
    public static void initialize(BalmNetworking networking) {
        SyncConfigMessage.register(
            id("config"),
            ClientConfigMessage.class,
            ClientConfigMessage::new,
            ModConfig.class,
            ModConfig::new
        );
    }
}
