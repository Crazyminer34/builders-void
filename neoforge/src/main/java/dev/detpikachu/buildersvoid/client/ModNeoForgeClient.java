package dev.detpikachu.buildersvoid.client;

import dev.detpikachu.buildersvoid.ModConstants;
import dev.detpikachu.buildersvoid.interop.ClothConfigInterop;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ModConstants.MOD_ID, dist = Dist.CLIENT)
public class ModNeoForgeClient {
    public ModNeoForgeClient(IEventBus eventBus, ModContainer container) {
        final var context = new NeoForgeLoadContext(eventBus);
        BalmClient.initializeMod(ModConstants.MOD_ID, context, ModCommonClient::initialize);
        container.registerExtensionPoint(IConfigScreenFactory.class, new ClothConfigInterop());
    }
}
