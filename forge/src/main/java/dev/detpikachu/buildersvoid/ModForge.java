package dev.detpikachu.buildersvoid;

import dev.detpikachu.buildersvoid.client.ModCommonClient;
import dev.detpikachu.buildersvoid.interop.ClothConfigInterop;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(ModConstants.MOD_ID)
public class ModForge {

    public ModForge() {

        Balm.initialize(ModConstants.MOD_ID, ModCommon::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            BalmClient.initialize(ModConstants.MOD_ID, ModCommonClient::initialize);
            ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((ignoredMinecraft, previousScreen) -> ClothConfigInterop.getConfigScreen(previousScreen))
            );
        });
    }
}
