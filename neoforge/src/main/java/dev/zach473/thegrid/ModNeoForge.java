package dev.zach473.thegrid;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ModConstants.MOD_ID)
public class ModNeoForge {
    public ModNeoForge(IEventBus eventBus) {
        final var context = new NeoForgeLoadContext(eventBus);
        Balm.initializeMod(ModConstants.MOD_ID, context, ModCommon::initialize);
    }
}
