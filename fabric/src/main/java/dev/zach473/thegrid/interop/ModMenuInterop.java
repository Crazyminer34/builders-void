package dev.zach473.thegrid.interop;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.zach473.thegrid.ModConfig;
import net.blay09.mods.balm.fabric.compat.ModMenuUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuInterop implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModMenuUtils.getConfigScreen(ModConfig.class);
    }
}
