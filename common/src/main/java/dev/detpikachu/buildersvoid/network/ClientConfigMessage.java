package dev.detpikachu.buildersvoid.network;

import dev.detpikachu.buildersvoid.ModConfig;
import net.blay09.mods.balm.api.network.SyncConfigMessage;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static dev.detpikachu.buildersvoid.ModConstants.id;

public class ClientConfigMessage extends SyncConfigMessage<ModConfig> {
    public static final CustomPacketPayload.Type<ClientConfigMessage> TYPE = new CustomPacketPayload.Type<>(id("config"));

    public ClientConfigMessage(ModConfig modConfig) {
        super(TYPE, modConfig);
    }
}
