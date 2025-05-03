package dev.detpikachu.buildersvoid.state;

import dev.detpikachu.buildersvoid.logic.DimensionLogic;
import dev.detpikachu.buildersvoid.state.containers.ReturnPosition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.UUID;

public class ModState extends SavedData {
    public static String FILE_NAME = "state";

    private int lastIndex = -1;
    private HashMap<UUID, Integer> indexes = new HashMap<>();
    private HashMap<UUID, ReturnPosition> returnPositions = new HashMap<>();

    /**
     * Get the active mod state object.
     *
     * @param level The current level the player is in
     * @return The active ModState
     */
    public static ModState get(ServerLevel level) {
        if (level.dimension().location() != DimensionLogic.DIMENSION_VOID) {
            level = DimensionLogic.getVoidDimension(level);
        }

        return level.getDataStorage().computeIfAbsent(ModState::load, ModState::new, FILE_NAME);
    }

    /**
     * Load the mod state from an NBT tag.
     *
     * @param compoundTag The CompoundTag to load from
     * @return The loaded ModState
     */
    public static ModState load(CompoundTag compoundTag) {
        final var state = new ModState();

        if (compoundTag.contains("LastIndex")) {
            state.lastIndex = compoundTag.getInt("LastIndex");
        }

        if (compoundTag.contains("Indexes")) {
            final var indexesTag = compoundTag.getCompound("Indexes");
            indexesTag.getAllKeys().forEach((uuid) -> {
                state.indexes.put(UUID.fromString(uuid), indexesTag.getInt(uuid));
            });
        }

        if (compoundTag.contains("ReturnPositions")) {
            final var returnPositionsTag = compoundTag.getCompound("ReturnPositions");
            returnPositionsTag.getAllKeys().forEach((uuid) -> {
                state.returnPositions.put(UUID.fromString(uuid), ReturnPosition.deserialize(returnPositionsTag.getCompound(uuid)));
            });
        }

        return state;
    }

    /**
     * Save the active mod state to an NBT tag.
     *
     * @param compoundTag The CompoundTag to save to
     * @return The saved CompoundTag
     */
    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putInt("LastIndex", lastIndex);

        final var indexesTag = new CompoundTag();
        indexes.forEach((uuid, index) -> {
            indexesTag.putInt(uuid.toString(), index);
        });
        compoundTag.put("Indexes", indexesTag);

        final var returnPositionsTag = new CompoundTag();
        returnPositions.forEach((uuid, position) -> {
            returnPositionsTag.put(uuid.toString(), position.serialize());
        });
        compoundTag.put("ReturnPositions", returnPositionsTag);

        return compoundTag;
    }

    /**
     * Check whether the given player UUID has an index recorded.
     *
     * @param uuid The UUID to search
     * @return true if an index has been recorded, false otherwise
     */
    public boolean hasIndex(UUID uuid) {
        return indexes.containsKey(uuid);
    }

    /**
     * Get the recorded index for the given player UUID.
     *
     * @param uuid The UUID to retrieve for
     * @return The recorded index of the UUID
     */
    public int getIndex(UUID uuid) {
        return indexes.get(uuid);
    }

    /**
     * Record an index for the given player UUID.
     *
     * @param uuid The UUID to record for
     * @return The recorded index of the UUID
     */
    public int addIndex(UUID uuid) {
        lastIndex += 1;
        indexes.put(uuid, lastIndex);
        setDirty();
        return lastIndex;
    }

    /**
     * Check whether the given player UUID has a return position recorded.
     *
     * @param uuid The UUID to search
     * @return true if a return position has been recorded, false otherwise
     */
    public boolean hasReturnPosition(UUID uuid) {
        return returnPositions.containsKey(uuid);
    }

    /**
     * Get and remove the recorded return position for the given player UUID.
     *
     * @param uuid The UUID to retrieve for
     * @return The recorded return position of the UUID
     */
    public ReturnPosition popReturnPosition(UUID uuid) {
        final var position = returnPositions.get(uuid);
        returnPositions.remove(uuid);
        setDirty();
        return position;
    }

    /**
     * Record a return position for the given player UUID.
     *
     * @param uuid The UUID to record for
     * @param dimension The dimension to record
     * @param position The position to record
     */
    public void pushReturnPosition(UUID uuid, ResourceLocation dimension, Vec3 position) {
        returnPositions.put(uuid, new ReturnPosition(dimension, position));
        setDirty();
    }
}
