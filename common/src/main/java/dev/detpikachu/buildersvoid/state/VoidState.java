package dev.detpikachu.buildersvoid.state;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.UUID;

public class VoidState extends SavedData {

    public static String FILE_NAME = "state";

    private int lastIndex = -1;
    private HashMap<UUID, Integer> indexes = new HashMap<>();

    public static VoidState load(CompoundTag compoundTag) {

        VoidState state = new VoidState();

        if (compoundTag.contains("LastIndex"))
        {
            state.lastIndex = compoundTag.getInt("LastIndex");
        }

        if (compoundTag.contains("Indexes")) {
            CompoundTag indexesTag = compoundTag.getCompound("Indexes");
            indexesTag.getAllKeys().forEach((uuid) -> {
                state.indexes.put(UUID.fromString(uuid), indexesTag.getInt(uuid));
            });
        }

        return state;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        CompoundTag indexesTag = new CompoundTag();
        indexes.forEach((uuid, index) -> {
            indexesTag.putInt(uuid.toString(), index);
        });

        compoundTag.putInt("LastIndex", lastIndex);
        compoundTag.put("Indexes", indexesTag);

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
}
