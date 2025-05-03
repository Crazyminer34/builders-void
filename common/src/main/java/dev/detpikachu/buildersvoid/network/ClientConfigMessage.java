package dev.detpikachu.buildersvoid.network;

import dev.detpikachu.buildersvoid.ModConfig;
import net.blay09.mods.balm.api.network.SyncConfigMessage;

public class ClientConfigMessage extends SyncConfigMessage<ModConfig> {
    public ClientConfigMessage(ModConfig modConfig) {
        super(modConfig);
    }
}
