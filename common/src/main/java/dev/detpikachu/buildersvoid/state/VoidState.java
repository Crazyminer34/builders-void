package dev.detpikachu.buildersvoid.state;

import dev.detpikachu.buildersvoid.state.containers.ReturnPosition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.UUID;

public class VoidState extends SavedData {
    public static String FILE_NAME = "state";

    private int lastIndex = -1;
    private HashMap<UUID, Integer> indexes = new HashMap<>();
    private HashMap<UUID, ReturnPosition> returnPositions = new HashMap<>();

    public static VoidState load(CompoundTag compoundTag) {
        VoidState state = new VoidState();

        if (compoundTag.contains("LastIndex")) {
            state.lastIndex = compoundTag.getInt("LastIndex");
        }

        if (compoundTag.contains("Indexes")) {
            CompoundTag indexesTag = compoundTag.getCompound("Indexes");
            indexesTag.getAllKeys().forEach((uuid) -> {
                state.indexes.put(UUID.fromString(uuid), indexesTag.getInt(uuid));
            });
        }

        if (compoundTag.contains("ReturnPositions")) {
            CompoundTag returnPositionsTag = compoundTag.getCompound("ReturnPositions");
            returnPositionsTag.getAllKeys().forEach((uuid) -> {
                state.returnPositions.put(UUID.fromString(uuid), ReturnPosition.deserialize(returnPositionsTag.getCompound(uuid)));
            });
        }

        return state;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putInt("LastIndex", lastIndex);

        CompoundTag indexesTag = new CompoundTag();
        indexes.forEach((uuid, index) -> {
            indexesTag.putInt(uuid.toString(), index);
        });
        compoundTag.put("Indexes", indexesTag);

        CompoundTag returnPositionsTag = new CompoundTag();
        returnPositions.forEach((uuid, position) -> {
            returnPositionsTag.put(uuid.toString(), position.serialize());
        });
        compoundTag.put("ReturnPositions", returnPositionsTag);

        return compoundTag;
    }

    public boolean hasIndex(UUID uuid) {
        return indexes.containsKey(uuid);
    }

    public int getIndex(UUID uuid) {
        return indexes.get(uuid);
    }

    public int addIndex(UUID uuid) {
        lastIndex += 1;
        indexes.put(uuid, lastIndex);
        setDirty();
        return lastIndex;
    }

    public boolean hasReturnPosition(UUID uuid) {
        return returnPositions.containsKey(uuid);
    }

    public ReturnPosition popReturnPosition(UUID uuid) {
        ReturnPosition position = returnPositions.get(uuid);
        returnPositions.remove(uuid);
        setDirty();
        return position;
    }

    public void pushReturnPosition(UUID uuid, ServerLevel dimension, Vec3 position) {
        returnPositions.put(uuid, new ReturnPosition(dimension.dimension().location(), position));
        setDirty();
    }
}
