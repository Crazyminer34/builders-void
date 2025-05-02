package dev.detpikachu.buildersvoid;

import net.blay09.mods.balm.api.Balm;
import net.minecraftforge.fml.common.Mod;

@Mod(ModConstants.MOD_ID)
public class ModForge {

    public ModForge() {

        Balm.initialize(ModConstants.MOD_ID, ModCommon::initialize);
    }
}
