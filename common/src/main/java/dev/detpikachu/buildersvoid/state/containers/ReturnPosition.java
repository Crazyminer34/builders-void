package dev.detpikachu.buildersvoid.state.containers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record ReturnPosition(ResourceLocation dimension, Vec3 position) {
    public CompoundTag serialize() {
        final var tag = new CompoundTag();
        tag.putString("Dimension", dimension.toString());

        final var positionTag = new CompoundTag();
        positionTag.putDouble("X", position.x);
        positionTag.putDouble("Y", position.y);
        positionTag.putDouble("Z", position.z);
        tag.put("Position", positionTag);

        return tag;
    }

    public static ReturnPosition deserialize(CompoundTag tag) {
        final var dimensionString = tag.getString("Dimension");
        final var dimension = ResourceLocation.tryParse(dimensionString);

        final var positionTag = tag.getCompound("Position");
        final var position = new Vec3(
            positionTag.getFloat("X"),
            positionTag.getFloat("Y"),
            positionTag.getFloat("Z")
        );

        return new ReturnPosition(dimension, position);
    }
}
