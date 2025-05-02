package dev.detpikachu.buildersvoid;

import net.blay09.mods.balm.api.Balm;
import net.fabricmc.api.ModInitializer;

public class ModFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        Balm.initialize(ModConstants.MOD_ID, ModCommon::initialize);
    }
}
