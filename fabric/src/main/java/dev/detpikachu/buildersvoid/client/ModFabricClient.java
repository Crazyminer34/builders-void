package dev.detpikachu.buildersvoid.client;

import dev.detpikachu.buildersvoid.ModConstants;
import net.blay09.mods.balm.api.client.BalmClient;
import net.fabricmc.api.ClientModInitializer;

public class ModFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BalmClient.initialize(ModConstants.MOD_ID, ModCommonClient::initialize);
    }
}
