package dev.detpikachu.buildersvoid;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.fabricmc.api.ModInitializer;

public class ModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(ModConstants.MOD_ID, EmptyLoadContext.INSTANCE, ModCommon::initialize);
    }
}
