package dev.zach473.thegrid.client;

import dev.zach473.thegrid.ModConstants;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.fabricmc.api.ClientModInitializer;

public class ModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BalmClient.initializeMod(ModConstants.MOD_ID, EmptyLoadContext.INSTANCE, ModCommonClient::initialize);
    }
}
