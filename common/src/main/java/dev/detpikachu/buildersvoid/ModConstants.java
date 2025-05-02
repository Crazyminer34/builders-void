package dev.detpikachu.buildersvoid;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModConstants {

	public static final String MOD_ID = "buildersvoid";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static final int VOID_HEIGHT = 50;
    public static final int VOID_SPACING = 10000;

    public static ResourceLocation id(String path) {

        return new ResourceLocation(MOD_ID, path);
    }
}
