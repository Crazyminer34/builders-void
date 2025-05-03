package dev.detpikachu.buildersvoid.logic;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import static dev.detpikachu.buildersvoid.ModConstants.id;

public class DimensionLogic {
    public static final ResourceLocation DIMENSION_VOID = id("builders_void");

    /**
     * Get the void dimension level.
     *
     * @param currentLevel The current level the player is in
     * @return The void dimension ServerLevel
     */
    public static ServerLevel getVoidDimension(ServerLevel currentLevel) {
        return getDimension(currentLevel, DIMENSION_VOID);
    }

    /**
     * Get a dimension.
     *
     * @param currentLevel The current level the player is in
     * @param location The ResourceLocation of the dimension to retrieve
     * @return The retrieved dimension
     */
    public static ServerLevel getDimension(ServerLevel currentLevel, ResourceLocation location) {
        return getDimension(currentLevel, getDimensionKey(location));
    }

    /**
     * Get a dimension.
     *
     * @param currentLevel The current level the player is in
     * @param key The ResourceKey of the dimension to retrieve
     * @return The retrieved dimension
     */
    public static ServerLevel getDimension(ServerLevel currentLevel, ResourceKey<Level> key) {
        return currentLevel.getServer().getLevel(key);
    }

    /**
     * Get the void dimension resource key.
     *
     * @return The void dimension ResourceKey
     */
    public static ResourceKey<Level> getVoidDimensionKey() {
        return getDimensionKey(DIMENSION_VOID);
    }

    /**
     * Get a dimension resource key.
     *
     * @param location The ResourceLocation of the dimension
     * @return The ResourceKey of the dimension
     */
    public static ResourceKey<Level> getDimensionKey(ResourceLocation location) {
        return ResourceKey.create(Registries.DIMENSION, location);
    }
}
