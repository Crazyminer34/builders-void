package dev.detpikachu.buildersvoid;

import dev.detpikachu.buildersvoid.network.ClientConfigMessage;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.config.BalmConfig;
import net.blay09.mods.balm.api.config.BalmConfigData;
import net.blay09.mods.balm.api.config.reflection.Comment;
import net.blay09.mods.balm.api.config.reflection.Config;

@Config(value = ModConstants.MOD_ID)
public class ModConfig implements BalmConfigData {
    @Comment("Enables a cooldown when using any of the pearl variants")
    public boolean enableCooldown = true;

    @Comment("Pearl usage cooldown length, in ticks")
    public int cooldown = 100;

    public static void initialize(BalmConfig config) {
        config.registerConfig(ModConfig.class, ClientConfigMessage::new);
    }

    public static ModConfig getActive() {
        return Balm.getConfig().getActive(ModConfig.class);
    }
}
